package exercise7;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MyServiceTest {
    @Test
    public void testHandlingVoidMethodsWithExceptions() {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        doThrow(new IllegalArgumentException("Error occurred")).when(mockApi).performActionWithException();
        MyService service = new MyService(mockApi);
        assertThrows(IllegalArgumentException.class, () -> service.doActionWithException());
        verify(mockApi).performActionWithException();
    }
}
