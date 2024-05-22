package pt.isel.icd.user.management;

import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class DeauthenticateUserCommand implements SimpleSocketCommand<UserServerController> {

    private UUID socketId;
    private UserServerController userServerController;

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID existingSocketId) {
        socketId = existingSocketId;
    }

    @Override
    public void setReceiver(UserServerController existingUserServerController) {
        userServerController = existingUserServerController;
    }

    @Override
    public void execute() {
        userServerController.deauthenticateUser(socketId);
    }
}
