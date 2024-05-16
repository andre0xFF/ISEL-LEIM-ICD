package pt.isel.icd.user.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class ReadUserProfileCommand implements SimpleSocketCommand<UserServerController> {
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
    public void setReceiver(UserServerController existingUserServerControoler) {
        userServerController = existingUserServerControoler;
    }

    @Override
    public void execute() {
        try {
            userServerController.readUserProfile(connectionIdentifier);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
