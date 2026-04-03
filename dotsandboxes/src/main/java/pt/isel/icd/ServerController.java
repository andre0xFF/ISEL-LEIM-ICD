package pt.isel.icd;

import java.util.HashMap;
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
import pt.isel.icd.user.management.UserServerRepository;

public class ServerController implements Controller, Authenticator {

    private static final Logger logger = Logger.getLogger(
        ServerController.class.getName()
    );

    private final ConnectionManager connectionManager;
    private final UserServerRepository userServerRepository;
    private final HashMap<UUID, User> authenticatedUsers = new HashMap<>();
    private final HashMap<UUID, Player> players = new HashMap<>();
    private final Game game = new Game();

    public ServerController(
        UserServerRepository repository,
        ConnectionManager connectionManager
    ) {
        this.userServerRepository = repository;
        this.connectionManager = connectionManager;
    }

    @Override
    public List<Class<? extends SimpleSocketCommand<?>>> commandsList() {
        return List.of(
            ConnectedCommand.class,
            DisconnectedCommand.class,
            AuthenticateUserCommand.class,
            CreateUserCommand.class,
            ReadUserProfileCommand.class,
            UpdateUserCommand.class,
            JoinGameCommand.class,
            LeaveGameCommand.class,
            PlaceLineCommand.class
        );
    }

    @Override
    public boolean isAuthenticated(UUID socketId) {
        return authenticatedUsers.containsKey(socketId);
    }

    public User getAuthenticatedUser(UUID socketId) {
        return authenticatedUsers.get(socketId);
    }

    // === User management ===

    public void authenticateUser(
        UUID socketId,
        String username,
        String password
    ) {
        User existingUser = userServerRepository.readUser(username);
        boolean success =
            existingUser != null && existingUser.password().equals(password);

        if (success) {
            authenticatedUsers.put(socketId, existingUser);
        }

        connectionManager.write(
            socketId,
            new AuthenticateUserResponseCommand(username, success)
        );
    }

    public void createUser(UUID socketId, String username, String password) {
        boolean success = false;
        try {
            User user = new User(username, password);
            if (userServerRepository.readUser(username) == null) {
                userServerRepository.addUser(user);
                userServerRepository.addProfile(
                    new Profile(username, "", 0, "", 0, 0)
                );
                success = true;
            }
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to create user: " + e.getMessage());
        }

        connectionManager.write(
            socketId,
            new CreateUserResponseCommand(username, success)
        );
    }

    public void readUserProfile(UUID socketId) {
        User user = authenticatedUsers.get(socketId);
        Profile profile =
            user != null
                ? userServerRepository.readProfile(user.username())
                : null;
        connectionManager.write(
            socketId,
            new ReadUserProfileResponseCommand(profile, profile != null)
        );
    }

    public void updateUserProfile(
        UUID socketId,
        String nationality,
        int age,
        String photo
    ) {
        User user = authenticatedUsers.get(socketId);
        if (user == null) return;

        Profile existing = userServerRepository.readProfile(user.username());
        if (existing == null) return;

        Profile updated = new Profile(
            user.username(),
            nationality,
            age,
            photo,
            existing.wins(),
            existing.losses()
        );
        userServerRepository.updateProfile(updated);
    }

    // === Game management ===

    public void joinGame(UUID socketId) {
        if (players.containsKey(socketId)) return;

        if (game.isClosed()) {
            game.open();
        }

        PlayerMarker marker = players.isEmpty()
            ? PlayerMarker.A
            : PlayerMarker.B;
        Player player = new Player(marker);
        game.join(player);
        players.put(socketId, player);

        if (game.isFull()) {
            game.start();

            for (var entry : players.entrySet()) {
                connectionManager.write(
                    entry.getKey(),
                    new JoinGameResponseCommand(
                        true,
                        entry.getValue().marker(),
                        Game.DEFAULT_ROWS,
                        Game.DEFAULT_COLS
                    )
                );
            }
        }
    }

    public void leaveGame(UUID socketId) {
        Player player = players.get(socketId);
        if (player == null) return;

        players.remove(socketId);
        connectionManager.write(socketId, new LeaveGameResponseCommand(true));

        for (var entry : players.entrySet()) {
            connectionManager.write(
                entry.getKey(),
                new LeaveGameResponseCommand(true)
            );
        }

        players.clear();
        game.close();
    }

    public void placeLine(UUID socketId, Dot dot1, Dot dot2) {
        Player player = players.get(socketId);
        if (player == null) return;

        boolean placed = game.placeLine(player, dot1, dot2);
        boolean extraTurn =
            placed && !game.isFinished() && game.isPlayerTurn(player);

        for (var entry : players.entrySet()) {
            connectionManager.write(
                entry.getKey(),
                new PlaceLineResponseCommand(
                    placed,
                    dot1,
                    dot2,
                    0,
                    player.marker().name(),
                    extraTurn
                )
            );
        }

        if (game.isFinished()) {
            Player winner = game.winner();
            Player playerA = game.getPlayer(0);
            Player playerB = game.getPlayer(1);
            String winnerMarker =
                winner != null ? winner.marker().name() : "DRAW";

            for (var entry : players.entrySet()) {
                connectionManager.write(
                    entry.getKey(),
                    new GameOverCommand(
                        winner != null,
                        winnerMarker,
                        playerA.score(),
                        playerB.score()
                    )
                );
            }
        }
    }
}
