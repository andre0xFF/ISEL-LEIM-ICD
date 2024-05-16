package pt.isel.icd.user.management;

import pt.isel.icd.patterns.verticals.Service;
import pt.isel.icd.user.logic.Profile;
import pt.isel.icd.user.logic.User;

import java.util.HashMap;
import java.util.UUID;

public class UserServerService implements Service, Authenticator {
    private final UserServerRepository userServerRepository;
    private final HashMap<UUID, User> areAuthenticated = new HashMap<>();
    private final UserService userService = new UserService();

    public UserServerService(UserServerRepository existingUserServerRepository) {
        userServerRepository = existingUserServerRepository;
    }

    public void authenticate(UUID userIdentifier, User user) {
        if (!userService.validateUsername(user.username())) {
            throw new IllegalArgumentException("Invalid username");
        }

        if (!userService.validatePassword(user.password())) {
            throw new IllegalArgumentException("Invalid password");
        }

        User existingUser = userServerRepository.readUser(user.username());

        if (existingUser == null || !user.password().equals(existingUser.password())) {
            throw new IllegalArgumentException("Wrong username or password");
        }

        areAuthenticated.put(userIdentifier, user);
    }

    public void createUser(User user) {
        if (!userService.validateUsername(user.username())) {
            throw new IllegalArgumentException("Invalid username");
        }

        if (!userService.validatePassword(user.password())) {
            throw new IllegalArgumentException("Invalid password");
        }

        if (userServerRepository.readUser(user.username()) != null) {
            throw new IllegalArgumentException("User already exists");
        }

        userServerRepository.addUser(user);
    }

    public void deleteUser(User user) {
        if (userService.validateUsername(user.username())) {
            throw new IllegalArgumentException("Invalid username");
        }

        User existingUser = userServerRepository.readUser(user.username());

        if (existingUser == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        userServerRepository.removeUser(user);
    }

    @Override
    public boolean isAuthenticated(UUID userIdentifier) {
        return areAuthenticated.containsKey(userIdentifier);
    }

    public Profile readUserProfile(User user) {
        return userServerRepository.readProfile(user);
    }

    public void addProfile(Profile profile) {
        userServerRepository.addProfile(profile);
    }

    public void updateProfile(Profile profile) {
        userServerRepository.updateProfile(profile);
    }

    public void removeProfile(Profile profile) {
        userServerRepository.removeProfile(profile);
    }

    public User fetchUser(UUID userIdentifier) {
        return areAuthenticated.get(userIdentifier);
    }
}
