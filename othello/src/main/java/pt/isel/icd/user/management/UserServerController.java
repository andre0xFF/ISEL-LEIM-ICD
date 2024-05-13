package pt.isel.icd.user.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;
import java.util.UUID;

public class UserServerController implements Controller {
    private final UserServerService userServerService;
    private final ConnectionManager connectionManager;

    public UserServerController(UserServerService existingUserServerService, ConnectionManager existingConnectionManager) {
        userServerService = existingUserServerService;
        connectionManager = existingConnectionManager;

        connectionManager.addMiddleware(new AuthenticationSimpleSocketMiddleware(userServerService));
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {
                add(AuthenticateUserCommand.class);
                add(CreateUserCommand.class);
                add(DeleteUserCommand.class);
            }
        };
    }

    public void authenticate(UUID connectionIdentifier, String username, String password) throws JsonProcessingException {
        boolean isAuthenticated = false;
        User user = userServerService.authenticate(username, password);

        if (user != null) {
            isAuthenticated = true;
        }

        userServerService.authenticate(connectionIdentifier, isAuthenticated);
        connectionManager.write(connectionIdentifier, new AuthenticateUserResponseCommand(isAuthenticated));
    }

    public void createUser(UUID connectionIdentifier, String username, String password) {

    }

    public void deleteUser(UUID connectionIdentifier) {

    }
}
