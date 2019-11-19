package io.github.mat3e.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

public class ProjectPlan {
    @Id
    private String id;
    @NotBlank(message = "Project name is required")
    private String name;
    private ZonedDateTime deadline;
    private Set<Milestone> projectSteps = new HashSet<>();

    // used by ObjectMapper
    @SuppressWarnings("unused")
    ProjectPlan() {
    }

    public ProjectPlan(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    // used by ObjectMapper
    @SuppressWarnings("unused")
    public String getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public List<Milestone> getProjectSteps() {
        return projectSteps.stream()
                .sorted(comparingInt(m -> m.daysToProjectDeadline))
                .collect(toList());
    }

    public static class Milestone {
        private String description;
        private int daysToProjectDeadline;

        @JsonCreator
        public Milestone(
                @JsonProperty("description") String description,
                @JsonProperty("daysToProjectDeadline") int daysToProjectDeadline
        ) {
            this.description = description;
            this.daysToProjectDeadline = daysToProjectDeadline;
        }

        @SuppressWarnings("unused")
        public String getDescription() {
            return description;
        }

        @SuppressWarnings("unused")
        public int getDaysToProjectDeadline() {
            return daysToProjectDeadline;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Milestone)) return false;
            Milestone milestone = (Milestone) o;
            return daysToProjectDeadline == milestone.daysToProjectDeadline &&
                    description.equals(milestone.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(description, daysToProjectDeadline);
        }
    }
}
