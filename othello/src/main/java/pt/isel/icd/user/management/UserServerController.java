package pt.isel.icd.user.management;

import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;
import java.util.UUID;

public class UserServerController implements Controller {
    private final ConnectionManager connectionManager;

    public UserServerController(ConnectionManager existingConnectionManager) {
        connectionManager = existingConnectionManager;

//        connectionManager.addMiddleware(new AuthenticationMiddleware());
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {

            }
        };
    }

    public void authenticate(UUID connectionIdentifier, String username, String password) {

    }

    public void createUser(UUID connectionIdentifier, String username, String password) {

    }

    public void deleteUser(UUID connectionIdentifier) {

    }
}
