package pt.isel.icd.game.management;

import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import pt.isel.icd.game.logic.Game;
import pt.isel.icd.game.logic.Player;
import pt.isel.icd.game.logic.PlayerMarker;

/**
 * Agrupa um jogo a decorrer com os dois participantes (identificados pelo id do
 * socket) e os respetivos marcadores. Cada sessao e isolada pelo seu gameId, o
 * que permite varios jogos em simultaneo no mesmo servidor.
 *
 * O participante que entrou primeiro fica com o marcador A (e joga primeiro); o
 * segundo fica com o marcador B.
 */
public class GameSession {

    private final String gameId;
    private final Game game;
    private final UUID socketA; // participante com marcador A (entrou primeiro)
    private final UUID socketB; // participante com marcador B
    private final Player playerA;
    private final Player playerB;
    private final long startMillis;

    // Estado do temporizador de jogada (acedido sob synchronized(session)).
    private ScheduledFuture<?> turnTimer; // tarefa de timeout do turno atual
    private long turnToken; // distingue temporizadores (anti-corrida)
    private boolean ended; // garante terminacao unica do jogo

    public GameSession(
        String gameId,
        Game game,
        UUID socketA,
        UUID socketB,
        Player playerA,
        Player playerB
    ) {
        this.gameId = gameId;
        this.game = game;
        this.socketA = socketA;
        this.socketB = socketB;
        this.playerA = playerA;
        this.playerB = playerB;
        this.startMillis = System.currentTimeMillis();
    }

    public String gameId() {
        return gameId;
    }

    public Game game() {
        return game;
    }

    public UUID socketA() {
        return socketA;
    }

    public UUID socketB() {
        return socketB;
    }

    /** Instante (epoch millis) em que a sessao foi criada/iniciada. */
    public long startMillis() {
        return startMillis;
    }

    /** Devolve o Player associado a este socket, ou null se nao participa. */
    public Player playerFor(UUID socketId) {
        if (socketId.equals(socketA)) return playerA;
        if (socketId.equals(socketB)) return playerB;
        return null;
    }

    /** Devolve o marcador associado a este socket, ou null se nao participa. */
    public PlayerMarker markerFor(UUID socketId) {
        Player player = playerFor(socketId);
        return player != null ? player.marker() : null;
    }

    /** Indica se o socket dado participa nesta sessao. */
    public boolean hasParticipant(UUID socketId) {
        return socketId.equals(socketA) || socketId.equals(socketB);
    }

    // === Temporizador de jogada (coordenado pelo ServerController) ===

    public ScheduledFuture<?> turnTimer() {
        return turnTimer;
    }

    public void setTurnTimer(ScheduledFuture<?> timer) {
        this.turnTimer = timer;
    }

    public long turnToken() {
        return turnToken;
    }

    /** Avanca o token do turno e devolve o novo valor. */
    public long bumpTurnToken() {
        return ++turnToken;
    }

    public boolean isEnded() {
        return ended;
    }

    public void markEnded() {
        this.ended = true;
    }
}
