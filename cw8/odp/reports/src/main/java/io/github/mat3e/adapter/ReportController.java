package io.github.mat3e.adapter;

import io.github.mat3e.model.ReportedTaskStateRepository;
import io.github.mat3e.model.projection.DoneTask;
import io.github.mat3e.model.projection.ReportedTaskMapper;
import io.github.mat3e.model.projection.TaskWithChangesCount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
class ReportController {
    private ReportedTaskStateRepository repository;

    public ReportController(ReportedTaskStateRepository repository) {
        this.repository = repository;
    }

    @GetMapping("count")
    ResponseEntity<List<TaskWithChangesCount>> readTasksWithChangesCount() {
        return ResponseEntity.ok(ReportedTaskMapper.toChangesCount(repository.findAll()));
    }

    @GetMapping("done")
    ResponseEntity<List<DoneTask>> readDoneTasks() {
        return ResponseEntity.ok(ReportedTaskMapper.toDone(repository.findAll()));
    }
}
