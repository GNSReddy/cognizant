package exercise5;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MyServiceTest {
    @Test
    public void testMultipleReturns() {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("First").thenReturn("Second");
        MyService service = new MyService(mockApi);
        String result = service.fetchMultiple();
        assertEquals("First, Second", result);
        verify(mockApi, times(2)).getData();
    }
}
