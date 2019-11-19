package io.github.mat3e.sink;

import java.time.Instant;

class TaskDone {
    private Instant occurredOn;
    private String id;

    public TaskDone() {
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }

    public String getId() {
        return id;
    }
}
