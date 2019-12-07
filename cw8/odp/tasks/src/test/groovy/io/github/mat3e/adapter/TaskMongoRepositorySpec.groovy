package io.github.mat3e.adapter

import io.github.mat3e.model.Task
import org.bson.codecs.configuration.CodecConfigurationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import spock.lang.Specification

import java.time.ZoneId
import java.time.ZonedDateTime

@DataMongoTest
class TaskMongoRepositorySpec extends Specification {
    @Autowired
    TaskMongoRepository repository

    def setup() {
        repository.deleteAll()
    }

    def 'should correctly save and read the deadline date'() {
        given:
        def zone = ZoneId.of('Europe/Warsaw')
        and: "a timestamp without nanoseconds as Mongo doesn't store them anyway"
        def deadline = ZonedDateTime.now(zone).withNano(0)
        and:
        def toSave = new Task(deadline)

        when:
        repository.save(toSave)
        and:
        def toTest = repository.findAll()[0]

        then:
        notThrown CodecConfigurationException
        toTest.deadline.withZoneSameInstant(zone).withNano(0) == deadline
    }

    def 'should find only done tasks'() {
        given:
        repository.saveAll([
            newTask('foo'),
            newTask('bar', true),
            newTask('baz', true)
        ])

        expect:
        repository.count() == 3

        when:
        def result = repository.findAllByDone(true)

        then:
        result.size() == 2
    }

    def 'should find tasks before a given date'() {
        given:
        repository.saveAll([
            newTask('foo', false, ZonedDateTime.now().plusDays(7)),
            newTask('bar', true),
            newTask('baz', false, ZonedDateTime.now().minusDays(1))
        ])

        expect:
        repository.count() == 3

        when:
        def result = repository.findAllByDeadlineBeforeOrDeadlineNull(ZonedDateTime.now())

        then:
        result.size() == 2
    }

    def 'should find tasks before a given date and done'() {
        given:
        repository.saveAll([
            newTask('foo', false, ZonedDateTime.now().plusDays(7)),
            newTask('bar', true),
            newTask('baz', false, ZonedDateTime.now().minusDays(1))
        ])

        expect:
        repository.count() == 3

        when:
        def result = repository.findAllDoneByDeadline(ZonedDateTime.now())

        then:
        result.size() == 1
    }

    def 'should find tasks with id containing a given String'() {
        given:
        repository.saveAll([
            newTask('foo', false, null, 'foo'),
            newTask('bar', true, null, 'bar'),
            newTask('baz', false, null, 'baz')
        ])

        expect:
        repository.count() == 3

        when:
        def result = repository.findAllByIdContaining('ba')

        then:
        result.size() == 2
    }

    private static Task newTask(String text, boolean done = false, ZonedDateTime deadline = null, id = null) {
        def result = new Task(deadline)
        result.text = text
        if (done) result.change()
        if (id) result.id = id
        result
    }
}
