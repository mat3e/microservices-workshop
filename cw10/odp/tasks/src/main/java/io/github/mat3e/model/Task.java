package io.github.mat3e.model;

import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;

public class Task {
    @Id
    private String id;

    private ZonedDateTime deadline;

    private boolean done;

    private String text;

    public Task() {
    }

    public Task(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return done;
    }

    public String getText() {
        return text;
    }

    public TaskEvent change() {
        if (done) {
            return undo();
        }
        return finish();
    }

    TaskDone finish() {
        done = true;
        return new TaskDone(id);
    }

    TaskUndone undo() {
        done = false;
        return new TaskUndone(id);
    }
}
