package pt.isel.icd.user.management;

import pt.isel.icd.communication.ConnectionCommand;

import java.util.UUID;

public class UpdateUserCommand implements ConnectionCommand<UserClientController> {
    private UserClientController userServerController;
    private UUID connectionIdentifier;

    @Override
    public void setReceiver(UserClientController existingUserClientController) {
        userServerController = existingUserClientController;
    }

    @Override
    public void execute() {
    }

    @Override
    public void connectionIdentifier(UUID existingConnectionIdentifier) {
    }
}
