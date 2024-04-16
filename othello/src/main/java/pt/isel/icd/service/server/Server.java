package pt.isel.icd.service.server;

import pt.isel.icd.communication.Connection;
import pt.isel.icd.communication.SocketFacade;
import pt.isel.icd.service.server.controllers.UserController;
import pt.isel.icd.service.server.controllers.GameController;
import pt.isel.icd.service.server.controllers.GameListController;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a server.
 */
public class Server implements Runnable {
    private final ServerSocket serverSocket;
    private final HashMap<Connection, User> connectionUsers = new HashMap<>();
    private final ArrayList<ServerController> controllers = new ArrayList<>();

    public Server() throws IOException {
        this(SocketFacade.DEFAULT_PORT);
    }

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        UserController userController = new UserController(connectionUsers);
        GameListController gameListController = new GameListController(connectionUsers);
        GameController gameController = new GameController(connectionUsers, gameListController);

        controllers.add(userController);
        controllers.add(gameListController);
        controllers.add(gameController);
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

                connectionUsers.put(connection, new User());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int connectionsTotal() {
        return connectionUsers.size();
    }
}
