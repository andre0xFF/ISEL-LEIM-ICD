package pt.isel.icd.user.management;

import pt.isel.icd.patterns.verticals.Service;

import java.util.HashMap;
import java.util.UUID;

public class UserServerService implements Service, Authenticator {
    private final UserServerRepository userServerRepository;
    private final HashMap<UUID, Boolean> areAuthenticated = new HashMap<>();
    private final UserService userService = new UserService();

    public UserServerService(UserServerRepository existingUserServerRepository) {
        userServerRepository = existingUserServerRepository;
    }

    public User authenticate(String username, String password) {
        if (!userService.validateUsername(username)) {
            throw new IllegalArgumentException("Invalid username");
        }

        if (!userService.validatePassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        User user = userServerRepository.findUserbyUsername(username);

        if (user == null || !password.equals(user.password())) {
            throw new IllegalArgumentException("Wrong username or password");
        }

        return user;
    }

    @Override
    public void authenticate(UUID connectionIdentifier, boolean isAuthenticated) {
        areAuthenticated.put(connectionIdentifier, isAuthenticated);
    }

    public User createUser(String username, String password) {
        if (!userService.validateUsername(username)) {
            throw new IllegalArgumentException("Invalid username");
        }

        if (!userService.validatePassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        User existingUser = userServerRepository.findUserbyUsername(username);

        if (existingUser != null) {
            throw new IllegalArgumentException("User already exists");
        }

        return userServerRepository.addUser(username, password);
    }

    public User deleteUser(String username) {
        if (userService.validateUsername(username)) {
            throw new IllegalArgumentException("Invalid username");
        }

        User existingUser = userServerRepository.findUserbyUsername(username);

        if (existingUser == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        return userServerRepository.removeUser(username);
    }

    @Override
    public boolean isAuthenticated(UUID connectionIdentifier) {
        return areAuthenticated.get(connectionIdentifier);
    }
}
