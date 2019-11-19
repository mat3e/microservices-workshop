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

    def 'should mark done as true and return TaskDone event with id'() {
        given:
        def toTest = new Task()
        toTest.id = 'foo'

        when:
        def event = toTest.finish()

        then:
        toTest.done
        and:
        event instanceof TaskDone
        event.id == toTest.id
    }

    def 'should mark done as false and return TaskUndone event with id'() {
        given:
        def toTest = new Task()
        toTest.id = 'foo'
        and:
        toTest.finish()

        when:
        def event = toTest.undo()

        then:
        !toTest.done
        and:
        event instanceof TaskUndone
        event.id == toTest.id
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
