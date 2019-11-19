package io.github.mat3e;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableBinding(Source.class)
public class TasksApplication {
    public static void main(String[] args) {
        SpringApplication.run(TasksApplication.class, args);
    }

    @Bean
    ObjectMapper configuredObjectMapper() {
        return new ObjectMapper()
                .registerModules(new Jdk8Module(), new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    }

    @Bean
    MongoCustomConversions mongoConversions() {
        return new MongoCustomConversions(List.of(
                // no lambdas for detailed type definitions
                new Converter<Date, ZonedDateTime>() {
                    @Override
                    public ZonedDateTime convert(Date date) {
                        return date.toInstant().atZone(ZoneOffset.UTC);
                    }
                },
                new Converter<ZonedDateTime, Date>() {
                    @Override
                    public Date convert(ZonedDateTime source) {
                        return Date.from(source.toInstant());
                    }
                }
        ));
    }
}
