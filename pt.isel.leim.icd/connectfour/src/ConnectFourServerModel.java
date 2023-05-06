import models.ConnectFour;
import network.Client;
import network.Server;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the server side of the game.
 */
public class ConnectFourServerModel implements Runnable {

    public static final int clientsLimit = 2;
    private ConnectFour connectFour;
    private final Server server;
    private final ArrayList<RemotePlayer> players = new ArrayList<>();
    private final ArrayList<Client> clients = new ArrayList<>();

    public ConnectFourServerModel() throws IOException {
        this(Client.DEFAULT_PORT);
    }

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
        return (int) this.players.stream().filter(RemotePlayer::isLogged).count();
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
                this.clients.add(client);
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
}
