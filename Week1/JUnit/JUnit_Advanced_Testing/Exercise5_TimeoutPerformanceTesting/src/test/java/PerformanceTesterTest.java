import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class PerformanceTesterTest {

    PerformanceTester tester = new PerformanceTester();

    @Test
    void testPerformance() {

        assertTimeout(

                Duration.ofSeconds(2),

                () -> tester.performTask()

        );

    }

}