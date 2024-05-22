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
        if (game.isClosed()) {
            game.open();
        }

        if (game.isOpen()) {
            Piece piece = players.isEmpty() ? Piece.X : Piece.O;
            Player player = new Player(piece);

            game.join(player);
            players.put(socketId, player);
        }

        if (game.countPlayers() == 2) {
            game.start();
        }

        if (game.hasStarted()) {
            UUID otherSocketId = players.keySet().stream().findFirst().orElse(null);

            connectionManager.write(socketId, new JoinGameResponseCommand(players.containsKey(socketId)));
            connectionManager.write(otherSocketId, new JoinGameResponseCommand(players.containsKey(otherSocketId)));
        }
    }

    public void leaveGame(UUID socketId) throws JsonProcessingException {
        boolean playerLeft = false;
        Player player = players.get(socketId);
        UUID otherSocketId;

        if (game.isOpen() || game.hasStarted()) {
            playerLeft = game.leave(player);
        }

        if (!playerLeft) {
            return;
        }

        players.remove(socketId);

        otherSocketId = players.keySet().stream().findFirst().orElse(null);

        players.remove(otherSocketId);

        game.close();

        connectionManager.write(socketId, new LeaveGameResponseCommand(!players.containsKey(socketId)));
        connectionManager.write(otherSocketId, new LeaveGameResponseCommand(!players.containsKey(otherSocketId)));
    }

    public void placeGamePiece(UUID socketId, int row, int column) throws JsonProcessingException {
        Player player = players.get(socketId);
        boolean piecePlaced = false;

        piecePlaced = game.placePiece(player, row, column);

        connectionManager.write(
            socketId,
            new PlacePieceResponseCommand(piecePlaced, row, column)
        );
    }
}
