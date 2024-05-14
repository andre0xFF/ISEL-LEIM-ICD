package pt.isel.icd.user.management;

public class UserClientRepository {
    private User user;

    public void addUser(User existingUser) {
        user = existingUser;
    }

    public User user() {
        return user;
    }
}
