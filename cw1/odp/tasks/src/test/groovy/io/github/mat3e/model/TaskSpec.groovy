package io.github.mat3e.model

import org.springframework.data.annotation.Id
import spock.lang.Specification

import java.time.ZoneId
import java.time.ZonedDateTime

class TaskSpec extends Specification {
    def 'should contain @Id'() {
        expect:
        Task.declaredFields.findAll { field -> field.isAnnotationPresent(Id) }.size() == 1
    }

    def 'should not be done after the creation'() {
        when:
        def result = new Task()

        then:
        !result.done
    }

    def 'can be created with deadline'() {
        given:
        def deadline = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"))

        when:
        def toTest = new Task(deadline)

        then:
        toTest.deadline == deadline
    }

    def 'should mark done as true'() {
        given:
        def toTest = new Task()

        when:
        toTest.finish()

        then:
        toTest.done
    }

    def 'should mark done as false'() {
        given:
        def toTest = new Task()
        toTest.finish()

        when:
        toTest.undo()

        then:
        !toTest.done
    }

    def 'should change state'() {
        given:
        def toTest = new Task()

        expect:
        !toTest.done

        when:
        toTest.change()

        then:
        toTest.done

        when:
        toTest.change()

        then:
        !toTest.done
    }
}
