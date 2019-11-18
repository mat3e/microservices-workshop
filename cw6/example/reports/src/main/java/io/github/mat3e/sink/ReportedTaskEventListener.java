package io.github.mat3e.sink;

import io.github.mat3e.model.ReportedTaskState;
import io.github.mat3e.model.ReportedTaskStateRepository;
import io.github.mat3e.model.TaskCreated;
import io.github.mat3e.model.TaskDone;
import io.github.mat3e.model.TaskUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
class ReportedTaskEventListener {
    private final static Logger logger = LoggerFactory.getLogger(ReportedTaskEventListener.class);
    private ReportedTaskStateRepository repository;

    ReportedTaskEventListener(ReportedTaskStateRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void on(TaskCreated taskCreated) {
        logger.info("New task created: " + taskCreated.getId());
        var state = new ReportedTaskState(
                taskCreated.getId(),
                taskCreated.getDeadline(),
                taskCreated.getText(),
                taskCreated.getOccurredOn()
        );
        repository.save(state);
    }

    @EventListener
    public void on(TaskDone task) {
        logger.info("Task changed: " + task.getId());
        repository.save(ReportedTaskState.done(task.getId(), task.getOccurredOn()));
    }

    @EventListener
    public void on(TaskUndone task) {
        logger.info("Task changed: " + task.getId());
        repository.save(ReportedTaskState.undone(task.getId(), task.getOccurredOn()));
    }
}
