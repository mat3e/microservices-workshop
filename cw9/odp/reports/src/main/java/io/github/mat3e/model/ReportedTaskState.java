package io.github.mat3e.model;

import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.time.ZonedDateTime;

public class ReportedTaskState {
    public static ReportedTaskState done(String id, Instant timestamp) {
        return new ReportedTaskState(id, true, timestamp);
    }

    public static ReportedTaskState undone(String id, Instant timestamp) {
        return new ReportedTaskState(id, false, timestamp);
    }

    @Id
    private String id;

    private String taskId;

    private ZonedDateTime deadline;

    private boolean done;

    private String text;

    private Instant timestamp;

    public ReportedTaskState() {
    }

    public ReportedTaskState(String taskId, ZonedDateTime deadline, String text, Instant timestamp) {
        this.taskId = taskId;
        this.deadline = deadline;
        this.text = text;
        this.timestamp = timestamp;
    }

    public ReportedTaskState(String taskId, ZonedDateTime deadline, boolean done, String text, Instant timestamp) {
        this.taskId = taskId;
        this.deadline = deadline;
        this.done = done;
        this.text = text;
        this.timestamp = timestamp;
    }

    private ReportedTaskState(String taskId, boolean done, Instant timestamp) {
        this.taskId = taskId;
        this.done = done;
        this.timestamp = timestamp;
    }

    public String getTaskId() {
        return taskId;
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

    public Instant getTimestamp() {
        return timestamp;
    }
}
