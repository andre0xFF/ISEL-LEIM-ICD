package pt.isel.icd.user.management;

import pt.isel.icd.communication.ConnectionCommand;

import java.util.UUID;

public class AuthenticateUserCommand implements ConnectionCommand<UserServerController> {
    private UserServerController userServerController;
    private UUID connectionIdentifier;
    private String username;
    private String password;

    @Override
    public void setReceiver(UserServerController existingUserServerController) {
        userServerController = existingUserServerController;
    }

    @Override
    public void execute() {
        userServerController.authenticate(connectionIdentifier, username, password);
    }

    @Override
    public void connectionIdentifier(UUID existingConnectionIdentifier) {
        connectionIdentifier = existingConnectionIdentifier;
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }
}
