package pt.isel.icd.game.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class JoinGameResponseCommand implements SimpleSocketCommand<GameClientController> {
    
    @JsonProperty
    private final boolean joinedGame;
    private UUID socketId;
    private GameClientController gameClientController;

    @JsonCreator
    public JoinGameResponseCommand(
            @JsonProperty("joinedGame") boolean existingJoinedGame
    ) {
        joinedGame = existingJoinedGame;
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
        gameClientController.handleJoinGameResponse(joinedGame);
    }
}
