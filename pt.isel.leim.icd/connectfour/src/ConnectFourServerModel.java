import models.ConnectFour;
import models.Player;
import network.Client;
import network.Server;
import network.messages.LoginMessage;
import network.messages.Message;
import network.messages.PingMessage;
import network.messages.PongMessage;
import network.socket.Listener;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectFourServerModel implements Runnable {

    class PlayerServerModel implements Listener<Message> {

        private Client client;
        private Player player;

        public PlayerServerModel(Client client) {
            client.listen(this);

            this.client = client;
        }

        public boolean isLogged() {
            return this.player != null;
        }

        public Player player() {
            return this.player;
        }

        @Override
        public void onMessage(Message message) {
            if (message instanceof PingMessage pingMessage) {
                onMessage(pingMessage);
            } else if (message instanceof PongMessage pongMessage) {
                onMessage(pongMessage);
            } else if (message instanceof LoginMessage loginMessage) {
                onMessage(loginMessage);
            }
        }

        private void onMessage(PingMessage message) {
            System.out.println("Ping received");
        }

        private void onMessage(PongMessage message) {
            // ...
        }

        private void onMessage(LoginMessage message) {
            this.player = new Player(message.username());
        }
    }

    public static final int clientsLimit = 2;
    private ConnectFour connectFour;
    private final Server server;
    private ArrayList<PlayerServerModel> players = new ArrayList<>();

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

    public int playersCount() {
        return this.players.size();
    }

    public int loggedPlayersCount() {
        return (int) this.players.stream().filter(
                player -> player.isLogged()
        ).count();
    }

    /**
     * Gets a player by username.
     *
     * @param username The username of the player.
     * @return The player with the given username.
     */
    public Player player(String username) {
        for (PlayerServerModel playerServerModel : this.players) {
            if (playerServerModel.player().username().equals(username)) {
                return playerServerModel.player();
            }
        }

        return null;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < ConnectFourServerModel.clientsLimit; i++) {
                Client client = this.server.accept();
                this.players.add(new PlayerServerModel(client));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
