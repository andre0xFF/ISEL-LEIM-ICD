package pt.isel.icd.user.management;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class ReadProfileCommand implements SimpleSocketCommand<UserServerController> {
    private UUID connectionIdentifier;
    private UserServerController userServerController;

    @JsonProperty
    private final String username;

    public ReadProfileCommand(
            @JsonProperty("username") String existingUsername
    ) {
        username = existingUsername;
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
    public void setReceiver(UserServerController existingUserServerControoler) {
        userServerController = existingUserServerControoler;
    }

    @Override
    public void execute() {
        try {
            userServerController.readProfile(connectionIdentifier, username);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
