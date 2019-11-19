package io.github.mat3e.model;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task insert(Task entity);

    List<Task> findAll();

    Optional<Task> findById(String id);

    Task save(Task entity);

    void deleteById(String id);
}
