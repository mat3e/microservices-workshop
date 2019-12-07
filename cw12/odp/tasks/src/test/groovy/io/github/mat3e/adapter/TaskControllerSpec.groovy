package io.github.mat3e.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.mat3e.model.Task
import io.github.mat3e.process.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll
import spock.mock.DetachedMockFactory

import java.time.ZonedDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(TaskController)
@TestPropertySource(properties = ['spring.cloud.consul.config.enabled = false'])
class TaskControllerSpec extends Specification {
    static final def URL = '/api/tasks'

    @Autowired
    MockMvc mockMvc

    @Autowired
    TaskService serviceStub

    @Autowired
    ObjectMapper mapper

    def 'should create and return a new task'() {
        given:
        def id = 'testId'
        def date = ZonedDateTime.now().toInstant().toEpochMilli()
        serviceStub.create(_ as Task) >> { Task task ->
            task.id = id
            return task
        }

        when:
        def response = mockMvc.perform(
            post(URL)
                .content(asJson([
                    deadline: date,
                    text    : 'do stuff'
                ]))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn().response

        then:
        response.status == HttpStatus.CREATED.value()
        and:
        response.getHeaderValue(HttpHeaders.LOCATION) == "/$id"
        and:
        with(new ObjectMapper().readValue(response.contentAsString, Map)) {
            it.id == id
            it.deadline == date
            it.text == 'do stuff'
            it.done == false
        }
    }

    def 'should read all the tasks'() {
        given:
        def date = ZonedDateTime.now()
        def epoch = date.toInstant().toEpochMilli()
        serviceStub.readAll() >> [
            exampleTask('foo', date),
            exampleTask('bar')
        ]

        when:
        def response = mockMvc.perform get(URL) andReturn() response

        then:
        response.status == HttpStatus.OK.value()
        and:
        with(mapper.readValue(response.contentAsString, List) as List<Map>) {
            it.size() == 2
            it[0].id == 'foo'
            it[0].deadline == epoch
            it[1].id == 'bar'
        }
    }

    @Unroll
    def 'should return #expectedStatus when reading #testCase'() {
        given:
        def taskId = 'id'
        serviceStub.read(taskId) >> repositoryResult

        when:
        def response = mockMvc.perform get("$URL/$taskId") andReturn() response

        then:
        response.status == expectedStatus

        where:
        testCase            | repositoryResult        | expectedStatus
        'not existing task' | Optional.empty()        | HttpStatus.NOT_FOUND.value()
        'existing task'     | Optional.of(new Task()) | HttpStatus.OK.value()
    }

    @Unroll
    def 'should return #expectedStatus when changing #testCase'() {
        given:
        def taskId = 'id'
        serviceStub.changeState(taskId) >> repositoryResult

        when:
        def response = mockMvc.perform patch("$URL/$taskId") andReturn() response

        then:
        response.status == expectedStatus

        where:
        testCase                 | idInBody    | repositoryResult        | expectedStatus
        'not existing task'      | 'id'        | Optional.empty()        | HttpStatus.NOT_FOUND.value()
        'existing task'          | 'id'        | Optional.of(new Task()) | HttpStatus.NO_CONTENT.value()
    }

    def 'should return 204 when deleting a resource'() {
        when:
        def response = mockMvc.perform delete("$URL/123") andReturn() response

        then:
        response.status == HttpStatus.NO_CONTENT.value()
    }

    private String asJson(Map requestBody) {
        mapper.writeValueAsString(requestBody)
    }

    private static Task exampleTask(String id, ZonedDateTime date = null) {
        def result = new Task(date)
        result.text = 'example text'
        result.id = id
        result
    }

    @TestConfiguration
    static class StubConfiguration {
        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        TaskService service() {
            detachedMockFactory.Stub(TaskService)
        }
    }
}
