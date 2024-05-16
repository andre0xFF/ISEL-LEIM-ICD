package pt.isel.icd.user.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.user.logic.User;

import java.util.UUID;

public class AuthenticateUserCommand implements SimpleSocketCommand<UserServerController> {
    private UserServerController userServerController;
    private UUID connectionIdentifier;

    @JsonProperty
    private final User user;

    @JsonCreator
    public AuthenticateUserCommand(
            @JsonProperty("user") User existingUser
    ) {
        user = existingUser;
    }

    @Override
    public void setReceiver(UserServerController existingUserServerController) {
        userServerController = existingUserServerController;
    }

    @Override
    public void execute() {
        try {
            userServerController.authenticateUser(connectionIdentifier, user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID connectionIdentifier() {
        return connectionIdentifier;
    }

    @Override
    public void connectionIdentifier(UUID existingConnectionIdentifier) {
        connectionIdentifier = existingConnectionIdentifier;
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }
}
