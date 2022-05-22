package controllers;

import models.AccountsModel;

public class GameController implements Controller {
    private AccountsModel accounts = new AccountsModel();

    @Override
    public AccountsModel getAccountsModel() {
        return accounts;
    }
}
