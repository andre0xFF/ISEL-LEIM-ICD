package resources;

import java.util.ArrayList;

public class Channel {

    private String id;
    private String name;
    private ArrayList<User> users;

    public Channel() {
    }

    public Channel(String id, ArrayList<User> users, String name) {
        this.id = id;
        this.users = users;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public String getId() {
        return this.id;
    }
}
