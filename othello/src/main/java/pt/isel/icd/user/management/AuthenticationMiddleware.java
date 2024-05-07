package pt.isel.icd.user.management;

import pt.isel.icd.communication.Middleware;
import pt.isel.icd.patterns.command.Command;

class AuthenticationMiddleware implements Middleware<UserServerController> {

    @Override
    public void handle(UserServerController receiver, Command<?> command) {

    }
}
