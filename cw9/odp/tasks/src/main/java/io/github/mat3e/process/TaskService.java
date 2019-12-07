package io.github.mat3e.process;

import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskCreated;
import io.github.mat3e.model.TaskEvent;
import io.github.mat3e.model.TaskRepository;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final static String EVENT_TYPE_HEADER = "type";

    private TaskRepository repository;
    private Source source;

    TaskService(TaskRepository repository, final Source source) {
        this.repository = repository;
        this.source = source;
    }

    @Transactional
    public Task create(Task toCreate) {
        String idBefore = toCreate.getId();
        Task result = repository.insert(toCreate);
        if (idChanged(idBefore, result)) {
            publishCreateEvent(result);
        }
        return result;
    }

    @Transactional
    public Optional<Task> changeState(String id) {
        Optional<Task> optionalTask = repository.findById(id);
        optionalTask.ifPresent(task -> {
            TaskEvent event = task.change();
            repository.save(task);
            publishChangeEvent(event);
        });
        return optionalTask;
    }

    private void publishCreateEvent(Task task) {
        source.output().send(MessageBuilder
                .withPayload(new TaskCreated(task))
                .setHeader(EVENT_TYPE_HEADER, TaskCreated.TYPE)
                .build());
    }

    private void publishChangeEvent(TaskEvent event) {
        source.output().send(MessageBuilder
                .withPayload(event)
                .setHeader(EVENT_TYPE_HEADER, event.getType())
                .build());
    }

    public List<Task> readAll() {
        return repository.findAll();
    }

    public Optional<Task> read(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    private boolean idChanged(String idBefore, Task result) {
        return !result.getId().equals(idBefore);
    }
}
