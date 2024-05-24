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

    public void placePiece(UUID originSocketId, int row, int column) throws JsonProcessingException {
        Player player = players.get(originSocketId);
        boolean piecePlaced = game.placePiece(player, row, column);

        connectionManager.write(originSocketId, new PlacePieceResponseCommand(piecePlaced, row, column));

        if (!game.isFinished()) {
            return;
        }

        for (UUID socketId : players.keySet()) {
            connectionManager.write(socketId, new GameOverCommand(game.hasWinner(), game.winner()));
        }
    }
}
