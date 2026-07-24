package exercise9;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceTest {
    private final CalculatorService service = new CalculatorService();

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2",
            "10, 20, 30",
            "-5, 5, 0"
    })
    public void testAddParameterized(int a, int b, int expected) {
        assertEquals(expected, service.add(a, b));
    }
}
