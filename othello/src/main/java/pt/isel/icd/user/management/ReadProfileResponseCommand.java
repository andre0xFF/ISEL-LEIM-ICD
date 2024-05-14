package pt.isel.icd.user.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import pt.isel.icd.communication.SimpleSocketCommand;

import java.util.UUID;

public class ReadProfileResponseCommand implements SimpleSocketCommand<UserClientController>  {
    private UUID connectionIdentifier;
    private UserClientController userClientController;

    @JsonProperty
    private final boolean hasProfile;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Profile profile;

    @JsonCreator
    public ReadProfileResponseCommand(
            @JsonProperty("profile")
            Profile existingProfile,

            @JsonProperty("hasProfile")
            boolean existingHasProfile
    ) {
        hasProfile = existingHasProfile;
        profile = existingProfile;
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
        userClientController.handleReadProfileResponse(connectionIdentifier, profile, hasProfile);
    }
}
