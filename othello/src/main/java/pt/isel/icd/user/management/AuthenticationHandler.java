package pt.isel.icd.user.management;

import pt.isel.icd.patterns.chainofresponsability.Handler;
import pt.isel.icd.patterns.command.Command;

class AuthenticationHandler implements Handler<Command<UserServerController>> {

    @Override
    public void setNext(Handler<Command<UserServerController>> next) {

    }

    @Override
    public void handle(Command<UserServerController> request) {

    }
}
