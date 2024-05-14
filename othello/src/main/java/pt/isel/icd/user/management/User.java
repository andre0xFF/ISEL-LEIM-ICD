package pt.isel.icd.user.management;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pt.isel.icd.patterns.verticals.Entity;

@JacksonXmlRootElement(localName = "user")
public record User(
    @JacksonXmlProperty(localName = "username")
    String username,

    @JacksonXmlProperty(localName = "password")
    String password
) implements Entity {
    public User {
        if (username == null) {
            throw new IllegalArgumentException("Username must be a non-null string");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password must be a non-null string");
        }
    }
}
