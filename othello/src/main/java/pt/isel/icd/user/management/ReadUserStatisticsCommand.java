package pt.isel.icd.user.management;

import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class ReadUserStatisticsCommand implements SimpleSocketCommand<UserServerController> {
    private UUID socketId;
    private UserServerController userServerController;

    @Override
    public void socketId(UUID existingSocketId) {
        socketId = existingSocketId;
    }

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void setReceiver(UserServerController existingUserServerController) {
        userServerController = existingUserServerController;
    }

    @Override
    public void execute() {
        userServerController.readUserStatistics(socketId);
    }
}
