package pt.isel.icd.user.management;

import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class DeauthenticateUserResponseCommand implements SimpleSocketCommand<UserClientController> {

    private UserClientController userClientController;
    private UUID socketId;

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID existingSocketId) {
        socketId = existingSocketId;
    }

    @Override
    public void setReceiver(UserClientController existingUserClientController) {
        userClientController = existingUserClientController;
    }

    @Override
    public void execute() {
        userClientController.handleDeauthenticateUserResponse();
    }
}
