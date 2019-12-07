package io.github.mat3e.adapter;

import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
interface TaskMongoRepository extends TaskRepository, MongoRepository<Task, String> {
    List<Task> findAllByDone(boolean done);

    List<Task> findAllByIdContaining(String sequence);

    List<Task> findAllByDeadlineBeforeOrDeadlineNull(ZonedDateTime dateTime);

    default List<Task> findAllDoneByDeadline(ZonedDateTime dateTime) {
        return findAllByDeadlineBeforeAndDoneOrDeadlineNullAndDone(dateTime, true, true);
    }

    List<Task> findAllByDeadlineBeforeAndDoneOrDeadlineNullAndDone(ZonedDateTime dateTime, boolean done, boolean doneNoDeadline);
}
