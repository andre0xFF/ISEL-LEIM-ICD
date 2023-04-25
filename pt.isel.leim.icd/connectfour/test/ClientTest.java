import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private static Server server;
    private static Thread serverThread;
    private static Client client;
    private static final ArrayList<Class<? extends Message>> messages = new ArrayList<>() {
        {
            add(Message.PingMessage.class);
            add(Message.PongMessage.class);
        }
    };

    @BeforeAll
    static void setUp() {
        server = new Server(messages, 8002);
        serverThread = new Thread(server::start);

        serverThread.start();

        client = new Client(8002);
    }

    @Test
    void shouldConnectToServer() {
        assertTrue(client.isConnected());
    }

    @Test
    void shouldSendPingMessage() throws JsonProcessingException {
        assertDoesNotThrow(() -> client.write(new Message.PingMessage()));
    }
}
