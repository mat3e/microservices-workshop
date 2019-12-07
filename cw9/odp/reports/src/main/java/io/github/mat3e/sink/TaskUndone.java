package io.github.mat3e.sink;

import java.time.Instant;

class TaskUndone {
    private Instant occurredOn;
    private String id;

    public TaskUndone() {
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }

    public String getId() {
        return id;
    }
}
