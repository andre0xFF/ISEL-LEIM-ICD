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
                // add(DeleteUserResponseCommand.class);
                add(ReadProfileResponseCommand.class);
            }
        };
    }

    public void authenticate(User user) throws JsonProcessingException {
        connectionManager.write(new AuthenticateUserCommand(user));
    }

    public void handleAuthenticationResponse(UUID connectionIdentifier, User user, boolean isAuthenticated) {
        if (isAuthenticated) {
            userClientService.authenticate(connectionIdentifier, user);
        }

        // TODO: Implement this method
        System.out.printf("User %s %s%n", connectionIdentifier, isAuthenticated ? "authenticated" : "not authenticated");
    }

    public void createUser(User user) throws JsonProcessingException {
        connectionManager.write(new CreateUserCommand(user));
    }

    public void handleCreateUserResponse(UUID connectionIdentifier, User user, boolean isRegistered) {
        if (isRegistered) {
            userClientService.authenticate(connectionIdentifier, user);
        }

        // TODO: Implement this method
        System.out.printf("User %s %s%n", connectionIdentifier, isRegistered ? "registered" : "not registered");
    }

    public void deleteUser() throws JsonProcessingException {
        // TODO: Implement this method
        // connectionManager.write(new DeleteUserCommand(username));
    }

    public void handleDeleteUserResponse(UUID connectionIdentifier, boolean isDeleted) {
        // TODO: Implement this method
        System.out.printf("User %s %s%n", connectionIdentifier, isDeleted ? "deleted" : "not deleted");
    }

    public void readProfile() throws JsonProcessingException {
        connectionManager.write(new ReadProfileCommand(userClientService.user().username()));
    }

    public void handleReadProfileResponse(UUID connectionIdentifier, Profile profile, boolean hasProfile) {
        // TODO: Implement this method
        System.out.printf("User %s %s%n", connectionIdentifier, hasProfile ? "has profile" : "does not have profile");
    }
}
