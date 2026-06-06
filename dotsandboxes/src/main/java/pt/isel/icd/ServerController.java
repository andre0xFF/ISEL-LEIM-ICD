package pt.isel.icd;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import pt.isel.icd.communication.ConnectedCommand;
import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.communication.Controller;
import pt.isel.icd.communication.DisconnectedCommand;
import pt.isel.icd.communication.DisconnectionListener;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.game.logic.Game;
import pt.isel.icd.game.logic.Player;
import pt.isel.icd.game.management.GameOverCommand;
import pt.isel.icd.game.management.GameRegistry;
import pt.isel.icd.game.management.GameSession;
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

public class ServerController
    implements Controller, Authenticator, DisconnectionListener
{

    private static final Logger logger = Logger.getLogger(
        ServerController.class.getName()
    );

    private final ConnectionManager connectionManager;
    private final UserServerRepository userServerRepository;
    private final HashMap<UUID, User> authenticatedUsers = new HashMap<>();
    // Registo de todos os jogos a decorrer + emparelhamento (multi-jogo, L1).
    private final GameRegistry gameRegistry = new GameRegistry();

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

    /**
     * Limpeza ao desligar um socket: remove a autenticacao, tira-o da fila de
     * emparelhamento e termina os jogos em que participava, avisando o
     * adversario (resolve a limitacao de "fantasmas" no emparelhamento).
     */
    @Override
    public void onDisconnected(UUID socketId) {
        authenticatedUsers.remove(socketId);
        gameRegistry.cancelWaiting(socketId);
        for (GameSession session : gameRegistry.sessionsOf(socketId)) {
            gameRegistry.remove(session.gameId());
            UUID opponent = session.socketA().equals(socketId)
                ? session.socketB()
                : session.socketA();
            connectionManager.write(
                opponent,
                new LeaveGameResponseCommand(true, session.gameId())
            );
        }
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

    /**
     * Pedido de entrada em jogo. Faz emparelhamento no GameRegistry: se houver
     * outro participante a aguardar, cria uma nova sessao e avisa ambos; caso
     * contrario, o pedido fica em espera. Um participante pode pedir varios
     * jogos em simultaneo.
     */
    public void joinGame(UUID socketId) {
        GameSession session = gameRegistry.matchOrEnqueue(socketId);
        if (session == null) {
            return; // a aguardar adversario
        }
        notifyJoined(session, session.socketA());
        notifyJoined(session, session.socketB());
    }

    private void notifyJoined(GameSession session, UUID socketId) {
        connectionManager.write(
            socketId,
            new JoinGameResponseCommand(
                true,
                session.markerFor(socketId),
                Game.DEFAULT_ROWS,
                Game.DEFAULT_COLS,
                session.gameId()
            )
        );
    }

    /**
     * Abandono de um jogo identificado por gameId. Termina a sessao e avisa os
     * dois participantes. Se ainda nao havia jogo (so estava em espera), apenas
     * retira o socket da fila de emparelhamento.
     */
    public void leaveGame(UUID socketId, String gameId) {
        GameSession session = gameRegistry.get(gameId);
        if (session == null) {
            gameRegistry.cancelWaiting(socketId);
            connectionManager.write(
                socketId,
                new LeaveGameResponseCommand(true, gameId)
            );
            return;
        }

        gameRegistry.remove(gameId);
        connectionManager.write(
            session.socketA(),
            new LeaveGameResponseCommand(true, gameId)
        );
        connectionManager.write(
            session.socketB(),
            new LeaveGameResponseCommand(true, gameId)
        );
    }

    /**
     * Coloca uma linha no jogo identificado por gameId, em nome do socket dado,
     * e difunde o resultado aos dois participantes. Se o jogo terminar, atualiza
     * estatisticas e envia GameOver.
     */
    public void placeLine(UUID socketId, String gameId, Dot dot1, Dot dot2) {
        GameSession session = gameRegistry.get(gameId);
        if (session == null) return;

        Player player = session.playerFor(socketId);
        if (player == null) return;

        Game game = session.game();
        boolean placed = game.placeLine(player, dot1, dot2);
        boolean extraTurn =
            placed && !game.isFinished() && game.isPlayerTurn(player);

        for (UUID sid : List.of(session.socketA(), session.socketB())) {
            connectionManager.write(
                sid,
                new PlaceLineResponseCommand(
                    placed,
                    dot1,
                    dot2,
                    0,
                    player.marker().name(),
                    extraTurn,
                    gameId
                )
            );
        }

        if (game.isFinished()) {
            finishGame(session);
        }
    }

    /**
     * Termina a sessao: atualiza as estatisticas de cada participante
     * autenticado e envia GameOver a ambos, removendo o jogo do registo.
     */
    private void finishGame(GameSession session) {
        Game game = session.game();
        Player winner = game.winner();
        Player playerA = game.getPlayer(0);
        Player playerB = game.getPlayer(1);
        String winnerMarker = winner != null ? winner.marker().name() : "DRAW";

        for (UUID sid : List.of(session.socketA(), session.socketB())) {
            updateProfileAfterGame(sid, session.playerFor(sid), winner);
            connectionManager.write(
                sid,
                new GameOverCommand(
                    winner != null,
                    winnerMarker,
                    playerA.score(),
                    playerB.score(),
                    session.gameId()
                )
            );
        }

        gameRegistry.remove(session.gameId());
    }

    /** Incrementa vitorias/derrotas do participante autenticado no fim do jogo. */
    private void updateProfileAfterGame(
        UUID sid,
        Player player,
        Player winner
    ) {
        User user = authenticatedUsers.get(sid);
        if (user == null) return;

        Profile profile = userServerRepository.readProfile(user.username());
        if (profile == null) return;

        boolean isWinner = winner != null && player.marker() == winner.marker();
        boolean isLoser = winner != null && player.marker() != winner.marker();
        userServerRepository.updateProfile(
            new Profile(
                profile.username(),
                profile.nationality(),
                profile.age(),
                profile.photo(),
                profile.wins() + (isWinner ? 1 : 0),
                profile.losses() + (isLoser ? 1 : 0)
            )
        );
    }
}
