package exercise1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceTest {
    private final CalculatorService service = new CalculatorService();

    @Test
    public void testAdd() {
        assertEquals(8, service.add(5, 3));
    }
}
