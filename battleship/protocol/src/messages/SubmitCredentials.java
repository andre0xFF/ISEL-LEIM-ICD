package messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;

public class SubmitCredentials implements Message {

    private final String username;
    private final String password;
    private final String controllerName;

    @JsonCreator
    public SubmitCredentials(
            @JsonSetter("controllerName") String controllerName,
            @JsonSetter("username") String username,
            @JsonSetter("password") String password
    ) {
        this.controllerName = controllerName;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getControllerName() {
        return controllerName;
    }
}
