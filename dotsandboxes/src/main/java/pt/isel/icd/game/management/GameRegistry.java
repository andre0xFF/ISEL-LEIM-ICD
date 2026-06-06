package pt.isel.icd.game.management;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import pt.isel.icd.game.logic.Game;
import pt.isel.icd.game.logic.Player;
import pt.isel.icd.game.logic.PlayerMarker;

/**
 * Mantem todos os jogos a decorrer no servidor, indexados por gameId, e gere o
 * emparelhamento (matchmaking). Substitui o anterior jogo unico global do
 * ServerController, permitindo varios jogos em simultaneo (resolve a lacuna L1).
 *
 * Os metodos que mexem na fila de emparelhamento sao sincronizados porque sao
 * invocados a partir de varias threads (uma por ligacao/cliente).
 */
public class GameRegistry {

    private final Map<String, GameSession> games = new ConcurrentHashMap<>();
    private final Queue<UUID> matchmaking = new ArrayDeque<>();

    /**
     * Coloca o participante na fila de espera e, havendo outro a aguardar, cria
     * de imediato uma nova sessao de jogo ja iniciada.
     *
     * @return a sessao criada, ou null se ficou apenas a aguardar adversario.
     */
    public synchronized GameSession matchOrEnqueue(UUID socketId) {
        // Ignora pedidos repetidos do mesmo socket que ja aguarda adversario.
        if (matchmaking.contains(socketId)) {
            return null;
        }

        // Procura um adversario diferente na fila de espera.
        UUID opponent = null;
        while (!matchmaking.isEmpty()) {
            UUID head = matchmaking.poll();
            if (!head.equals(socketId)) {
                opponent = head;
                break;
            }
        }

        if (opponent == null) {
            matchmaking.add(socketId);
            return null;
        }
        return createSession(opponent, socketId);
    }

    /**
     * Cria e inicia uma nova sessao: o primeiro socket fica com o marcador A
     * (joga primeiro) e o segundo com o marcador B.
     */
    private GameSession createSession(UUID socketA, UUID socketB) {
        Game game = new Game();
        game.open();
        Player playerA = new Player(PlayerMarker.A);
        Player playerB = new Player(PlayerMarker.B);
        game.join(playerA);
        game.join(playerB);
        game.start();

        // O gameId viaja como texto no protocolo, mas e gerado a partir de um
        // UUID internamente (String-in-XML, UUID internamente).
        String gameId = UUID.randomUUID().toString();
        GameSession session = new GameSession(
            gameId,
            game,
            socketA,
            socketB,
            playerA,
            playerB
        );
        games.put(gameId, session);
        return session;
    }

    /** Devolve a sessao com o gameId dado, ou null se nao existir. */
    public GameSession get(String gameId) {
        return gameId == null ? null : games.get(gameId);
    }

    /** Remove (termina) a sessao com o gameId dado. */
    public void remove(String gameId) {
        if (gameId != null) {
            games.remove(gameId);
        }
    }

    /** Remove um socket da fila de espera (ex.: ao sair ou ao desligar). */
    public synchronized void cancelWaiting(UUID socketId) {
        matchmaking.remove(socketId);
    }

    /** Lista as sessoes em que o socket participa (suporte a varios jogos). */
    public List<GameSession> sessionsOf(UUID socketId) {
        List<GameSession> result = new ArrayList<>();
        for (GameSession session : games.values()) {
            if (session.hasParticipant(socketId)) {
                result.add(session);
            }
        }
        return result;
    }
}
