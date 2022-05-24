import sessions.Communication;

import java.io.IOException;

public class TestClient {
    public static void main(String[] args) throws IOException {
        Communication communication = new Communication();

        communication.start();
        communication.write("Hello World!");
        communication.write("This is something.");

        communication.stop();
    }
}
