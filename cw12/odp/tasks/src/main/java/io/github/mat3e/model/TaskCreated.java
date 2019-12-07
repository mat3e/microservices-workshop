package io.github.mat3e.model;

import java.time.Clock;
import java.time.ZonedDateTime;

public class TaskCreated extends TaskEvent {
    public static final String TYPE = "created";

    private String id;
    private ZonedDateTime deadline;
    private String text;

    public TaskCreated(Task source) {
        this(source, Clock.systemDefaultZone());
    }

    TaskCreated(Task source, Clock clock) {
        super(clock);
        this.id = source.getId();
        this.deadline = source.getDeadline();
        this.text = source.getText();
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public String getText() {
        return text;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
