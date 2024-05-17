package pt.isel.icd.user.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.user.logic.Profile;

import java.util.UUID;

public class ReadUserProfileResponseCommand implements SimpleSocketCommand<UserClientController> {
    private UUID socketId;
    private UserClientController userClientController;

    @JsonProperty
    private final boolean hasProfile;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Profile profile;

    @JsonCreator
    public ReadUserProfileResponseCommand(
            @JsonProperty("profile")
            Profile existingProfile,

            @JsonProperty("hasProfile")
            boolean existingHasProfile
    ) {
        hasProfile = existingHasProfile;
        profile = existingProfile;
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
    public void setReceiver(UserClientController existingUserClientController) {
        userClientController = existingUserClientController;
    }

    @Override
    public void execute() {
        userClientController.handleReadUserProfileResponse(profile, hasProfile);
    }
}
