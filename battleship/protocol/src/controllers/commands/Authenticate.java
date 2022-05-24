package controllers.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import controllers.Controller;
import models.Account;

public class Authenticate implements CommandController {
    private final Account account;
    private Controller controller;

    @JsonCreator
    public Authenticate(@JsonSetter("account") Account account) {
        this.account = account;
    }

    @Override
    public void execute() {
        String username = account.getUsername();
        // TODO: Get account from model.

        if (account == null) {
            // TODO: Send no account.
        }

        String storedPassword = account.getPassword();
        String password = account.getPassword();

        if (storedPassword.equals(password)) {
            // TODO: Send authenticated.
        }

        // TODO: Send not authenticated.
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
