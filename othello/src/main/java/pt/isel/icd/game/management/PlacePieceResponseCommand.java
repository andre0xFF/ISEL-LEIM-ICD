package pt.isel.icd.game.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class PlacePieceResponseCommand implements SimpleSocketCommand<GameClientController> {

    @JsonProperty
    private final boolean placedPiece;

    @JsonProperty
    private final int row;

    @JsonProperty
    private final int column;

    private UUID socketId;
    private GameClientController gameClientController;

    @JsonCreator
    public PlacePieceResponseCommand(
            @JsonProperty("placedPiece") boolean existingPlacedPiece,
            @JsonProperty("row") int existingRow,
            @JsonProperty("column") int existingColumn
    ) {
        placedPiece = existingPlacedPiece;
        row = existingRow;
        column = existingColumn;
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
        gameClientController.handlePlacePieceResponse(placedPiece, row, column);
    }
}
