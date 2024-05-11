package pt.isel.icd.user.management;

import pt.isel.icd.patterns.verticals.Service;

public class UserServerService implements Service {
    private final UserServerRepository userServerRepository;

    public UserServerService(UserServerRepository existingUserServerRepository) {
        userServerRepository = existingUserServerRepository;
    }

    public User authenticate(String username, String password) {
        if (!validateUsername(username)) {
            throw new IllegalArgumentException("Invalid username");
        }

        if (!validatePassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        User user = userServerRepository.findUserbyUsername(username);

        if (user == null || !password.equals(user.password())) {
            throw new IllegalArgumentException("Wrong username or password");
        }

        return user;
    }

    public User createUser(String username, String password) {
        if (!validateUsername(username)) {
            throw new IllegalArgumentException("Invalid username");
        }

        if (!validatePassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        User existingUser = userServerRepository.findUserbyUsername(username);

        if (existingUser != null) {
            throw new IllegalArgumentException("User already exists");
        }

        return userServerRepository.addUser(username, password);
    }

    public User deleteUser(String username) {
        if (validateUsername(username)) {
            throw new IllegalArgumentException("Invalid username");
        }

        User existingUser = userServerRepository.findUserbyUsername(username);

        if (existingUser == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        return userServerRepository.removeUser(username);
    }

    private boolean validateUsername(String username) {
        return username != null
                && username.trim().length() >= 3
                && username.trim().length() <= 20;
    }

    private boolean validatePassword(String password) {
        return password != null
                && password.trim().length() >= 8
                && password.trim().length() <= 20;
    }
}
