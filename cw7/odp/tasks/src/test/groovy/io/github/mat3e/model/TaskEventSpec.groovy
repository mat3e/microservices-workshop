package io.github.mat3e.model

import spock.lang.Specification

import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class TaskEventSpec extends Specification {
    def 'should return creation event from task'() {
        given:
        def clock = referenceClock()
        def task = exampleTask()

        when:
        def event = new TaskCreated(task, clock)

        then:
        event.id == task.id
        event.text == task.text
        event.deadline == task.deadline
        and:
        event.occurredOn == clock.instant()
    }

    def 'should return event with task id'() {
        given:
        def clock = referenceClock()

        when:
        def done = new TaskDone('id', clock)
        def undone = new TaskUndone('id', clock)

        then:
        done.id == undone.id
        and:
        done.occurredOn == clock.instant()
        undone.occurredOn == clock.instant()
    }

    private static Clock referenceClock() {
        Clock.fixed(Instant.now(), ZoneOffset.UTC)
    }

    private static Task exampleTask() {
        def result = new Task()
        result.id = 'id'
        result.text = 'important'
        result
    }
}
