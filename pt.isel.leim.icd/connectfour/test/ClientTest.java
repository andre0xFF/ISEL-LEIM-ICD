import com.fasterxml.jackson.core.JsonProcessingException;
import messages.Message;
import messages.PingMessage;
import messages.PongMessage;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private static Server server;
    private static Thread serverThread;
    private static Client client;
    private static final ArrayList<Class<? extends Message>> messages = new ArrayList<>() {
        {
            add(PingMessage.class);
            add(PongMessage.class);
        }
    };

    @BeforeAll
    static void setUp() throws IOException {
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
        assertDoesNotThrow(() -> client.write(new PingMessage()));
    }
}
