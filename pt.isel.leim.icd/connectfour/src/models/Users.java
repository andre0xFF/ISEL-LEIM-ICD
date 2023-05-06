package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class Users implements Model {

    @JsonProperty
    private final HashMap<String, String> usersNpasswords = new HashMap<>();

    public Boolean authenticate(String username, char[] password) {
        return this.usersNpasswords.containsKey(username)
                && this.usersNpasswords.get(username).equals(String.valueOf(password));
    }

    public boolean register(String username, char[] password) {
        if (this.usersNpasswords.containsKey(username)) {
            return false;
        }

        this.usersNpasswords.put(username, String.valueOf(password));

        return true;
    }
}
