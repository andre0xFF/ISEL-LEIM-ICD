package pt.isel.icd.game.management;


import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.ConnectedCommand;
import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.communication.DisconnectedCommand;
import pt.isel.icd.game.logic.Piece;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;

public class GameClientController implements Controller {
    private final ConnectionManager connectionManager;

    public GameClientController(ConnectionManager existingConnectionManager) {
        connectionManager = existingConnectionManager;
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {
                add(ConnectedCommand.class);
                add(DisconnectedCommand.class);
                add(JoinGameResponseCommand.class);
                add(LeaveGameResponseCommand.class);
                add(PlacePieceResponseCommand.class);
            }
        };
    }

    public void joinGame() throws JsonProcessingException {
        connectionManager.write(new JoinGameCommand());
    }

    protected void handleJoinGameResponse(boolean joinedGame) {
        // TODO: Implement method
    }

    public void leaveGame() throws JsonProcessingException {
        connectionManager.write(new LeaveGameCommand());
    }

    protected void handleLeaveGameResponse(boolean leftGame) {
        // TODO: Implement method
    }

    public void placePiece(int row, int column) throws JsonProcessingException {
        connectionManager.write(new PlacePieceCommand(row, column));
    }

    protected void handlePlacePieceResponse(boolean placedPiece, int row, int column) {
        // TODO: Implement method
    }
}
