package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Account {
    private final String username;
    private final String password;

    @JsonCreator
    public Account(
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
