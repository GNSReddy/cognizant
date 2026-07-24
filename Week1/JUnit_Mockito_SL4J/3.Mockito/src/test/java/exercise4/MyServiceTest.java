package exercise4;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class MyServiceTest {
    @Test
    public void testHandlingVoidMethods() {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        doNothing().when(mockApi).performAction();
        MyService service = new MyService(mockApi);
        service.doAction();
        verify(mockApi).performAction();
    }
}
