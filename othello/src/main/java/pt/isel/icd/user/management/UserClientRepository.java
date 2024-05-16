package pt.isel.icd.user.management;

import pt.isel.icd.user.logic.User;

public class UserClientRepository {
    private User user;

    public void addUser(User existingUser) {
        user = existingUser;
    }

    public User user() {
        return user;
    }
}
