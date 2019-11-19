package io.github.mat3e.adapter;

import io.github.mat3e.model.ReportedTaskState;
import io.github.mat3e.model.ReportedTaskStateRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ReportedTaskStateMongoRepository extends ReportedTaskStateRepository, MongoRepository<ReportedTaskState, String> {
}
