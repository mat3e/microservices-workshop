package io.github.mat3e.model;

import java.time.Clock;
import java.time.Instant;

public abstract class TaskEvent {
    private Instant occurrence;

    TaskEvent(Clock clock) {
        this.occurrence = Instant.now(clock);
    }

    public Instant getOccurredOn() {
        return occurrence;
    }

    public abstract String getType();
}
