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

    public void authenticateUser(UUID connectionIdentifier, String username) {
        isAuthenticated = true;

        userClientRepository.username(username);
    }

    public String user() {
        return userClientRepository.username();
    }
}
