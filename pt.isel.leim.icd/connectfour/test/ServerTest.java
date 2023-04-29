import network.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerTest {

    private Server server;

    @BeforeEach
    void setUpEach() throws IOException {
        this.server = new Server(0);
    }

    @Test
    void shouldAcceptConnection() throws IOException {
        accept(1);

        try (Socket socket = new Socket("localhost", this.server.port())) {
            assertTrue(socket.isConnected());
        }
    }

    @Test
    void shouldAcceptMultipleConnections() throws IOException {
        accept(3);

        Socket socket1 = new Socket("localhost", server.port());
        Socket socket2 = new Socket("localhost", server.port());
        Socket socket3 = new Socket("localhost", server.port());

        assertTrue(socket1.isConnected());
        assertTrue(socket2.isConnected());
        assertTrue(socket3.isConnected());

        socket1.close();
        socket2.close();
        socket3.close();
    }

    @Test
    void shouldReceiveText() throws IOException {
        accept(1);

        try (Socket socket = new Socket("localhost", server.port())) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            assertDoesNotThrow(() -> out.println("Hello world!"));
        }
    }

    void accept(int totalClients) {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < totalClients; i++) {
                    server.accept();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }
}
