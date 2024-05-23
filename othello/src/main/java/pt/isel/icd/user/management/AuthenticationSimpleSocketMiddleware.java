package pt.isel.icd.user.management;

import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.communication.SimpleSocketMiddleware;

import java.util.logging.Logger;

public class AuthenticationSimpleSocketMiddleware implements SimpleSocketMiddleware {
    private static final Logger logger = Logger.getLogger(AuthenticationSimpleSocketMiddleware.class.getName());

    private final Authenticator authenticator;

    public AuthenticationSimpleSocketMiddleware(Authenticator existingAuthenticator) {
        authenticator = existingAuthenticator;
    }

    @Override
    public boolean handle(SimpleSocketCommand<?> command) {
        if (!command.requiresAuthentication()) {
            return true;
        }

        boolean isAuthenticated = authenticator.isAuthenticated(command.socketId());

        if (!isAuthenticated) {
            String message = String.format(
                "Command %s from socket %s is not authenticated",
                command.getClass().getSimpleName(),
                command.socketId()
            );

            logger.warning(message);
        }

        return isAuthenticated;
    }
}
