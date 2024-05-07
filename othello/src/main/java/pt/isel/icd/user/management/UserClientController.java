package pt.isel.icd.user.management;

import pt.isel.icd.communication.ClientsManager;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;

public class UserClientController implements Controller {
    private final ClientsManager clientsManager;

    public UserClientController(ClientsManager existingClientsManager) {
        clientsManager = existingClientsManager;
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {

            }
        };
    }
}