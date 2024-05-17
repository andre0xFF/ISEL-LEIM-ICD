package pt.isel.icd.user.logic;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pt.isel.icd.database.Entity;

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

        if (username.trim().length() < 3 || username.trim().length() > 20) {
            throw new IllegalArgumentException("Username must have between 3 and 20 characters");
        }

        if (password.trim().length() < 8 || password.trim().length() > 20) {
            throw new IllegalArgumentException("Password must have between 8 and 20 characters");
        }
    }
}
