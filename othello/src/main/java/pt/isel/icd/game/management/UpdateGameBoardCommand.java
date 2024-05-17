package pt.isel.icd.game.management;

import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class UpdateGameBoardCommand implements SimpleSocketCommand<GameClientController> {
    private GameClientController gameClientController;
    private UUID socketId;

    @Override
    public void setReceiver(GameClientController existingGameClientController) {
        gameClientController = existingGameClientController;
    }

    @Override
    public void execute() {
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
