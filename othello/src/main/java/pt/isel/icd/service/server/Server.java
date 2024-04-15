package pt.isel.icd.service.server;

import pt.isel.icd.messaging.Connection;
import pt.isel.icd.messaging.SocketFacade;
import pt.isel.icd.service.server.controllers.AuthenticationController;
import pt.isel.icd.service.server.controllers.LobbyController;
import pt.isel.icd.service.server.controllers.RegistrationController;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Represents a server.
 */
public class Server {
    private final ServerSocket serverSocket;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<ServerController> controllers = new ArrayList<>();

    public Server() throws IOException {
        this(SocketFacade.DEFAULT_PORT);
    }

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        controllers.add(new LobbyController());
        controllers.add(new AuthenticationController());
        controllers.add(new RegistrationController());
    }

    /**
     * Returns the port of the server.
     *
     * @return The port of the server.
     */
    public int port() {
        return serverSocket.getLocalPort();
    }

    /**
     * Accepts a connection.
     *
     * @return The connection.
     * @throws IOException If an I/O error occurs when accepting the connection.
     */
    public Connection accept() throws IOException {
        SocketFacade socket = new SocketFacade(serverSocket.accept());

        return new Connection(socket);
    }

    /**
     * Runs the server. Creates a user for each new connection.
     */
    public void run() {
        while (true) {
            try {
                addUser(accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addUser(Connection connection) {
        User user = new User(connection);

        users.add(user);

        for (ServerController controller : controllers) {
            controller.addUser(user);
        }
    }

    private void removeUser(User user) {
        for (ServerController controller : controllers) {
            controller.removeUser(user);
        }

        users.remove(user);
    }
}
