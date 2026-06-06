package pt.isel.icd;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import pt.isel.icd.communication.ConnectedCommand;
import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.communication.Controller;
import pt.isel.icd.communication.DisconnectedCommand;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.game.logic.Game;
import pt.isel.icd.game.logic.Player;
import pt.isel.icd.game.logic.PlayerMarker;
import pt.isel.icd.game.management.GameOverCommand;
import pt.isel.icd.game.management.JoinGameCommand;
import pt.isel.icd.game.management.JoinGameResponseCommand;
import pt.isel.icd.game.management.LeaveGameCommand;
import pt.isel.icd.game.management.LeaveGameResponseCommand;
import pt.isel.icd.game.management.PlaceLineCommand;
import pt.isel.icd.game.management.PlaceLineResponseCommand;
import pt.isel.icd.user.logic.Profile;
import pt.isel.icd.user.logic.User;
import pt.isel.icd.user.management.AuthenticateUserCommand;
import pt.isel.icd.user.management.AuthenticateUserResponseCommand;
import pt.isel.icd.user.management.Authenticator;
import pt.isel.icd.user.management.CreateUserCommand;
import pt.isel.icd.user.management.CreateUserResponseCommand;
import pt.isel.icd.user.management.ReadUserProfileCommand;
import pt.isel.icd.user.management.ReadUserProfileResponseCommand;
import pt.isel.icd.user.management.UpdateUserCommand;

public class ClientController implements Controller, Authenticator {

    private static final Logger logger = Logger.getLogger(
        ClientController.class.getName()
    );

    private final ConnectionManager connectionManager;
    private String username;
    private boolean isAuthenticated;
    private Game game;
    private PlayerMarker myMarker;
    // Identificador do jogo atual (multi-jogo). A GUI joga um jogo de cada vez,
    // por isso basta guardar e reenviar ("echo") o gameId de forma opaca.
    private String gameId;
    private GameEventListener listener;

    public ClientController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<Class<? extends SimpleSocketCommand<?>>> commandsList() {
        return List.of(
            ConnectedCommand.class,
            DisconnectedCommand.class,
            AuthenticateUserResponseCommand.class,
            CreateUserResponseCommand.class,
            ReadUserProfileResponseCommand.class,
            JoinGameResponseCommand.class,
            LeaveGameResponseCommand.class,
            PlaceLineResponseCommand.class,
            GameOverCommand.class
        );
    }

    @Override
    public boolean isAuthenticated(UUID socketId) {
        return isAuthenticated;
    }

    public String getUsername() {
        return username;
    }

    public Game getGame() {
        return game;
    }

    public PlayerMarker getMyMarker() {
        return myMarker;
    }

    public void setListener(GameEventListener listener) {
        this.listener = listener;
    }

    // === User actions ===

    public void authenticateUser(User user) {
        if (isAuthenticated) {
            logger.info("Already authenticated");
            return;
        }
        connectionManager.write(new AuthenticateUserCommand(user));
    }

    public void handleAuthenticateUserResponse(
        String username,
        boolean authenticated
    ) {
        this.isAuthenticated = authenticated;
        this.username = username;
        logger.info(
            authenticated
                ? "Authenticated as " + username
                : "Authentication failed"
        );

        if (listener != null) {
            listener.onAuthenticated(username, authenticated);
        }
    }

    public void createUser(User user) {
        connectionManager.write(new CreateUserCommand(user));
    }

    public void handleCreateUserResponse(String username, boolean created) {
        logger.info(
            created ? "User " + username + " created" : "User creation failed"
        );
        if (listener != null) {
            listener.onUserCreated(username, created);
        }
    }

    public void readUserProfile() {
        connectionManager.write(new ReadUserProfileCommand());
    }

    public void handleReadUserProfileResponse(
        Profile profile,
        boolean hasProfile
    ) {
        logger.info(hasProfile ? "Profile: " + profile : "No profile found");
        if (listener != null) {
            listener.onProfileRead(profile, hasProfile);
        }
    }

    public void updateProfile(String nationality, int age, String photo) {
        connectionManager.write(new UpdateUserCommand(nationality, age, photo));
    }

    // === Game actions ===

    public void joinGame() {
        connectionManager.write(new JoinGameCommand());
    }

    public void handleJoinGameResponse(
        boolean joined,
        PlayerMarker marker,
        int boardRows,
        int boardCols,
        String gameId
    ) {
        if (!joined) {
            logger.info("Failed to join game");
            return;
        }
        this.gameId = gameId;
        myMarker = marker;
        game = new Game();
        game.open();
        Player playerA = new Player(PlayerMarker.A);
        Player playerB = new Player(PlayerMarker.B);
        game.join(playerA);
        game.join(playerB);
        game.start();
        logger.info("Joined game as player " + marker);

        if (listener != null) {
            listener.onGameJoined(marker);
        }
    }

    public void leaveGame() {
        connectionManager.write(new LeaveGameCommand(gameId));
    }

    public void handleLeaveGameResponse(boolean left, String gameId) {
        logger.info("Left game: " + left);
        game = null;
        this.gameId = null;

        if (listener != null) {
            listener.onGameLeft();
        }
    }

    public void placeLine(Dot dot1, Dot dot2) {
        connectionManager.write(new PlaceLineCommand(gameId, dot1, dot2));
    }

    public void handlePlaceLineResponse(
        boolean placed,
        Dot dot1,
        Dot dot2,
        int boxesClosed,
        String marker,
        boolean extraTurn,
        String gameId
    ) {
        logger.info(
            String.format(
                "Line %s -> %s %s: placed=%s, extraTurn=%s",
                dot1,
                dot2,
                marker,
                placed,
                extraTurn
            )
        );

        if (listener != null && placed) {
            listener.onLinePlaced(dot1, dot2, marker, extraTurn);
        }
    }

    public void handleGameOver(
        boolean hasWinner,
        String winnerMarker,
        int scoreA,
        int scoreB,
        String gameId
    ) {
        logger.info(
            String.format(
                "Game Over! Winner: %s, Score A: %d, Score B: %d",
                hasWinner ? winnerMarker : "DRAW",
                scoreA,
                scoreB
            )
        );

        if (listener != null) {
            listener.onGameOver(hasWinner, winnerMarker, scoreA, scoreB);
        }
    }
}
