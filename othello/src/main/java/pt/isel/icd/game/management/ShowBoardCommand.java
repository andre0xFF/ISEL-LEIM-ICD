package pt.isel.icd.game.management;

import pt.isel.icd.communication.ConnectionCommand;

import java.util.UUID;

public class ShowBoardCommand implements ConnectionCommand<GameServerController> {
    private GameServerController gameServerController;
    private UUID connectionIdentifier;

    @Override
    public void setReceiver(GameServerController existingGameServerController) {
        gameServerController = existingGameServerController;
    }

    @Override
    public void execute() {
        gameServerController.showBoard(connectionIdentifier);
    }

    @Override
    public void connectionIdentifier(UUID existingConnectionIdentifier) {
        connectionIdentifier = existingConnectionIdentifier;
    }
}
