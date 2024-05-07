package pt.isel.icd.user.management;

import pt.isel.icd.communication.Middleware;
import pt.isel.icd.patterns.command.Command;

class AuthenticationMiddleware implements Middleware {

    private final Authenticator authenticator;

    public AuthenticationMiddleware(Authenticator existingAuthenticator) {
        authenticator = existingAuthenticator;
    }

    @Override
    public void handle(Command<?> request) {

    }
}
