package pt.isel.icd.game.management;


import pt.isel.icd.communication.ClientsManager;
import pt.isel.icd.communication.ConnectCommand;
import pt.isel.icd.communication.DisconnectCommand;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;

public class GameClientController implements Controller {
    private final GameController gameController;
    private final ClientsManager clientsManager;

    public GameClientController(ClientsManager existingClientsManager) {
        clientsManager = existingClientsManager;
        gameController = new GameController(existingClientsManager);
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>(gameController.commandsList()) {
            {
                add(ConnectCommand.class);
                add(DisconnectCommand.class);
            }
        };
    }
}
