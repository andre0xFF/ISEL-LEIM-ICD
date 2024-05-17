package pt.isel.icd.user.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.user.logic.User;

import java.util.UUID;

public class AuthenticateUserCommand implements SimpleSocketCommand<UserServerController> {
    private UserServerController userServerController;
    private UUID socketId;

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
            userServerController.authenticateUser(socketId, user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID existingSocketId) {
        socketId = existingSocketId;
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }
}
