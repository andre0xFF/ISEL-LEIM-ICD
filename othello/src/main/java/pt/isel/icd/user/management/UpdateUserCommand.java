package pt.isel.icd.user.management;

import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class UpdateUserCommand implements SimpleSocketCommand<UserClientController> {
    private UserClientController userServerController;
    private UUID socketId;

    @Override
    public void setReceiver(UserClientController existingUserClientController) {
        userServerController = existingUserClientController;
    }

    @Override
    public void execute() {
    }

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID existingSocketId) {
        socketId = existingSocketId;
    }
}
