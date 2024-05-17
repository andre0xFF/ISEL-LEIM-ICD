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

public class UserClientController implements Controller, Authenticator {
    private final ConnectionManager connectionManager;
    private final UserClientRepository userClientRepository;

    private boolean isAuthenticated;

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
    public boolean isAuthenticated(UUID connectionIdentifier) {
        return isAuthenticated;
    }

    public void authenticateUser(User user) throws JsonProcessingException {
        connectionManager.write(new AuthenticateUserCommand(user));
    }

    public void handleAuthenticateUserResponse(UUID connectionIdentifier, String username, boolean existingIsAuthenticated) {
        isAuthenticated = existingIsAuthenticated;

        if (isAuthenticated) {
            userClientRepository.username(username);
        }

        // TODO: Implement method
    }

    public void deauthenticateUser() throws JsonProcessingException {
        connectionManager.write(new DeauthenticateUserCommand());
    }

    public void handleDeauthenticateUserResponse() {
        // TODO: Implement method
    }

    public void createUser(User user) throws JsonProcessingException {
        connectionManager.write(new CreateUserCommand(user));
    }

    public void handleCreateUserResponse(UUID connectionIdentifier, String username, boolean isRegistered) {
        if (isRegistered) {
        }

        // TODO: Implement method
    }

    public void deleteUser() throws JsonProcessingException {
        // TODO: Implement method
        // connectionManager.write(new DeleteUserCommand(username));
    }

    public void handleDeleteUserResponse(UUID connectionIdentifier, boolean isDeleted) {
        // TODO: Implement method
        System.out.printf("User %s %s%n", connectionIdentifier, isDeleted ? "deleted" : "not deleted");
    }

    public void readUserProfile() throws JsonProcessingException {
        connectionManager.write(new ReadUserProfileCommand());
    }

    public void handleReadUserProfileResponse(UUID connectionIdentifier, Profile profile, boolean hasProfile) {
        // TODO: Implement method
        System.out.printf("User %s %s%n", connectionIdentifier, hasProfile ? "has profile" : "does not have profile");
    }

    public void readUserStatistics() {
        // TODO: Implement method
    }

    public void handleReadUserStatisticsResponse() {
        // TODO: Implement method
    }

    public void joinGame() {
        // TODO: Implement method
    }

    public void handleJoinGameResponse() {
        // TODO: Implement method
    }

    public void leaveGame() {
        // TODO: Implement method
    }

    public void leaveGameResponse() {
        // TODO: Implement method
    }
}
