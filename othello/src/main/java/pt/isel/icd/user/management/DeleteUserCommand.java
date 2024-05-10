package pt.isel.icd.user.management;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class DeleteUserCommand implements SimpleSocketCommand<UserServerController> {
    private UserServerController userServerController;
    private UUID connectionIdentifier;

    @JsonProperty
    private final String username;

    public DeleteUserCommand(@JsonProperty("username") String existingUsername) {
        username = existingUsername;
    }

    @Override
    public void setReceiver(UserServerController existingUserServerController) {
        userServerController = existingUserServerController;
    }

    @Override
    public void execute() {
        userServerController.deleteUser(connectionIdentifier);
    }

    @Override
    public UUID connectionIdentifier() {
        return connectionIdentifier;
    }

    @Override
    public void connectionIdentifier(UUID existingConnectionIdentifier) {
        connectionIdentifier = existingConnectionIdentifier;
    }
}
