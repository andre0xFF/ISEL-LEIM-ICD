package pt.isel.icd.game.management;

import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class PlaceGamePieceCommand implements SimpleSocketCommand<GameServerController> {
    private final int row;
    private final int column;

    private GameServerController gameServerController;
    private UUID connectionIdentifier;

    public PlaceGamePieceCommand(int existingRow, int existingColumn) {
        row = existingRow;
        column = existingColumn;
    }

    @Override
    public void setReceiver(GameServerController existingGameServerController) {
        gameServerController = existingGameServerController;
    }

    @Override
    public void execute() {
        gameServerController.placeGamePiece(connectionIdentifier, row, column);
    }

    @Override
    public UUID connectionIdentifier() {
        return connectionIdentifier;
    }

    @Override
    public void connectionIdentifier(UUID existingConnectionIdentifier) {
        connectionIdentifier = existingConnectionIdentifier;
    }
}
