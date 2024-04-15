package pt.isel.icd.communication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class SocketFacadeTest {

    private ServerSocket serverSocket;
    private SocketFacade socketFacade;

    @BeforeEach
    public void setup() throws IOException {
        // Create a server socket. Use a different port for each test.
        serverSocket = new ServerSocket(0);

        Socket socket = new Socket("localhost", serverSocket.getLocalPort());
        socketFacade = new SocketFacade(socket);
    }

    @Test
    public void isConnected() {
        assertTrue(socketFacade.isConnected());
    }

    @Test
    public void isClosed() throws IOException {
        socketFacade.close();
        assertTrue(socketFacade.isClosed());
    }

    @Test
    public void writeAndRead() throws IOException {
        String testCommand = "Hello, World!";
        socketFacade.write(testCommand);

        Socket serviceSideSocket = serverSocket.accept();
        BufferedReader serverSideReader = new BufferedReader(new InputStreamReader(serviceSideSocket.getInputStream()));

        String receivedCommand = serverSideReader.readLine();
        assertEquals(testCommand, receivedCommand);
    }
}
