package pt.isel.icd.game.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class LeaveGameCommand implements SimpleSocketCommand<GameServerController> {

    private GameServerController gameServerController;
    private UUID socketId;

    @Override
    public void setReceiver(GameServerController existingGameServerController) {
        gameServerController = existingGameServerController;
    }

    @Override
    public void execute() {
        try {
            gameServerController.leaveGame(socketId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID existingSocketId) {
        socketId = existingSocketId;
    }
}
