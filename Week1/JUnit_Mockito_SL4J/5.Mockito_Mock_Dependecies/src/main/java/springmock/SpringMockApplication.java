package springmock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "springmock", "exercise1", "exercise2", "exercise3"
})
@EnableJpaRepositories(basePackages = {
    "springmock", "exercise1", "exercise2", "exercise3"
})
@EntityScan(basePackages = {
    "springmock", "exercise1", "exercise2", "exercise3"
})
public class SpringMockApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringMockApplication.class, args);
    }
}
