package pt.isel.icd.service.server;

import pt.isel.icd.communication.Connection;
import pt.isel.icd.communication.SocketFacade;
import pt.isel.icd.service.server.controllers.UserController;
import pt.isel.icd.service.server.controllers.GameController;
import pt.isel.icd.service.server.controllers.GameListController;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Represents a server.
 */
public class Server implements Runnable {
    private final ServerSocket serverSocket;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<ServerController> controllers = new ArrayList<>();

    public Server() throws IOException {
        this(SocketFacade.DEFAULT_PORT);
    }

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        controllers.add(new GameController());
        controllers.add(new UserController());
        controllers.add(new GameListController());
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
     * Starts accepting connections in separate thread.
     */
    public void accept() {
        new Thread(this).start();
    }

    /**
     * Runs the server. Creates a user for each new connection.
     */
    @Override
    public void run() {
        while (true) {
            try {
                SocketFacade socket = new SocketFacade(serverSocket.accept());
                Connection connection = new Connection(socket);

                addUser(connection);
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

    public int usersTotal() {
        return users.size();
    }
}
