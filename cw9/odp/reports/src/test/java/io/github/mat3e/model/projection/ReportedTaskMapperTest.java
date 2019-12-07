package io.github.mat3e.model.projection;

import io.github.mat3e.model.ReportedTaskState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

class ReportedTaskMapperTest {
    @Test
    @DisplayName("should return just done tasks")
    void mapOnlyDoneTasks() {
        // given
        String id = "first", text = "Test projection";
        String id2 = "second", text2 = "Add more test cases";
        String id3 = "third", text3 = "Finish this test";
        // and
        var input = List.of(
                taskCreatedYesterdayForTomorrow(id, text),
                ReportedTaskState.done(id, Instant.now()),
                ReportedTaskState.undone(id, Instant.now().plus(1, ChronoUnit.HOURS)),
                taskCreatedYesterdayForTomorrow(id2, text2),
                ReportedTaskState.done(id2, Instant.now()),
                taskCreatedYesterdayForTomorrow(id3, text3),
                ReportedTaskState.done(id3, Instant.now().plus(3, ChronoUnit.DAYS))
        );

        // when
        List<DoneTask> doneTasks = ReportedTaskMapper.toDone(input);

        // then
        assertThat(doneTasks).extracting(DoneTask::getText).containsExactlyInAnyOrder(text2, text3);
        // and
        assertThat(doneTasks)
                .filteredOn(task -> task.getText().equals(text2))
                .first()
                .extracting(DoneTask::isDoneBeforeDeadline)
                .isEqualTo(true);
        assertThat(doneTasks)
                .filteredOn(task -> task.getText().equals(text3))
                .first()
                .extracting(DoneTask::isDoneBeforeDeadline)
                .isEqualTo(false);
    }

    @Test
    @DisplayName("should return tasks with their number of changes")
    void mapIntoTasksWithNumberOfChanges() {
        // given
        String id = "first", text = "Test projection";
        String id2 = "second", text2 = "Add more test cases";
        String id3 = "third", text3 = "Finish this test";
        // and
        var input = List.of(
                taskCreatedYesterdayForTomorrow(id, text),
                ReportedTaskState.done(id, Instant.now()),
                ReportedTaskState.undone(id, Instant.now().plus(1, ChronoUnit.HOURS)),
                taskCreatedYesterdayForTomorrow(id2, text2),
                ReportedTaskState.done(id2, Instant.now()),
                taskCreatedYesterdayForTomorrow(id3, text3),
                ReportedTaskState.done(id3, Instant.now()),
                ReportedTaskState.undone(id3, Instant.now().plusSeconds(10)),
                ReportedTaskState.done(id3, Instant.now().plusSeconds(20))
        );

        // when
        List<TaskWithChangesCount> mappedTasks = ReportedTaskMapper.toChangesCount(input);
        // and
        mappedTasks.sort(comparing(TaskWithChangesCount::getCount));

        // then
        assertThat(mappedTasks).extracting(TaskWithChangesCount::getCount).containsExactly(1, 2, 3);
        assertThat(mappedTasks).extracting(TaskWithChangesCount::getText).containsExactly(text2, text, text3);
        assertThat(mappedTasks).extracting(TaskWithChangesCount::isDone).containsExactly(true, false, true);
    }

    private static ReportedTaskState taskCreatedYesterdayForTomorrow(String id, String text) {
        return new ReportedTaskState(id, ZonedDateTime.now().plusDays(1), text, Instant.now().minus(1, ChronoUnit.DAYS));
    }
}
