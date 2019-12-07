package io.github.mat3e;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class PlansApplication implements RepositoryRestConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(PlansApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
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

    @Bean
    LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean validator) {
        return new ValidatingMongoEventListener(validator);
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        Validator validator = validator();
        validatingListener
                .addValidator("beforeCreate", validator);
    }

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        objectMapper
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    }
}
