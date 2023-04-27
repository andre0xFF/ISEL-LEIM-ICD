package network;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class SocketTest {

    private ServerSocket javaServerSocket;
    private java.net.Socket javaSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    private final String actualMessage = "Hello World!";
    private final String expectedMessage = "Hello World!";

    @BeforeEach
    void setUpEach() throws IOException {
        this.javaServerSocket = new ServerSocket(0);

        new Thread(() -> {
            try {
                this.javaSocket = this.javaServerSocket.accept();
                this.writer = new PrintWriter(this.javaSocket.getOutputStream(), true);
                this.reader = new BufferedReader(new InputStreamReader(this.javaSocket.getInputStream()));

                this.writer.println(actualMessage);
                this.reader.readLine();
            } catch (IOException ignored) {}
        }).start();
    }

    @AfterEach
    void tearDownEach() throws IOException {
        this.reader.close();
        this.writer.close();
        this.javaSocket.close();
        this.javaServerSocket.close();
    }

    @Test
    void shouldConnect() throws IOException {
        try (Socket socket = new Socket(javaServerSocket.getLocalPort())) {
            assertTrue(socket.isConnected());
        }
    }

    @Test
    void shouldWriteMessage() throws IOException {
        try (Socket socket = new Socket(javaServerSocket.getLocalPort())) {
            socket.write(actualMessage);
            assertDoesNotThrow(socket::close);
        }
    }

    @Test
    void shouldReadMessageWhenMessageIsWritten() throws IOException, InterruptedException {
        try (Socket socket = new Socket(javaServerSocket.getLocalPort())) {
            assertEquals(expectedMessage, socket.read());
        }
    }

    @Test
    void shouldListenWhenMessagesAreWritten() throws IOException {
        try (Socket socket = new Socket(javaServerSocket.getLocalPort())) {
            String message = expectedMessage;

            socket.listen(
                actualMessage -> assertEquals(message, actualMessage)
            );

            this.writer.write(message);
            this.writer.write(message);

            socket.ignore();
        }
    }

    @Test
    void shouldIgnoreWhenMessagesAreWritten() throws IOException {
        try (Socket socket = new Socket(javaServerSocket.getLocalPort())) {
            socket.listen(
                actualMessage -> fail()
            );

            socket.ignore();

            this.writer.write(actualMessage);
            this.writer.write(actualMessage);
        }
    }

    @Test
    void shouldClose() throws IOException {
        try (Socket socket = new Socket(javaServerSocket.getLocalPort())) {
            socket.close();
            assertFalse(socket.isConnected());
            assertTrue(socket.isClosed());
        }
    }

    @Test
    void shouldReturnHostname() throws IOException {
        try (Socket socket = new Socket(javaServerSocket.getLocalPort())) {
            assertEquals("localhost", socket.hostname());
        }
    }

    @Test
    void shouldReturnPort() throws IOException {
        try (Socket socket = new Socket(javaServerSocket.getLocalPort())) {
            assertEquals(javaServerSocket.getLocalPort(), socket.port());
        }
    }
}
