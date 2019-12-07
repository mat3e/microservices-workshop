package io.github.mat3e.adapter


import io.github.mat3e.model.ProjectPlan
import org.bson.codecs.configuration.CodecConfigurationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import spock.lang.Specification

import javax.validation.ConstraintViolationException
import java.time.ZoneId
import java.time.ZonedDateTime

@DataMongoTest
class ProjectPlanRepositorySpec extends Specification {
    @Autowired
    ProjectPlanRepository repository

    def 'should autowire repository'() {
        expect:
        repository != null
    }

    def 'should not save null name'() {
        given:
        def toSave = new ProjectPlan(ZonedDateTime.now())

        when:
        repository.save(toSave)

        then:
        thrown ConstraintViolationException
    }

    def 'should correctly save and read the deadline date'() {
        given:
        def zone = ZoneId.of('Europe/Warsaw')
        and: "a timestamp without nanoseconds as Mongo doesn't store them anyway"
        def deadline = ZonedDateTime.now(zone).withNano(0)
        and:
        def toSave = new ProjectPlan(deadline)
        toSave.name = 'test name'

        when:
        repository.save(toSave)
        and:
        def toTest = repository.findAll()[0]

        then:
        notThrown CodecConfigurationException
        toTest.deadline.withZoneSameInstant(zone).withNano(0) == deadline
    }
}
