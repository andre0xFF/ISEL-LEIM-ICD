package pt.isel.icd.user.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class ReadUserProfileCommand implements SimpleSocketCommand<UserServerController> {
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
    public void setReceiver(UserServerController existingUserServerControoler) {
        userServerController = existingUserServerControoler;
    }

    @Override
    public void execute() {
        try {
            userServerController.readUserProfile(socketId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
