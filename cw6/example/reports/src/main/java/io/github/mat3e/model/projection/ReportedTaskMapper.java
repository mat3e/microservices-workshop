package io.github.mat3e.model.projection;

import io.github.mat3e.model.ReportedTaskState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class ReportedTaskMapper {
    public static List<DoneTask> toDone(List<ReportedTaskState> taskSnapshots) {
        return emptyList();
    }

    public static List<TaskWithChangesCount> toChangesCount(List<ReportedTaskState> taskSnapshots) {
        return emptyList();
    }
}
