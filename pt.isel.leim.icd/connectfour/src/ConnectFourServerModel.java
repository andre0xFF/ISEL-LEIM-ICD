import models.ConnectFour;
import models.Player;
import network.Client;
import network.Server;
import network.messages.*;
import network.socket.Listener;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectFourServerModel implements Runnable {

    private ArrayList<GameSessionModel> gameSessions = new ArrayList<>();

    class PlayerServerModel implements Listener<Message> {

        private Client client;
        private Player player;
        private Client opponent;
        private GameSessionModel gameSession;


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
            } else if (message instanceof DropTokenMessage dropTokenMessage) {
                onMessage(dropTokenMessage);
            } else if (message instanceof AskQueueGameMessage askQueueGameMessage) {
                onMessage(askQueueGameMessage);
            } else if (message instanceof CancelQueueGameMessage cancelQueueGameMessage) {
                onMessage(cancelQueueGameMessage);
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

        private void onMessage(DropTokenMessage dropTokenMessage) {
            if (gameSession.getState() == GameSessionState.PLAYING_GAME) {

                if (connectFour.dropToken(dropTokenMessage.column())) {
                    // Send write message for other player in session
                    Client opponent = gameSession.getOpponent(client);

                    try {
                        opponent.write(new DropTokenMessage(dropTokenMessage.column()));
                    } catch (IOException | SAXException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        private void onMessage(AskQueueGameMessage QueueUpMessage) {
            GameSessionModel gameSessionAvailable = getAvailableGameSession();

            if (gameSessionAvailable == null) {
                this.gameSession = createGameSession(client);

            } else {
                gameSessionAvailable.addPlayer(this.client);
            }
        }

        private void onMessage(CancelQueueGameMessage cancelQueueGameMessage) {
            if (gameSession.getState() == GameSessionState.WAITING_FOR_PLAYER) {
                removeGameSession(gameSession);
                gameSession = null;
            }
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

    public GameSessionModel getAvailableGameSession() {
        GameSessionModel gameSession;
        for (int i = this.gameSessions.size(); i > 0; i--) {
            gameSession = this.gameSessions.get(i);

            if (!gameSession.isGameSessionFull()) {
                return gameSession;
            }
        }
        return null;
    }

    public GameSessionModel createGameSession(Client client) {
        GameSessionModel gameSession = new GameSessionModel(client);
        this.gameSessions.add(gameSession);
        return gameSession;
    }

    public void removeGameSession(GameSessionModel gameSession) {
        this.gameSessions.remove(gameSession);

    }

    private void queueUpGame() {

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
