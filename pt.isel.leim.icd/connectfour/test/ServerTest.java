import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private static Server server;
    private static Thread serverThread;
    private static final ArrayList<Class<? extends Message>> messages = new ArrayList<>() {
        {
            add(Message.PingMessage.class);
            add(Message.PongMessage.class);
        }
    };

    private final Message.Serializer serializer = new Message.Serializer();

    @BeforeAll
    static void setUp() {
        server = new Server(messages);
        serverThread = new Thread(server::start);

        serverThread.start();
    }

    @AfterAll
    static void tearDown() {
        server.stop();
        serverThread.interrupt();
    }

    @Test
    void shouldStartServerOnPort8000ByDefaultWhenNotSpecified() {
        assertEquals(8000, server.getPort());
    }

    @Test
    void shouldStartServerOnPort8001WhenSpecified() {
        Server server = new Server(messages, 8001);

        assertEquals(8001, server.getPort());
    }

    @Test
    void shouldAcceptConnection() throws IOException {
        Socket socket = new Socket("localhost", server.getPort());

        assertTrue(socket.isConnected());
    }

    @Test
    void shouldAcceptMultipleConnections() throws IOException {
        Socket socket1 = new Socket("localhost", server.getPort());
        Socket socket2 = new Socket("localhost", server.getPort());
        Socket socket3 = new Socket("localhost", server.getPort());

        assertTrue(socket1.isConnected());
        assertTrue(socket2.isConnected());
        assertTrue(socket3.isConnected());
    }

    @Test
    void shouldReceiveText() throws IOException {
        Socket socket = new Socket("localhost", server.getPort());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        assertDoesNotThrow(() -> out.println("Hello world!"));
    }
}
