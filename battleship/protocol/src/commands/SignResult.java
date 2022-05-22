package commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;

public class SignResult implements CommandResult {
    private final boolean authentication;
    private final boolean registration;

    @JsonCreator
    public SignResult(
            @JsonSetter("authentication") boolean authentication,
            @JsonSetter("registration") boolean registration
    ) {
        this.authentication = authentication;
        this.registration = registration;
    }

    public boolean getAuthentication() {
        return authentication;
    }

    public boolean getRegistration() {
        return registration;
    }
}
