package pt.isel.icd.game.management;

import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;
import java.util.UUID;

public class GameServerController implements Controller {
    private final GameController gameController;
    private final ConnectionManager connectionManager;

    public GameServerController(ConnectionManager existingConnectionManager) {
        connectionManager = existingConnectionManager;
        gameController = new GameController(existingConnectionManager);
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>(gameController.commandsList()) {
            {
                add(ShowGameBoardCommand.class);
                add(JoinGameCommand.class);
                add(LeaveGameCommand.class);
                add(PlaceGamePieceCommand.class);
            }
        };
    }

    public void joinGame(UUID connectionIdentifier) {
        // TODO: Implement method
    }

    public void leaveGame(UUID connectionIdentifier) {
        // TODO: Implement method
    }

    public void placeGamePiece(UUID connectionIdentifier, int row, int column) {
        // TODO: Implement method
    }

    public void showGameBoard(UUID connectionIdentifier) {
        // TODO: Implement method
    }
}
