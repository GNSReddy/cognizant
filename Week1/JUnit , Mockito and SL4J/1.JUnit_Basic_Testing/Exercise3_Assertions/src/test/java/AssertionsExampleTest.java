import org.junit.Test;

import static org.junit.Assert.*;

public class AssertionsExampleTest {

    AssertionsExample obj = new AssertionsExample();

    @Test
    public void testAssertions() {

        // Assert Equals
        assertEquals(5, obj.add(2,3));

        // Assert True
        assertTrue(obj.isEligible(20));

        // Assert False
        assertFalse(obj.isEligible(15));

        // Assert Null
        assertNull(obj.getNullValue());

        // Assert Not Null
        assertNotNull(obj.getName());

    }

}