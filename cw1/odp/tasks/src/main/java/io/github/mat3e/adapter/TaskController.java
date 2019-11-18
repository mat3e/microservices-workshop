package io.github.mat3e.adapter;

import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
class TaskController {
    private TaskRepository repository;

    TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody Task toCreate) {
        Task inserted = repository.insert(toCreate);
        return ResponseEntity
                .created(URI.create("/" + inserted.getId()))
                .body(inserted);
    }

    @GetMapping
    ResponseEntity<List<Task>> readAllTasks() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{taskId}")
    ResponseEntity<Task> readTask(@PathVariable String taskId) {
        return repository.findById(taskId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{taskId}")
    ResponseEntity<Task> updateTask(@PathVariable String taskId, @RequestBody Task toUpdate) {
        Optional<Task> existingTask = repository.findById(taskId);
        if (existingTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (differentId(toUpdate, taskId)) {
            return ResponseEntity.badRequest().build();
        }
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{taskId}")
    ResponseEntity<Task> deleteTask(@PathVariable String taskId) {
        repository.deleteById(taskId);
        return ResponseEntity.noContent().build();
    }

    private boolean differentId(Task task, String existingId) {
        return !existingId.equals(task.getId());
    }
}
