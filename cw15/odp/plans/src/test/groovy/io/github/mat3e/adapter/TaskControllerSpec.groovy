package io.github.mat3e.adapter

import io.github.mat3e.ConfigProps
import io.github.mat3e.model.ProjectPlan
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import java.time.ZonedDateTime

class TaskControllerSpec extends Specification {
    def 'should create tasks from plan and its milestones'() {
        given:
        TaskClient client = Mock()
        and:
        def deadline = ZonedDateTime.now()
        ProjectPlanRepository repository = 'repository returning "foo" with milestones "bar", "baz", "abc", -3, 0 and 1 day from' deadline

        when:
        def result = new TaskController(repository, client).createTasksFromPlan('id').body

        then:
        1 * client.sendTask(new TaskDto('foo', deadline)) >> 'task with id'('a')
        then:
        1 * client.sendTask(new TaskDto('bar', deadline.plusDays(-3))) >> 'task with id'('b')
        then:
        1 * client.sendTask(new TaskDto('baz', deadline)) >> 'task with id'('c')
        then:
        1 * client.sendTask(new TaskDto('abc', deadline.plusDays(1))) >> 'task with id'('d')
        and:
        result == ['a', 'b', 'c', 'd']
    }

    private ProjectPlanRepository 'repository returning "foo" with milestones "bar", "baz", "abc", -3, 0 and 1 day from'(ZonedDateTime deadline) {
        def plan = new ProjectPlan(deadline)
        plan.name = 'foo'
        plan.projectSteps = [
            new ProjectPlan.Milestone('bar', -3),
            new ProjectPlan.Milestone('baz', 0),
            new ProjectPlan.Milestone('abc', 1)
        ]
        Mock(ProjectPlanRepository) {
            1 * findById(_ as String) >> Optional.of(plan)
        }
    }

    private static TaskDto 'task with id'(String id) {
        new TaskDto(id, '', ZonedDateTime.now())
    }
}
