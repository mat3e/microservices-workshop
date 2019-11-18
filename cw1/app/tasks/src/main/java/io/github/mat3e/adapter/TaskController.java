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
// TODO: url
@RequestMapping("/api")
class TaskController {
    private TaskRepository repository;

    TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    // TODO: RequestMapping, passing the parameters, defining response URI
    ResponseEntity<Task> createTask(Task toCreate) {
        Task inserted = repository.insert(toCreate);
        return ResponseEntity
                // TODO: is this OK?
                .created(URI.create("/" + inserted))
                .body(inserted);
    }

    // TODO: RequestMapping or related annotation
    ResponseEntity<List<Task>> readAllTasks() {
        return ResponseEntity.ok(repository.findAll());
    }

    // TODO: RequestMapping and passing the parameter, decide on the response
    ResponseEntity<Task> readTask(String taskId) {
        return repository.findById(taskId)
                .map(ResponseEntity::ok)
                // TODO: 204 or 404
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // TODO: RequestMapping, passing the parameter, decide on the response
    ResponseEntity<Task> updateTask(String taskId, Task toUpdate) {
        Optional<Task> existingTask = repository.findById(taskId);
        if (existingTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (differentId(toUpdate, taskId)) {
            return ResponseEntity.badRequest().build();
        }
        repository.save(toUpdate);
        // TODO: is no content OK?
        return ResponseEntity.noContent().build();
    }

    // TODO: RequestMapping, passing the parameter, decide on the response
    ResponseEntity<Task> deleteTask(String taskId) {
        repository.deleteById(taskId);
        // TODO: no content or something different?
        return ResponseEntity.noContent().build();
    }

    private boolean differentId(Task task, String existingId) {
        return !existingId.equals(task.getId());
    }
}
