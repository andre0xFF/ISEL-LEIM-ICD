package pt.isel.icd.user.management;

import com.fasterxml.jackson.core.JsonProcessingException;
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
                add(CreateUserResponseCommand.class);
            }
        };
    }

    public void authenticate(String username, String password) throws JsonProcessingException {
        connectionManager.write(new AuthenticateUserCommand(username, password));
    }

    public void handleAuthenticationResponse(UUID connectionIdentifier, boolean isAuthenticated) {
        userClientService.authenticate(connectionIdentifier, isAuthenticated);

        // TODO: Implement this method
        System.out.printf("User %s %s%n", connectionIdentifier, isAuthenticated ? "authenticated" : "not authenticated");
    }

    public void createUser(String username, String password) throws JsonProcessingException {
        connectionManager.write(new CreateUserCommand(username, password));
    }

    public void handleCreateUserResponse(UUID connectionIdentifier, boolean isRegistered) {
        // TODO: Implement this method
        System.out.printf("User %s %s%n", connectionIdentifier, isRegistered ? "created" : "not created");
    }
}
