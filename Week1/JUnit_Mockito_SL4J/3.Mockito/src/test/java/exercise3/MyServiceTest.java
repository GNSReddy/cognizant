package exercise3;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MyServiceTest {
    @Test
    public void testArgumentMatching() {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getDataById(anyInt())).thenReturn("Id Data");
        MyService service = new MyService(mockApi);
        String result = service.fetchDataById(10);
        assertEquals("Id Data", result);
        verify(mockApi).getDataById(eq(10));
    }
}
