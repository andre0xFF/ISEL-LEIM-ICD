package application;

import java.util.ArrayList;

public class Channel {

    final String name;
    final ArrayList<User> users;

    public Channel(ArrayList<User> users, String name) {
        this.users = users;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
