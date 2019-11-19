package io.github.mat3e.adapter;

import io.github.mat3e.model.Task;
import io.github.mat3e.process.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
class TaskController {
    private TaskService process;

    TaskController(TaskService process) {
        this.process = process;
    }

    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody Task toCreate) {
        Task inserted = process.create(toCreate);
        return ResponseEntity
                .created(URI.create("/" + inserted.getId()))
                .body(inserted);
    }

    @GetMapping
    ResponseEntity<List<Task>> readAllTasks() {
        return ResponseEntity.ok(process.readAll());
    }

    @GetMapping("/{taskId}")
    ResponseEntity<Task> readTask(@PathVariable String taskId) {
        return process.read(taskId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{taskId}")
    ResponseEntity<Task> updateTask(@PathVariable String taskId) {
        Optional<Task> updatedTask = process.changeState(taskId);
        if (updatedTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{taskId}")
    ResponseEntity<Task> deleteTask(@PathVariable String taskId) {
        process.delete(taskId);
        return ResponseEntity.noContent().build();
    }

    private boolean differentId(Task task, String existingId) {
        return !existingId.equals(task.getId());
    }
}
