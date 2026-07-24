package exercise5;

public class MyService {
    private final ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public String fetchMultiple() {
        return externalApi.getData() + ", " + externalApi.getData();
    }
}
