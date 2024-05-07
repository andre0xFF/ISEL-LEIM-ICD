package pt.isel.icd.game.management;


import pt.isel.icd.communication.ClientsManager;
import pt.isel.icd.communication.PingCommand;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;

public class GameController implements Controller {
    private final ClientsManager clientsManager;

    public GameController(ClientsManager existingClientsManager) {
        clientsManager = existingClientsManager;
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {
                add(PingCommand.class);
            }
        };
    }
}
