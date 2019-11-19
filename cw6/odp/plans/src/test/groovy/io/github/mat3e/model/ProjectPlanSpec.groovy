package io.github.mat3e.model

import org.springframework.data.annotation.Id
import spock.lang.Specification

import java.time.ZoneId
import java.time.ZonedDateTime

class ProjectPlanSpec extends Specification {
    def 'should contain @Id'() {
        expect:
        ProjectPlan.declaredFields.findAll { field -> field.isAnnotationPresent(Id) }.size() == 1
    }

    def 'should contain deadline'() {
        given:
        def deadline = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"))

        when:
        def toTest = new ProjectPlan(deadline)

        then:
        toTest.deadline == deadline
    }
}
