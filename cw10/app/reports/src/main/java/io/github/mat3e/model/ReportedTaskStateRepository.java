package io.github.mat3e.model;

import java.util.List;

public interface ReportedTaskStateRepository {
    ReportedTaskState save(ReportedTaskState snapshot);

    List<ReportedTaskState> findAll();
}
