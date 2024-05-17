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
    private final HashMap<UUID, User> users = new HashMap<>();

    public UserServerController(UserServerRepository existingUserServerRepository, ConnectionManager existingConnectionManager) {
        userServerRepository = existingUserServerRepository;
        connectionManager = existingConnectionManager;
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {
                add(AuthenticateUserCommand.class);
                add(DeauthenticateUserCommand.class);
                add(CreateUserCommand.class);
                add(DeleteUserCommand.class);
                add(ReadUserProfileCommand.class);
            }
        };
    }

    public void authenticateUser(UUID socketId, User user) throws JsonProcessingException {
        User existingUser = userServerRepository.readUser(user.username());

        if (user.password().equals(existingUser.password())) {
            users.put(socketId, user);
        }

        AuthenticateUserResponseCommand authenticateUserResponseCommand = new AuthenticateUserResponseCommand(
                user.username(),
                users.containsKey(socketId)
        );

        connectionManager.write(socketId, authenticateUserResponseCommand);
    }

    public void deauthenticateUser(UUID socketId) {
        // TODO: Implement method
    }

    public void createUser(UUID socketId, User user) throws JsonProcessingException {
        if (userServerRepository.readUser(user.username()) == null) {
            userServerRepository.addUser(user);
        }

        CreateUserResponseCommand createUserResponseCommand = new CreateUserResponseCommand(
                user.username(),
                userServerRepository.readUser(user.username()) != null
        );

        connectionManager.write(socketId, createUserResponseCommand);
    }

    public void deleteUser(UUID socketId) {
        // TODO: Implement method
    }

    public void readUserProfile(UUID socketId) throws JsonProcessingException {
        Profile profile = null;
        User user = users.get(socketId);
        profile = userServerRepository.readProfile(user);

        ReadUserProfileResponseCommand readUserProfileResponseCommand = new ReadUserProfileResponseCommand(
                profile,
                profile != null
        );

        connectionManager.write(socketId, readUserProfileResponseCommand);
    }

    public void readUserStatistics(UUID socketId) {
        // TODO: Implement method
    }

    @Override
    public boolean isAuthenticated(UUID socketId) {
        return users.containsKey(socketId);
    }
}
