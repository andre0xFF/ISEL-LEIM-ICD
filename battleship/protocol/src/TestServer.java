import sessions.Communication;
import sessions.Server;

import java.io.IOException;
import java.util.ArrayList;

public class TestServer {
    public static void main(String[] args) {
        Server server = new Server();

        server.start();

        ArrayList<Communication> communications = new ArrayList<>();

        Thread acceptClients = new Thread(() -> {
            while (true) {
                Communication communication = server.accept();

                Thread readClient = new Thread(() -> {
                    String text;

                    while((text = communication.read()) != null) {
                        System.out.printf("%s%n", text);
                    }

                    try {
                        communication.stop();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                readClient.start();
                communications.add(communication);
            }
        });

        acceptClients.start();

        // server.stop();
    }
}
