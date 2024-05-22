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
import java.util.logging.Logger;

public class UserClientController implements Controller, Authenticator {
    private static final Logger logger = Logger.getLogger(UserClientController.class.getName());

    private final ConnectionManager connectionManager;

    private String username;

    public UserClientController(ConnectionManager existingConnectionManager) {
        connectionManager = existingConnectionManager;
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
        return !username.isEmpty();
    }

    public void authenticateUser(User user) throws JsonProcessingException {
        connectionManager.write(new AuthenticateUserCommand(user));
    }

    protected void handleAuthenticateUserResponse(String existingUsername, boolean isAuthenticated) {
        if (isAuthenticated) {
            username = existingUsername;
        }

        logger.info(isAuthenticated ? "User authenticated" : "User not authenticated");
    }

    public void deauthenticateUser() throws JsonProcessingException {
        connectionManager.write(new DeauthenticateUserCommand());
    }

    protected void handleDeauthenticateUserResponse() {
        logger.info("User deauthenticated");
    }

    public void createUser(User user) throws JsonProcessingException {
        connectionManager.write(new CreateUserCommand(user));
    }

    protected void handleCreateUserResponse(String username, boolean isRegistered) {
        logger.info(isRegistered ? "User registered" : "User not registered");
    }

    public void deleteUser() throws JsonProcessingException {
        // TODO: Implement method
        // connectionManager.write(new DeleteUserCommand(username));
    }

    protected void handleDeleteUserResponse(boolean isDeleted) {
        logger.info(isDeleted ? "User deleted" : "User not deleted");
    }

    public void readUserProfile() throws JsonProcessingException {
        connectionManager.write(new ReadUserProfileCommand());
    }

    protected void handleReadUserProfileResponse(Profile profile, boolean hasProfile) {
        // TODO: Implement method
        System.out.printf("User %s%n", hasProfile ? "has profile" : "does not have profile");
    }

    public void readUserStatistics() {
        // TODO: Implement method
    }

    protected void handleReadUserStatisticsResponse() {
        // TODO: Implement method
    }
}
