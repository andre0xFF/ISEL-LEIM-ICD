package pt.isel.icd.messaging;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Represents a server.
 */
public class Server {
    private final ServerSocket serverSocket;

    public Server() throws IOException {
        this(SocketFacade.DEFAULT_PORT);
    }

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Returns the port of the server.
     *
     * @return The port of the server.
     */
    public int port() {
        return this.serverSocket.getLocalPort();
    }

    /**
     * Accepts a client.
     *
     * @return The client.
     * @throws IOException If an I/O error occurs when accepting the client.
     */
    public Client accept() throws IOException {
        SocketFacade socket = new SocketFacade(serverSocket.accept());

        return new Client(socket);
    }
}
