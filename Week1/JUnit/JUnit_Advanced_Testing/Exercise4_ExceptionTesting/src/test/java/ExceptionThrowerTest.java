import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionThrowerTest {

    ExceptionThrower exceptionThrower =
            new ExceptionThrower();

    @Test
    void testException() {

        IllegalArgumentException exception =

                assertThrows(

                        IllegalArgumentException.class,

                        () -> exceptionThrower.throwException()

                );

        assertEquals(

                "Invalid Input",

                exception.getMessage()

        );

    }

}