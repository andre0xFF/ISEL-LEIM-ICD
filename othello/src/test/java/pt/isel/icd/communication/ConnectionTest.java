package pt.isel.icd.communication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isel.icd.communication.commands.Command;
import pt.isel.icd.communication.commands.PingCommand;
import pt.isel.icd.communication.commands.PongCommand;
import pt.isel.icd.patterns.observer.Subscriber;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionTest {

    private Connection connection;
    private PingCommand message;

    @BeforeEach
    void setUp() throws IOException {
        message = new PingCommand(LocalDateTime.now());

        // Create a server socket. Use a different port for each test.
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            connection = new Connection(serverSocket.getLocalPort());
        }
    }

    @Test
    void isConnected() {
        assertTrue(connection.isConnected());
    }

    @Test
    void close() throws IOException {
        connection.close();
        assertFalse(connection.isConnected());
    }

    @Test
    void write() throws IOException {
        connection.write(message);
    }

    static class TestSubscriber implements Subscriber<Command> {

        private int messages = 0;

        @Override
        public void update(Command context) {
            messages++;
            assertTrue(context instanceof PingCommand || context instanceof PongCommand);
        }

        public int messages() {
            return messages;
        }
    }

    static class TestConnectionSubscriber implements ConnectionSubscriber {

        private int messages = 0;

        @Override
        public Class<? extends Command>[] commandTypes() {
            return new Class[]{
                    PingCommand.class,
            };
        }

        @Override
        public void update(Command context) {
            messages++;
            assertInstanceOf(PingCommand.class, context);
        }

        public int messages() {
            return messages;
        }
    }

    @Test
    void subscribeUpdatePublishAndUnsubscribe() {
        TestSubscriber testSubscriber = new TestSubscriber();
        TestConnectionSubscriber testConnectionSubscriber = new TestConnectionSubscriber();

        // The TestSubscriber will be subscribed to all message types.
        connection.subscribe(testSubscriber);

        // The TestConnectionSubscriber will be subscribed to PingCommand.
        connection.subscribe(testConnectionSubscriber);

        assertEquals(1, connection.connectionSubscribersTotal());

        connection.update("<Command><PingCommand><dateTime>2021-05-18T15:00:00</dateTime></PingCommand></Command>");
        connection.update("<Command><PongCommand><dateTime>2021-05-18T15:00:00</dateTime></PongCommand></Command>");

        assertEquals(2, testSubscriber.messages());
        assertEquals(1, testConnectionSubscriber.messages());

        connection.unsubscribe(testSubscriber);
        connection.unsubscribe(testConnectionSubscriber);

        assertEquals(0, connection.connectionSubscribersTotal());
    }
}
