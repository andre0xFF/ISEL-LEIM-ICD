package pt.isel.icd.user.management;

import pt.isel.icd.communication.ConnectionCommand;

import java.util.UUID;

public class DeleteUserCommand implements ConnectionCommand<UserServerController> {
    private UserServerController userServerController;
    private UUID connectionIdentifier;

    @Override
    public void setReceiver(UserServerController existingUserServerController) {
        userServerController = existingUserServerController;
    }

    @Override
    public void execute() {
        userServerController.deleteUser(connectionIdentifier);
    }

    @Override
    public void connectionIdentifier(UUID existingConnectionIdentifier) {
        connectionIdentifier = existingConnectionIdentifier;
    }
}
