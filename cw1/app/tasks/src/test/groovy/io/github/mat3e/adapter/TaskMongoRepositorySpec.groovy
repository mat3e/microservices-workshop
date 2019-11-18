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

    def 'should correctly save and read the deadline date'() {
        given:
        def zone = ZoneId.of('Europe/Warsaw')
        and: and: "a timestamp without nanoseconds as Mongo doesn't store them anyway"
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
}
