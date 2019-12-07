package io.github.mat3e.adapter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.Objects;

class TaskDto {
    private String id;
    private String text;
    private ZonedDateTime deadline;

    @JsonCreator
    TaskDto(
            @JsonProperty("id") String id,
            @JsonProperty("text") String text,
            @JsonProperty("deadline") ZonedDateTime deadline
    ) {
        this(text, deadline);
        this.id = id;
    }

    TaskDto(String text, ZonedDateTime deadline) {
        this.text = text;
        this.deadline = deadline;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskDto)) return false;
        final TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id) &&
                Objects.equals(text, taskDto.text) &&
                Objects.equals(deadline, taskDto.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, deadline);
    }
}
