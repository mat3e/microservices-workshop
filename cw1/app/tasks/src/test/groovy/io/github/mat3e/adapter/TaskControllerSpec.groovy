package io.github.mat3e.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.mat3e.model.Task
import io.github.mat3e.model.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll
import spock.mock.DetachedMockFactory

import java.time.ZonedDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@WebMvcTest(TaskController)
class TaskControllerSpec extends Specification {
    // TODO: proper address
    static final def URL = '/api'

    @Autowired
    MockMvc mockMvc

    @Autowired
    TaskRepository repositoryStub

    @Autowired
    ObjectMapper mapper

    def 'should create and return a new task'() {
        given:
        def id = 'testId'
        def date = ZonedDateTime.now().toInstant().toEpochMilli()
        repositoryStub.insert(_ as Task) >> { Task task ->
            task.id = id
            return task
        }

        when:
        def response = mockMvc.perform(
            // TODO: method and URL
            MockMvcRequestBuilders.(URL)
                .content(asJson([
                    deadline: date,
                    text    : 'do stuff'
                ]))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn().response

        then:
        // TODO: status
        response.status == HttpStatus.
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
        repositoryStub.findAll() >> [
            exampleTask('foo', date),
            exampleTask('bar')
        ]

        when:
        // TODO: method and URL
        def response = mockMvc.perform MockMvcRequestBuilders.(URL) andReturn() response

        then:
        // TODO: status
        response.status == HttpStatus.
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
        repositoryStub.findById(taskId) >> repositoryResult

        when:
        // TODO: method and URL
        def response = mockMvc.perform MockMvcRequestBuilders.(URL) andReturn() response

        then:
        response.status == expectedStatus

        where:
        // TODO: statuses
        testCase            | repositoryResult        | expectedStatus
        'not existing task' | Optional.empty()        | HttpStatus.
        'existing task'     | Optional.of(new Task()) | HttpStatus.
    }

    @Unroll
    def 'should return #expectedStatus when updating #testCase'() {
        given:
        def taskId = 'id'
        repositoryStub.findById(taskId) >> repositoryResult

        when:
        def response = mockMvc.perform(
            // TODO: method and URL
            MockMvcRequestBuilders.(URL)
                .content(asJson([
                    id      : idInBody,
                    deadline: ZonedDateTime.now().toInstant().toEpochMilli(),
                ]))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn().response

        then:
        response.status == expectedStatus

        where:
        // TODO: statuses
        testCase                 | idInBody    | repositoryResult        | expectedStatus
        'not existing task'      | 'id'        | Optional.empty()        | HttpStatus.
        'existing task'          | 'id'        | Optional.of(new Task()) | HttpStatus.
        'task with different id' | 'different' | Optional.of(new Task()) | HttpStatus.
    }

    def 'should return 204 when deleting a resource'() {
        when:
        // TODO: method and URL
        def response = mockMvc.perform MockMvcRequestBuilders.(URL) andReturn() response

        then:
        // TODO: status
        response.status == HttpStatus.
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
        TaskRepository repository() {
            detachedMockFactory.Stub(TaskRepository)
        }
    }
}
