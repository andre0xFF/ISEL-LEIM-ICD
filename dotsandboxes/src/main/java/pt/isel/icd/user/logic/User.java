package pt.isel.icd.user.logic;

/**
 * Represents a user with credentials (username + password).
 */
public record User(String username, String password) {
    public User {
        if (
            username == null ||
            username.trim().length() < 3 ||
            username.trim().length() > 20
        ) {
            throw new IllegalArgumentException(
                "Username must have between 3 and 20 characters"
            );
        }
        if (
            password == null ||
            password.trim().length() < 8 ||
            password.trim().length() > 20
        ) {
            throw new IllegalArgumentException(
                "Password must have between 8 and 20 characters"
            );
        }
    }
}
