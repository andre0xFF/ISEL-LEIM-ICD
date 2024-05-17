package pt.isel.icd.game.management;

import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class ShowGameBoardCommand implements SimpleSocketCommand<GameServerController> {
    private GameServerController gameServerController;
    private UUID socketId;

    @Override
    public void setReceiver(GameServerController existingGameServerController) {
        gameServerController = existingGameServerController;
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
