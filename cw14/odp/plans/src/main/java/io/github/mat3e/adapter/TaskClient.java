package io.github.mat3e.adapter;

import io.github.mat3e.ConfigProps;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class TaskClient {
    private static final Logger logger = LoggerFactory.getLogger(TaskClient.class);

    private RestTemplate client;
    private String tasksUrl;

    public TaskClient(RestTemplate client, ConfigProps props) {
        this.tasksUrl = props.getServices().getTasksUrl();
        this.client = client;
    }

    @CircuitBreaker(name = "tasks", fallbackMethod = "fallback")
    TaskDto sendTask(TaskDto taskToSend) {
        return client.postForObject(tasksUrl, taskToSend, TaskDto.class);
    }

    private TaskDto fallback(TaskDto toSend, Exception e) {
        logger.warn("Couldn't send the task: {}, returning empty", e.getMessage());
        return toSend;
    }
}
