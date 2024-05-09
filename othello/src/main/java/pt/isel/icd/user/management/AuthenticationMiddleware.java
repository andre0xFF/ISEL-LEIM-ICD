package pt.isel.icd.user.management;

import pt.isel.icd.communication.ConnectionCommand;
import pt.isel.icd.communication.Middleware;

class AuthenticationMiddleware implements Middleware {

    private final Authenticator authenticator;

    public AuthenticationMiddleware(Authenticator existingAuthenticator) {
        authenticator = existingAuthenticator;
    }

    @Override
    public boolean handle(ConnectionCommand<?> command) {
        if (!command.requiresAuthentication()) {
            return true;
        }

        return authenticator.isAuthenticated(command.connectionIdentifier());
    }
}
