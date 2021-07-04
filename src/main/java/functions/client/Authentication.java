package functions.client;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import application.Server;
import application.models.Function;
import application.models.User;

public class Authentication implements Function<Server> {

    private Server server;
    private String username;
    private String password;
    private Boolean isAuthenticated = false;
    private User user;

    @JsonCreator
    public Authentication(
        @JsonProperty(value = "username") String username,
        @JsonProperty(value = "password") String password
    ) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() {
        try {
            User user = this.server.loadUser(this.username);

            if (user == null) {
                return;
            }

            if (!this.password.equals(user.getPassword())) {
                return;
            }

            this.isAuthenticated = true;
            this.user = user;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setReceiver(Server server) {
        this.server = server;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the isAuthenticated
     */
    @JsonIgnore
    public Boolean isAuthenticated() {
        return isAuthenticated;
    }

    /**
     * @return the user
     */
    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    
}
