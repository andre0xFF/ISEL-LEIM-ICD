import network.Client;
import network.messages.LoginMessage;
import network.messages.PingMessageTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ConnectFourServerModelTest {

    private ConnectFourServerModel model;

    @BeforeEach
    void setUpEach() throws InterruptedException, IOException {
        this.model = new ConnectFourServerModel(0);

        Thread.sleep(1000);
    }

    @Test
    void shouldAcceptTwoClients() throws IOException {
        try (
                Socket socket1 = new Socket("localhost", this.model.port());
                Socket socket2 = new Socket("localhost", this.model.port())
        ) {
            assertTrue(socket1.isConnected() && !socket1.isClosed());
            assertTrue(socket2.isConnected() && !socket2.isClosed());
        }
    }

    @Test
    void shouldNotAcceptMoreThanTwoClients() throws IOException {
        try (
                Socket socket1 = new Socket("localhost", this.model.port());
                Socket socket2 = new Socket("localhost", this.model.port());
                Socket socket3 = new Socket("localhost", this.model.port())
        ) {
            assertTrue(socket1.isConnected() && !socket1.isClosed());
            assertTrue(socket2.isConnected() && !socket2.isClosed());
            assertFalse(socket3.isConnected() && socket3.isClosed());
        }
    }

    @Test
    void shouldReadMessage() throws IOException {
        try (Socket socket = new Socket("localhost", this.model.port())) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println(PingMessageTest.serializedContent);
        }
    }

    @Test
    void shouldConnectOnePlayer() throws IOException {
        Client client = new Client("localhost", this.model.port());

        assertTrue(client.isConnected());
        assertEquals(this.model.playersCount(), 1);
    }

    @Test
    void shouldConnectTwoPlayers() throws IOException {
        Client client1 = new Client("localhost", this.model.port());
        Client client2 = new Client("localhost", this.model.port());

        assertTrue(client1.isConnected());
        assertTrue(client2.isConnected());
        assertEquals(this.model.playersCount(), 2);
    }

    @Test
    void shouldLoginPlayer() throws IOException, SAXException, InterruptedException {
        Client client = new Client("localhost", this.model.port());

        String actualUsername = "player1";
        String expectedUsername = "player1";

        client.write(
                new LoginMessage(
                        actualUsername,
                        new char[] {'p', 'a', 's', 's'}
                )
        );

        Thread.sleep(1000);

        assertEquals(this.model.loggedPlayersCount(), 1);
        assertNotNull(this.model.player(expectedUsername));
    }
}
