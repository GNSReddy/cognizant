package exercise6;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class MyServiceTest {
    @Test
    public void testVerifyInteractionOrder() {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        MyService service = new MyService(mockApi);
        service.fetchOrdered();
        InOrder inOrder = inOrder(mockApi);
        inOrder.verify(mockApi).firstCall();
        inOrder.verify(mockApi).secondCall();
    }
}
