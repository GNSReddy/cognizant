import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderedTests {

    MessageService service = new MessageService();

    @Test
    @Order(1)
    void testMessage() {

        System.out.println("Test 1 Executed");

        assertEquals("Welcome to JUnit 5",
                service.getMessage());

    }

    @Test
    @Order(2)
    void testSecond() {

        System.out.println("Test 2 Executed");

    }

    @Test
    @Order(3)
    void testThird() {

        System.out.println("Test 3 Executed");

    }

}