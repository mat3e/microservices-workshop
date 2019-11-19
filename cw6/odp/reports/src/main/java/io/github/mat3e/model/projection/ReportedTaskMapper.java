package io.github.mat3e.model.projection;

import io.github.mat3e.model.ReportedTaskState;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class ReportedTaskMapper {
    public static List<DoneTask> toDone(List<ReportedTaskState> taskSnapshots) {
        return taskSnapshots.stream()
                .sorted(comparing(ReportedTaskState::getTimestamp))
                .collect(toMap(
                        ReportedTaskState::getTaskId,
                        snap -> snap,
                        (snap1, snap2) -> {
                            var later = snap1.getTimestamp().isBefore(snap2.getTimestamp()) ? snap2 : snap1;
                            var withText = snap1.getText() == null ? snap2 : snap1;
                            return new ReportedTaskState(
                                    withText.getTaskId(),
                                    withText.getDeadline(),
                                    later.isDone(),
                                    withText.getText(),
                                    later.getTimestamp()
                            );
                        }
                )).values().stream()
                .filter(ReportedTaskState::isDone)
                .map(DoneTask::new)
                .collect(toList());
    }

    public static List<TaskWithChangesCount> toChangesCount(List<ReportedTaskState> taskSnapshots) {
        return new ArrayList<>(
                taskSnapshots.stream()
                        .collect(toMap(
                                ReportedTaskState::getTaskId,
                                TaskWithChangesCount::initial,
                                TaskWithChangesCount::merge
                        )).values()
        );
    }
}
