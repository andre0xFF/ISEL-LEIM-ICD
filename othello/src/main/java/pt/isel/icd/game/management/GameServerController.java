package pt.isel.icd.game.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.ConnectedCommand;
import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.communication.DisconnectedCommand;
import pt.isel.icd.game.logic.Game;
import pt.isel.icd.game.logic.Piece;
import pt.isel.icd.game.logic.Player;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameServerController implements Controller {
    private final ConnectionManager connectionManager;
    private final HashMap<UUID, Player> players = new HashMap<>();
    private final Game game = new Game();

    public GameServerController(ConnectionManager existingConnectionManager) {
        connectionManager = existingConnectionManager;
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {
                add(ConnectedCommand.class);
                add(DisconnectedCommand.class);
                add(JoinGameCommand.class);
                add(LeaveGameCommand.class);
                add(PlacePieceCommand.class);
            }
        };
    }

    public void joinGame(UUID socketId) throws JsonProcessingException {
        if (players.containsKey(socketId)) {
            return;
        }

        if (game.isClosed()) {
            game.open();
        }

        Piece playerPiece = players.isEmpty() ? Piece.X : Piece.O;
        Player player = new Player(playerPiece);

        game.join(player);
        players.put(socketId, player);

        if (players.size() == 2) {
            game.start();
        }

        if (!game.hasStarted()) {
            return;
        }

        UUID otherSocketId = players.keySet().stream().filter(id -> !id.equals(socketId)).findFirst().orElse(null);
        Player otherPlayer = players.get(otherSocketId);
        Piece otherPlayerPiece = otherPlayer.playPiece();

        connectionManager.write(
                socketId,
                new JoinGameResponseCommand(players.containsKey(socketId), playerPiece)
        );
        connectionManager.write(
                otherSocketId,
                new JoinGameResponseCommand(players.containsKey(otherSocketId), otherPlayerPiece)
        );
    }

    public void leaveGame(UUID socketId) throws JsonProcessingException {
        Player player = players.get(socketId);
        Player otherPlayer = players.get(players.keySet().stream().findFirst().orElse(null));
        UUID otherSocketId = players.keySet().stream().findFirst().orElse(null);

        game.leave(player);
        players.remove(socketId);

        game.leave(otherPlayer);
        players.remove(otherSocketId);

        game.close();

        connectionManager.write(socketId, new LeaveGameResponseCommand(!players.containsKey(socketId)));
        connectionManager.write(otherSocketId, new LeaveGameResponseCommand(!players.containsKey(otherSocketId)));
    }

    public void placeGamePiece(UUID socketId, int row, int column) throws JsonProcessingException {
        Player player = players.get(socketId);
        boolean piecePlaced = game.placePiece(player, row, column);

        connectionManager.write(socketId, new PlacePieceResponseCommand(piecePlaced, row, column));

        if (!game.hasWinner()) {
            return;
        }

        Player winner = game.winner();
        UUID winnerSocketId = players.entrySet().stream().filter(entry -> entry.getValue().equals(winner)).map(HashMap.Entry::getKey).findFirst().orElse(null);
        Player loser = game.loser();
        UUID loserSocketId = players.entrySet().stream().filter(entry -> entry.getValue().equals(loser)).map(HashMap.Entry::getKey).findFirst().orElse(null);

        // connectionManager.write(winnerSocketId, new GameOverCommand(winner.playPiece(), true));
        // connectionManager.write(loserSocketId, new GameOverCommand(loser.playPiece(), false));
    }
}
