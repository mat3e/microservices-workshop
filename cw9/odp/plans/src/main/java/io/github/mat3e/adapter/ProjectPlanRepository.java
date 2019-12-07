package io.github.mat3e.adapter;

import io.github.mat3e.model.ProjectPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "plans", collectionResourceRel = "plans")
interface ProjectPlanRepository extends MongoRepository<ProjectPlan, String> {
}
