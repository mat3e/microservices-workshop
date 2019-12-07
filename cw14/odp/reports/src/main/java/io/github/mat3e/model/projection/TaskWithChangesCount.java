package io.github.mat3e.model.projection;

import io.github.mat3e.model.ReportedTaskState;

import java.time.Instant;
import java.util.Objects;

public final class TaskWithChangesCount {
    static TaskWithChangesCount initial(ReportedTaskState snapshot) {
        return new TaskWithChangesCount(snapshot.getText(), snapshot.isDone(), 0, snapshot.getTimestamp());
    }

    private String text;
    private boolean done;
    private int count;
    private Instant timestamp;

    private TaskWithChangesCount(String text, boolean done, int count, Instant timestamp) {
        this.text = text;
        this.done = done;
        this.count = count;
        this.timestamp = timestamp;
    }

    TaskWithChangesCount merge(TaskWithChangesCount snapshot) {
        boolean takeFromSnapshot = timestamp.isBefore(snapshot.timestamp);
        boolean laterDone = takeFromSnapshot ? snapshot.done : done;
        Instant laterTimestamp = takeFromSnapshot ? snapshot.timestamp : timestamp;
        return new TaskWithChangesCount(
                text == null ? snapshot.text : text,
                laterDone,
                count + 1,
                laterTimestamp
        );
    }

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return done;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskWithChangesCount)) return false;
        TaskWithChangesCount that = (TaskWithChangesCount) o;
        return done == that.done &&
                count == that.count &&
                text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, done, count);
    }
}
