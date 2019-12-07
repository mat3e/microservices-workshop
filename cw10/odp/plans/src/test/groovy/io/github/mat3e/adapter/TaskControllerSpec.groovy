package io.github.mat3e.adapter

import io.github.mat3e.ConfigProps
import io.github.mat3e.model.ProjectPlan
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import java.time.ZonedDateTime

class TaskControllerSpec extends Specification {
    def 'should create tasks from plan and its milestones'() {
        given:
        RestTemplate client = Mock()
        and:
        def deadline = ZonedDateTime.now()
        ProjectPlanRepository repository = 'repository returning "foo" with milestones "bar", "baz", "abc", -3, 0 and 1 day from' deadline
        ConfigProps props = 'client returning hardcoded URL "url"'()

        when:
        def result = new TaskController(repository, client, props).createTasksFromPlan('id').body

        then:
        1 * client.postForObject('url', new TaskDto('foo', deadline), TaskDto) >> 'task with id'('a')
        then:
        1 * client.postForObject('url', new TaskDto('bar', deadline.plusDays(-3)), TaskDto) >> 'task with id'('b')
        then:
        1 * client.postForObject('url', new TaskDto('baz', deadline), TaskDto) >> 'task with id'('c')
        then:
        1 * client.postForObject('url', new TaskDto('abc', deadline.plusDays(1)), TaskDto) >> 'task with id'('d')
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

    private ConfigProps 'client returning hardcoded URL "url"'() {
        Stub(ConfigProps) {
            getServices() >> Mock(ConfigProps.ExternalServiceUrls) {
                getTasksUrl() >> 'url'
            }
        }
    }

    private static TaskDto 'task with id'(String id) {
        new TaskDto(id, '', ZonedDateTime.now())
    }
}
