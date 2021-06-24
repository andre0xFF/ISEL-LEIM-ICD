package resources;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import resources.models.User;

public class Channel {

    private String id;
    private String name;
    private ArrayList<User> users;

    @JsonCreator
    public Channel(
        @JsonProperty(value = "id") String id, 
        @JsonProperty(value = "users") ArrayList<User> users, 
        @JsonProperty(value = "name") String name
    ) {
        this.id = id;
        this.users = users;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @JacksonXmlElementWrapper(localName = "users")
    @JacksonXmlProperty(localName = "user")
    public ArrayList<User> getUsers() {
        return this.users;
    }

    public String getId() {
        return this.id;
    }
}
