package pt.isel.icd.user.management;

import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;
import java.util.UUID;

public class UserClientController implements Controller, Authenticator {
    private final ConnectionManager connectionManager;

    public UserClientController(ConnectionManager existingConnectionManager) {
        connectionManager = existingConnectionManager;

        connectionManager.addMiddleware(new AuthenticationSimpleSocketMiddleware(this));
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {
                add(UpdateUserCommand.class);
            }
        };
    }

    @Override
    public boolean isAuthenticated(UUID connectionIdentifier) {
        // TODO verify authentication
        return false;
    }
}