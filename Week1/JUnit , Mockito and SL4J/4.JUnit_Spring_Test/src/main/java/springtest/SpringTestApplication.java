package springtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "springtest", "exercise1", "exercise2", "exercise3", 
    "exercise4", "exercise5", "exercise6", "exercise7", "exercise8", "exercise9"
})
@EnableJpaRepositories(basePackages = {
    "springtest", "exercise1", "exercise2", "exercise3", 
    "exercise4", "exercise5", "exercise6", "exercise7", "exercise8", "exercise9"
})
@EntityScan(basePackages = {
    "springtest", "exercise1", "exercise2", "exercise3", 
    "exercise4", "exercise5", "exercise6", "exercise7", "exercise8", "exercise9"
})
public class SpringTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class, args);
    }
}
