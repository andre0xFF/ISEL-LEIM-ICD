package pt.isel.icd.game.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class LeaveGameResponseCommand implements SimpleSocketCommand<GameClientController> {

    @JsonProperty
    private final boolean leftGame;

    private UUID socketId;
    private GameClientController gameClientController;

    @JsonCreator
    public LeaveGameResponseCommand(
            @JsonProperty("leftGame") boolean existingLeftGame
    ) {
        leftGame = existingLeftGame;
    }

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID existingSocketId) {
        socketId = existingSocketId;
    }

    @Override
    public void setReceiver(GameClientController existingGameClientController) {
        gameClientController = existingGameClientController;
    }

    @Override
    public void execute() {
        gameClientController.handleLeaveGameResponse(leftGame);
    }
}
