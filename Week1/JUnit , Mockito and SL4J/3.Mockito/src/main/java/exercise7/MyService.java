package exercise7;

public class MyService {
    private final ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public void doActionWithException() {
        externalApi.performActionWithException();
    }
}
