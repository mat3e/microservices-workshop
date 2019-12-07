package io.github.mat3e.process;

import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskCreated;
import io.github.mat3e.model.TaskEvent;
import io.github.mat3e.model.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private ApplicationEventPublisher publisher;
    private TaskRepository repository;

    TaskService(ApplicationEventPublisher publisher, TaskRepository repository) {
        this.publisher = publisher;
        this.repository = repository;
    }

    @Transactional
    public Task create(Task toCreate) {
        String idBefore = toCreate.getId();
        Task result = repository.insert(toCreate);
        if (idChanged(idBefore, result)) {
            publisher.publishEvent(new TaskCreated(result));
        }
        return result;
    }

    @Transactional
    public Optional<Task> changeState(String id) {
        Optional<Task> optionalTask = repository.findById(id);
        optionalTask.ifPresent(task -> {
            TaskEvent event = task.change();
            repository.save(task);
            publisher.publishEvent(event);
        });
        return optionalTask;
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
