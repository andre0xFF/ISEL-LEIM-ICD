package pt.isel.icd.user.management;

import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;
import java.util.UUID;

public class UserClientController implements Controller {
    private final ConnectionManager connectionManager;
    private final UserClientService userClientService;

    public UserClientController(ConnectionManager existingConnectionManager, UserClientService existingUserClientService) {
        connectionManager = existingConnectionManager;
        userClientService = existingUserClientService;

        connectionManager.addMiddleware(new AuthenticationSimpleSocketMiddleware(existingUserClientService));
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {
                add(AuthenticateUserResponseCommand.class);
            }
        };
    }

    public void authenticate(UUID connectionIdentifier, boolean isAuthenticated) {
        userClientService.authenticate(connectionIdentifier, isAuthenticated);
    }
}
