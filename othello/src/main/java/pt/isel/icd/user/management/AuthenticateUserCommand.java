package pt.isel.icd.user.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.icd.communication.ConnectionCommand;

import java.util.UUID;

public class AuthenticateUserCommand implements ConnectionCommand<UserServerController> {
    private UserServerController userServerController;
    private UUID connectionIdentifier;

    @JsonProperty
    private final String username;

    @JsonProperty
    private final String password;

    @JsonCreator
    public AuthenticateUserCommand(
            @JsonProperty("username") String existingUsername,
            @JsonProperty("password") String existingPassword
    ) {
        username = existingUsername;
        password = existingPassword;
    }

    @Override
    public void setReceiver(UserServerController existingUserServerController) {
        userServerController = existingUserServerController;
    }

    @Override
    public void execute() {
        userServerController.authenticate(connectionIdentifier, username, password);
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
