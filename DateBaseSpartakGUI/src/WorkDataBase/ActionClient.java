package WorkDataBase;

public class ActionClient {

    private static ClientClass client;

    public static ClientClass getClient() {
        return client;
    }

    public static void setClient(ClientClass client) {
        ActionClient.client = client;
    }
}
