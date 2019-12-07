package io.github.mat3e;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("plans")
public class ConfigProps {
    private ExternalServiceUrls services;

    public ExternalServiceUrls getServices() {
        return services;
    }

    public void setServices(final ExternalServiceUrls services) {
        this.services = services;
    }

    public static class ExternalServiceUrls {
        private String tasksUrl;

        public String getTasksUrl() {
            return tasksUrl;
        }

        public void setTasksUrl(final String tasksUrl) {
            this.tasksUrl = tasksUrl;
        }
    }
}
