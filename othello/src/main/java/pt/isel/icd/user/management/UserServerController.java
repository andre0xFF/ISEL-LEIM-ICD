package pt.isel.icd.user.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;
import pt.isel.icd.user.logic.Profile;
import pt.isel.icd.user.logic.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserServerController implements Controller, Authenticator {
    private final UserServerRepository userServerRepository;
    private final ConnectionManager connectionManager;
    private final HashMap<UUID, User> areAuthenticated = new HashMap<>();

    public UserServerController(UserServerRepository existingUserServerRepository, ConnectionManager existingConnectionManager) {
        userServerRepository = existingUserServerRepository;
        connectionManager = existingConnectionManager;
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
        User existingUser = userServerRepository.readUser(user.username());

        if (user.password().equals(existingUser.password())) {
            areAuthenticated.put(connectionIdentifier, user);
        }

        AuthenticateUserResponseCommand authenticateUserResponseCommand = new AuthenticateUserResponseCommand(
                user.username(),
                areAuthenticated.containsKey(connectionIdentifier)
        );

        connectionManager.write(connectionIdentifier, authenticateUserResponseCommand);
    }

    public void deauthenticateUser() {
        // TODO: Implement method
    }

    public void createUser(UUID connectionIdentifier, User user) throws JsonProcessingException {
        if (userServerRepository.readUser(user.username()) == null) {
            userServerRepository.addUser(user);
        }

        CreateUserResponseCommand createUserResponseCommand = new CreateUserResponseCommand(
                user.username(),
                userServerRepository.readUser(user.username()) != null
        );

        connectionManager.write(connectionIdentifier, createUserResponseCommand);
    }

    public void deleteUser(UUID connectionIdentifier) {
        // TODO: Implement method
    }

    public void readUserProfile(UUID connectionIdentifier) throws JsonProcessingException {
        Profile profile = null;
        User user = areAuthenticated.get(connectionIdentifier);
        profile = userServerRepository.readProfile(user);

        ReadUserProfileResponseCommand readUserProfileResponseCommand = new ReadUserProfileResponseCommand(
                profile,
                profile != null
        );

        connectionManager.write(connectionIdentifier, readUserProfileResponseCommand);
    }

    public void readUserStatistics() {
        // TODO: Implement method
    }

    public void joinGame() {
        // TODO: Implement method
    }

    public void leaveGame() {
        // TODO: Implement method
    }

    @Override
    public boolean isAuthenticated(UUID connectionIdentifier) {
        return areAuthenticated.containsKey(connectionIdentifier);
    }
}
