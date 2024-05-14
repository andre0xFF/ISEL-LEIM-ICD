package pt.isel.icd.user.management;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class CreateUserResponseCommand implements SimpleSocketCommand<UserClientController> {
    private UUID connectionIdentifier;
    private UserClientController userClientController;

    @JsonProperty
    private final boolean isRegistered;

    public CreateUserResponseCommand(
            @JsonProperty("isRegistered") boolean existingIsRegistered
    ) {
        isRegistered = existingIsRegistered;
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
        userClientController.handleCreateUserResponse(connectionIdentifier, isRegistered);
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }
}
