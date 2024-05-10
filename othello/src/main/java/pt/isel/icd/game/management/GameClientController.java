package pt.isel.icd.game.management;


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
                add(UpdateBoardCommand.class);
            }
        };
    }

    public void updateBoard() {

    }
}
