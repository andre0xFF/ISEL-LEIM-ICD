package controllers.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import controllers.Controller;
import models.Account;

public class Register implements CommandController {
    private final Account account;
    private Controller controller;

    @JsonCreator
    public Register(@JsonSetter("account") Account account) {
        this.account = account;
    }

    @Override
    public void execute() {
        boolean registered = true; // = this.controller.getAccountsModel().addAccount(account);

        if (registered) {
            // TODO: Send registered.
        }
        else {
            // TODO: Send not registered.
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
