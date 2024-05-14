package pt.isel.icd.user.management;

import pt.isel.icd.patterns.verticals.Service;

import java.util.UUID;

public class UserClientService implements Service, Authenticator {

    private final UserClientRepository userClientRepository;
    private boolean isAuthenticated;

    public UserClientService(UserClientRepository existingUserClientRepository) {
        userClientRepository = existingUserClientRepository;
    }

    @Override
    public boolean isAuthenticated(UUID connectionIdentifier) {
        return isAuthenticated;
    }

    public void authenticate(UUID connectionIdentifier, User user) {
        isAuthenticated = true;

        userClientRepository.addUser(user);
    }

    public User user() {
        return userClientRepository.user();
    }
}
