package controllers_bkp.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import controllers_bkp.Message;

public class Authenticate implements Message {

    private final String username;
    private final String password;

    @JsonCreator
    public Authenticate(
            @JsonSetter("username") String username,
            @JsonSetter("password") String password
    ) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
