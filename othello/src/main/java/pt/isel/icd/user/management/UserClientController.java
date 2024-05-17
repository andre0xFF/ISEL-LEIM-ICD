package pt.isel.icd.user.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.game.management.JoinGameCommand;
import pt.isel.icd.game.management.LeaveGameCommand;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;
import pt.isel.icd.user.logic.Profile;
import pt.isel.icd.user.logic.User;

import java.util.ArrayList;
import java.util.UUID;

public class UserClientController implements Controller, Authenticator {
    private final ConnectionManager connectionManager;
    private final UserClientRepository userClientRepository;

    public UserClientController(UserClientRepository existingUserClientRepository, ConnectionManager existingConnectionManager) {
        connectionManager = existingConnectionManager;
        userClientRepository = existingUserClientRepository;
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {
                add(AuthenticateUserResponseCommand.class);
                add(CreateUserResponseCommand.class);
                // add(DeleteUserResponseCommand.class);
                add(ReadUserProfileResponseCommand.class);
            }
        };
    }

    @Override
    public boolean isAuthenticated(UUID socketId) {
        return userClientRepository.username() != null;
    }

    public void authenticateUser(User user) throws JsonProcessingException {
        connectionManager.write(new AuthenticateUserCommand(user));
    }

    protected void handleAuthenticateUserResponse(UUID socketId, String username, boolean isAuthenticated) {
        if (isAuthenticated) {
            userClientRepository.username(username);
        }

        // TODO: Implement method
    }

    public void deauthenticateUser() throws JsonProcessingException {
        connectionManager.write(new DeauthenticateUserCommand());
    }

    protected void handleDeauthenticateUserResponse() {
        // TODO: Implement method
    }

    public void createUser(User user) throws JsonProcessingException {
        connectionManager.write(new CreateUserCommand(user));
    }

    protected void handleCreateUserResponse(UUID socketId, String username, boolean isRegistered) {
        if (isRegistered) {
        }

        // TODO: Implement method
    }

    public void deleteUser() throws JsonProcessingException {
        // TODO: Implement method
        // connectionManager.write(new DeleteUserCommand(username));
    }

    protected void handleDeleteUserResponse(UUID socketId, boolean isDeleted) {
        // TODO: Implement method
        System.out.printf("User %s %s%n", socketId, isDeleted ? "deleted" : "not deleted");
    }

    public void readUserProfile() throws JsonProcessingException {
        connectionManager.write(new ReadUserProfileCommand());
    }

    protected void handleReadUserProfileResponse(UUID socketId, Profile profile, boolean hasProfile) {
        // TODO: Implement method
        System.out.printf("User %s %s%n", socketId, hasProfile ? "has profile" : "does not have profile");
    }

    public void readUserStatistics() {
        // TODO: Implement method
    }

    protected void handleReadUserStatisticsResponse() {
        // TODO: Implement method
    }
}
