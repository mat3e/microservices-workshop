package io.github.mat3e.sink;

import io.github.mat3e.model.ReportedTaskState;
import io.github.mat3e.model.ReportedTaskStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
class ReportedTaskEventListener {
    private final static Logger logger = LoggerFactory.getLogger(ReportedTaskEventListener.class);
    private ReportedTaskStateRepository repository;

    ReportedTaskEventListener(ReportedTaskStateRepository repository) {
        this.repository = repository;
    }

    @Async
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

    @Async
    @EventListener
    public void on(TaskDone task) {
        logger.info("Task changed: " + task.getId());
        repository.save(ReportedTaskState.done(task.getId(), task.getOccurredOn()));
    }

    @Async
    @EventListener
    public void on(TaskUndone task) {
        logger.info("Task changed: " + task.getId());
        repository.save(ReportedTaskState.undone(task.getId(), task.getOccurredOn()));
    }
}
