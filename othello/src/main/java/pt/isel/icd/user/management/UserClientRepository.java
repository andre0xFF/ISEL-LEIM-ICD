package pt.isel.icd.user.management;

public class UserClientRepository {
    private String username;

    public void username(String existingUsername) {
        username = existingUsername;
    }

    public String username() {
        return username;
    }
}
