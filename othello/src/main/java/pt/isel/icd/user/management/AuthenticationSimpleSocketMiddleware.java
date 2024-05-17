package pt.isel.icd.user.management;

import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.communication.SimpleSocketMiddleware;

public class AuthenticationSimpleSocketMiddleware implements SimpleSocketMiddleware {

    private final Authenticator authenticator;

    public AuthenticationSimpleSocketMiddleware(Authenticator existingAuthenticator) {
        authenticator = existingAuthenticator;
    }

    @Override
    public boolean handle(SimpleSocketCommand<?> command) {
        if (!command.requiresAuthentication()) {
            return true;
        }

        return authenticator.isAuthenticated(command.socketId());
    }
}
