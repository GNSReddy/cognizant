package com.cognizant.ormlearn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * OrmLearnApplicationTests - Default Spring Boot test class.
 * Verifies that the Spring ApplicationContext loads successfully.
 *
 * Handbook: src/test/java folder - for testing the application
 */
@SpringBootTest
class OrmLearnApplicationTests {

    @Test
    void contextLoads() {
        // Verifies that the Spring ApplicationContext starts without errors.
        // Hibernate will validate the country table schema on test startup.
    }

}
