package controllers.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import controllers.ServerController;
import models.Account;

public class Authenticate implements ServerControllerCommand {

    private final Account account;
    private ServerController controller;

    @JsonCreator
    public Authenticate(@JsonSetter("account") Account account) {
        this.account = account;
    }

    @Override
    public void execute() {
        String username = account.getUsername();
        // Account storedAccount = controller.getAccount("username");
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
    public void setController(ServerController controller) {
        this.controller = controller;
    }

    public Account getAccount() {
        return account;
    }
}
