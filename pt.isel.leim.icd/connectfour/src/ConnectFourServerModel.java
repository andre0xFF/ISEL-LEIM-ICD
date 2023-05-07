import models.ConnectFour;
import network.Client;
import network.Server;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The server model of the Connect Four game. It is responsible for managing the game and the players.
 * An instance of this class runs in a separate thread automatically when created.
 */
public class ConnectFourServerModel implements Runnable {

    public static final int clientsLimit = 2;
    private ConnectFour connectFour;
    private final Server server;
    private final ArrayList<RemotePlayer> players = new ArrayList<>();


    /**
     * Creates a new instance of the ConnectFourServerModel class.
     *
     * @throws IOException If an I/O error occurs when creating the server.
     */
    public ConnectFourServerModel() throws IOException {
        this(Client.DEFAULT_PORT);
    }

    /**
     * Creates a new instance of the ConnectFourServerModel class.
     *
     * @param port The port of the server.
     * @throws IOException If an I/O error occurs when creating the server.
     */
    public ConnectFourServerModel(int port) throws IOException {
        this.server = new Server(port);
        Thread thread = new Thread(this);

        thread.start();
    }

    /**
     * Gets the port of the server.
     *
     * @return The port of the server.
     */
    public int port() {
        return this.server.port();
    }

    public int playersSize() {
        return this.players.size();
    }

    public int loggedPlayersSize() {
        return (int) this.players.stream().filter(RemotePlayer::authenticated).count();
    }

    /**
     * Gets a player by username.
     *
     * @param username The username of the player.
     * @return The player with the given username.
     */
    public RemotePlayer player(String username) {
        for (RemotePlayer remotePlayer : this.players) {
            if (remotePlayer.username().equals(username)) {
                return remotePlayer;
            }
        }

        return null;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < ConnectFourServerModel.clientsLimit; i++) {
                Client client = this.server.accept();

                this.players.add(new RemotePlayer(client));
            }

            this.connectFour = new ConnectFour(
                    this.players.get(0),
                    this.players.get(1)
            );

            // TODO: Sleep while there's no winner?
            // TODO: Accept more players when the game is over?

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        ConnectFourServerModel server = new ConnectFourServerModel();
    }
}
