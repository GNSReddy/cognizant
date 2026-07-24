package exercise3;

public class MyService {
    private final ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public String fetchDataById(int id) {
        return externalApi.getDataById(id);
    }
}
