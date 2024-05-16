package pt.isel.icd.user.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;
import pt.isel.icd.user.logic.Profile;
import pt.isel.icd.user.logic.User;

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
                add(ReadUserProfileCommand.class);
            }
        };
    }

    public void authenticateUser(UUID connectionIdentifier, User user) throws JsonProcessingException {
        boolean isAuthenticated = false;

        try {
            userServerService.authenticate(connectionIdentifier, user);

            isAuthenticated = true;
        } catch (IllegalArgumentException ignored) {
        }

        AuthenticateUserResponseCommand authenticateUserResponseCommand = new AuthenticateUserResponseCommand(
                user.username(),
                isAuthenticated
        );

        connectionManager.write(connectionIdentifier, authenticateUserResponseCommand);
    }

    public void deauthenticateUser() {
        // TODO: Implement method
    }

    public void createUser(UUID connectionIdentifier, User user) throws JsonProcessingException {
        boolean isRegistered = false;

        try {
            userServerService.createUser(user);

            isRegistered = true;
        } catch (IllegalArgumentException ignored) {
        }

        CreateUserResponseCommand createUserResponseCommand = new CreateUserResponseCommand(user.username(), isRegistered);

        connectionManager.write(connectionIdentifier, createUserResponseCommand);
    }

    public void deleteUser(UUID connectionIdentifier) {
        // TODO: Implement method
    }

    public void readProfile(UUID connectionIdentifier, String username) throws JsonProcessingException {
        Profile profile = null;

        try {
            profile = userServerService.readProfile(username);
        } catch (IllegalArgumentException ignored) {
        }

        ReadUserProfileResponseCommand readProfileResponseCommand = new ReadUserProfileResponseCommand(profile, profile != null);

        connectionManager.write(connectionIdentifier, readProfileResponseCommand);
    }

    public void readUserStats() {
        // TODO: Implement method
    }

    public void joinGame() {
        // TODO: Implement method
    }

    public void leaveGame() {
        // TODO: Implement method
    }
}
