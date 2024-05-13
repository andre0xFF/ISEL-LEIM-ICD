package pt.isel.icd.user.management;

import pt.isel.icd.patterns.verticals.Service;

import java.util.UUID;

public class UserClientService implements Service, Authenticator {

    private boolean isAuthenticated;

    @Override
    public boolean isAuthenticated(UUID connectionIdentifier) {
        return isAuthenticated;
    }

    @Override
    public void authenticate(UUID connectionIdentifier, boolean existingIsAuthenticated) {
        isAuthenticated = existingIsAuthenticated;
    }
}
