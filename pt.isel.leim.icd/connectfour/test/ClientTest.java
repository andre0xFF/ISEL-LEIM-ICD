import network.Client;
import network.messages.PingMessage;
import network.messages.PingMessageTest;
import network.messages.PongMessage;
import network.messages.PongMessageTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private Client client;
    private ServerSocket javaServerSocket;
    private java.net.Socket javaSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    @BeforeEach
    void setUp() throws IOException {
        this.javaServerSocket = new ServerSocket(0);
        this.client = new Client(this.javaServerSocket.getLocalPort());

        Thread thread = new Thread(() -> {
            try {
                this.javaSocket = this.javaServerSocket.accept();
                this.writer = new PrintWriter(this.javaSocket.getOutputStream(), true);
                this.reader = new BufferedReader(new InputStreamReader(this.javaSocket.getInputStream()));

                this.reader.readLine();
            } catch (IOException ignored) {
            }
        });

        thread.start();
    }

    @Test
    void shouldConnect() {
        assertTrue(this.client.isConnected());
    }

    @Test
    void shouldDisconnect() throws IOException {
        this.client.close();

        assertFalse(client.isConnected());
    }

    @Test
    void shouldWritePingMessage() {
        assertDoesNotThrow(() -> client.write(new PingMessage()));
    }

    @Test
    void shouldWritePongMessage() {
        assertDoesNotThrow(() -> client.write(new PongMessage()));
    }

    @Test
    void shouldRead() {
        this.writer.println(PingMessageTest.serializedContent);

        assertDoesNotThrow(() -> client.read());
    }

    @Test
    void shouldReadPingMessage() throws IOException, SAXException {
        this.writer.println(PingMessageTest.serializedContent);

        assertInstanceOf(PingMessage.class, client.read());
    }

    @Test
    void shouldReadPongMessage() throws IOException, SAXException {
        this.writer.println(PongMessageTest.serializedContent);

        assertInstanceOf(PongMessage.class, client.read());
    }
}
