package pt.isel.icd.game.management;

import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class PlaceGamePieceCommand implements SimpleSocketCommand<GameServerController> {
    private final int row;
    private final int column;

    private GameServerController gameServerController;
    private UUID socketId;

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
        gameServerController.placeGamePiece(socketId, row, column);
    }

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID existingsocketId) {
        socketId = existingsocketId;
    }
}
