package pt.isel.icd.game.management;


import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;

public class GameClientController implements Controller {
    private final GameController gameController;
    private final ConnectionManager connectionManager;

    public GameClientController(ConnectionManager existingConnectionManager) {
        connectionManager = existingConnectionManager;
        gameController = new GameController(existingConnectionManager);
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>(gameController.commandsList()) {
            {
                add(UpdateGameBoardCommand.class);
            }
        };
    }

    public void joinGame() throws JsonProcessingException {
        connectionManager.write(new JoinGameCommand());
    }

    protected void handleJoinGameResponse() {
        // TODO: Implement method
    }

    public void leaveGame() throws JsonProcessingException {
        connectionManager.write(new LeaveGameCommand());
    }

    protected void handleLeaveGameResponse() {
        // TODO: Implement method
    }

    public void placeGamePiece(int row, int column) throws JsonProcessingException {
        connectionManager.write(new PlaceGamePieceCommand(row, column));
    }

    protected void handlePlaceGamePieceResponse() {
        // TODO: Implement method
    }

    public void showGameBoard() throws JsonProcessingException {
        connectionManager.write(new ShowGameBoardCommand());
    }

    protected void handleShowGameBoardResponse() {
        // TODO: Implement method
    }
}
