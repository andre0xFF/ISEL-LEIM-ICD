package pt.isel.icd.game.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class PlacePieceCommand implements SimpleSocketCommand<GameServerController> {

    @JsonProperty
    private final int row;
    @JsonProperty
    private final int column;

    private GameServerController gameServerController;
    private UUID socketId;

    @JsonCreator
    public PlacePieceCommand(
            @JsonProperty("row") int existingRow,
            @JsonProperty("column") int existingColumn
    ) {
        row = existingRow;
        column = existingColumn;
    }

    @Override
    public void setReceiver(GameServerController existingGameServerController) {
        gameServerController = existingGameServerController;
    }

    @Override
    public void execute() {
        try {
            gameServerController.placePiece(socketId, row, column);
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
