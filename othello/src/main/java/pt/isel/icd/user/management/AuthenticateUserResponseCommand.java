package pt.isel.icd.user.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class AuthenticateUserResponseCommand implements SimpleSocketCommand<UserClientController> {
    private UUID connectionIdentifier;
    private UserClientController userClientController;

    @JsonProperty
    private final boolean isAutenticated;

    @JsonCreator
    public AuthenticateUserResponseCommand(
            @JsonProperty("isAuthenticated") boolean existingIsAutenticated
    ) {
        isAutenticated = existingIsAutenticated;
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
    public void setReceiver(UserClientController existingUserClientController) {
        userClientController = existingUserClientController;
    }

    @Override
    public void execute() {
        userClientController.authenticate(connectionIdentifier, isAutenticated);
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }
}
