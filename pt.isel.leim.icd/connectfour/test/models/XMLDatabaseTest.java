package models;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

class UsersMock {
    @JacksonXmlProperty(localName = "user")
    private final HashMap<String, String> usersNpasswords = new HashMap<>() {{
        put("johndoe", "abc123");
        put("janesmith", "pass456");
        put("bobsmith", "qwerty");
    }};
}

public class XMLDatabaseTest {

    @Test
    public void shouldSerializeUsers() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File("res/data/users.xml"), new UsersMock());
    }

    @Test
    public void shouldDeserializeUsers() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        UsersMock usersMock = xmlMapper.readValue(new File("res/data/users.xml"), UsersMock.class);
        System.out.println(usersMock);
    }
}
