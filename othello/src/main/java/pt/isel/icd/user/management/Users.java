package pt.isel.icd.user.management;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pt.isel.icd.patterns.verticals.Entity;

import java.util.ArrayList;

@JacksonXmlRootElement(localName = "users")
public class Users implements Entity {

    @JacksonXmlProperty(localName = "user")
    @JacksonXmlElementWrapper(useWrapping = false)
    private ArrayList<User> users = new ArrayList<>();

    public User user(String username) {
        return users.stream()
                .filter(user -> user.username().equals(username))
                .findFirst()
                .orElse(null);
    }

    public User add(String username, String password) {
        if (user(username) != null) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User(username, password);

        users.add(user);

        return user;
    }

    public User remove(String username) {
        User user = user(username);

        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        users.remove(user);

        return user;
    }
}
