package io.github.mat3e;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(PlansConfiguration.class)
class TestApp {
}
