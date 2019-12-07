package io.github.mat3e.model;

import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;

public abstract class TaskEvent {
    private Instant occurrence;

    TaskEvent(Clock clock) {
        this.occurrence = Instant.now(clock);
    }

    public final Instant getOccurredOn() {
        return occurrence;
    }
}
