package io.github.mat3e.sink;

import java.time.Instant;
import java.time.ZonedDateTime;

class TaskCreated {
    private Instant occurredOn;
    private String id;
    private ZonedDateTime deadline;
    private String text;

    public TaskCreated() {
    }

    public Instant getOccurredOn() {
        return occurredOn;
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
}
