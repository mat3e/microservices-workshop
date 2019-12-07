package io.github.mat3e.model.projection;

import io.github.mat3e.model.ReportedTaskState;

import java.util.Objects;

public final class DoneTask {
    private String text;
    private boolean doneBeforeDeadline;

    DoneTask(ReportedTaskState input) {
        if (!input.isDone()) {
            throw new IllegalArgumentException("Task state should represent done task");
        }
        text = input.getText();
        doneBeforeDeadline = input.getDeadline() == null || input.getTimestamp().isBefore(input.getDeadline().toInstant());
    }

    public String getText() {
        return text;
    }

    public boolean isDoneBeforeDeadline() {
        return doneBeforeDeadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoneTask)) return false;
        DoneTask doneTask = (DoneTask) o;
        return doneBeforeDeadline == doneTask.doneBeforeDeadline &&
                text.equals(doneTask.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, doneBeforeDeadline);
    }
}
