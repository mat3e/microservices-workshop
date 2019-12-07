package io.github.mat3e.model;

import java.time.Clock;

public class TaskUndone extends TaskEvent {
    private String id;

    TaskUndone(String id) {
        this(id, Clock.systemDefaultZone());
    }

    TaskUndone(String id, Clock clock) {
        super(clock);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
