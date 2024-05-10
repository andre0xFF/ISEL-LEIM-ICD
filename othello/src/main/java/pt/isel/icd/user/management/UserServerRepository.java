package pt.isel.icd.user.management;

import pt.isel.icd.patterns.verticals.Repository;

import java.util.HashMap;

public class UserServerRepository implements Repository {
    private final HashMap<String, User> users = new HashMap<>();


    public User findUserbyUsername(String username) {
        return users.get(username);
    }

    public User addUser(User user) {
        return users.put(user.username(), user);
    }

    public User removeUser(String username) {
        return users.remove(username);
    }
}
