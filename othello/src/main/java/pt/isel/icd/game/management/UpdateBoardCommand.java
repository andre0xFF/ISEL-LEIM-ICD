package pt.isel.icd.game.management;

import pt.isel.icd.communication.ConnectionCommand;

import java.util.UUID;

public class UpdateBoardCommand implements ConnectionCommand<GameClientController> {
    private GameClientController gameClientController;
    private UUID connectionIdentifier;

    @Override
    public void setReceiver(GameClientController existingGameClientController) {
        gameClientController = existingGameClientController;
    }

    @Override
    public void execute() {
        gameClientController.updateBoard();
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
