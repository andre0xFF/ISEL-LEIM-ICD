package pt.isel.icd.user.management;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pt.isel.icd.patterns.verticals.Entity;

import java.io.File;
import java.util.ArrayList;

@JacksonXmlRootElement(localName = "users")
public class Users implements Entity {

    @JacksonXmlProperty(localName = "user")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final ArrayList<User> users = new ArrayList<>();

    public User read(String username) {
        return users.stream()
                .filter(user -> user.username().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void add(User user) {
        if (read(user.username()) != null) {
            throw new IllegalArgumentException("User already exists");
        }

        users.add(user);
    }

    public void remove(User user) {
        if (read(user.username()) == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        users.remove(user);
    }
}
