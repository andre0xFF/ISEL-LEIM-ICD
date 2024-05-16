package pt.isel.icd.user.management;

import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class DeauthenticateUserCommand implements SimpleSocketCommand<UserServerController> {

    private UUID connectionIdentifier;
    private UserServerController userServerController;

    @Override
    public UUID connectionIdentifier() {
        return connectionIdentifier;
    }

    @Override
    public void connectionIdentifier(UUID existingConnectionIdentifier) {
        connectionIdentifier = existingConnectionIdentifier;
    }

    @Override
    public void setReceiver(UserServerController existingUserServerController) {
        userServerController = existingUserServerController;
    }

    @Override
    public void execute() {

    }
}
