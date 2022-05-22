import sessions.Client;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
    }
}
