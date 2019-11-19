package io.github.mat3e.model.projection;

import io.github.mat3e.model.ReportedTaskState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoneTaskTest {
    @Test
    @DisplayName("should throw when creating from undone task")
    void throwWhenUndone() {
        assertThrows(IllegalArgumentException.class, () -> new DoneTask(ReportedTaskState.undone("id", Instant.now())));
    }

    @Test
    @DisplayName("should be done before the deadline when no deadline")
    void createAsDoneBeforeDeadline() {
        // given
        var reported = new ReportedTaskState("id", null, true, "text", Instant.now());

        // when
        var result = new DoneTask(reported);

        // then
        assertThat(result.isDoneBeforeDeadline()).isTrue();
    }
}
