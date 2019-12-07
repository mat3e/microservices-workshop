package io.github.mat3e.adapter;

import io.github.mat3e.ConfigProps;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/plans/{planId}/tasks")
class TaskController {
    private ProjectPlanRepository repository;
    private TaskClient client;

    public TaskController(ProjectPlanRepository repository, TaskClient client) {
        this.repository = repository;
        this.client = client;
    }

    @PutMapping
    ResponseEntity<List<String>> createTasksFromPlan(@PathVariable String planId) {
        return repository.findById(planId)
                .map(plan -> Stream.concat(
                        Stream.of(new TaskDto(plan.getName(), plan.getDeadline())),
                        plan.getProjectSteps().stream().map(
                                planStep -> new TaskDto(
                                        planStep.getDescription(),
                                        plan.getDeadline()
                                                .plusDays(planStep.getDaysToProjectDeadline())
                                )
                        )).map(client::sendTask)
                        .map(TaskDto::getId)
                        .collect(toList())
                ).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
