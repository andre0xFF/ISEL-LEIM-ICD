package commands;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import models.Account;
import controllers.Controller;
import models.AccountsModel;

public class Sign implements Command {

    private final boolean isAuthentication;
    private final boolean isRegistration;
    private final Account account;

    @JsonCreator
    public Sign(
            @JsonSetter("isAuthentication") boolean isAuthentication,
            @JsonSetter("isRegistration") boolean isRegistration,
            @JsonSetter("account") Account account
    ) {
        this.isAuthentication = isAuthentication;
        this.isRegistration = isRegistration;
        this.account = account;
    }

    public boolean getIsAuthentication() {
        return isAuthentication;
    }

    public boolean getIsRegistration() {
        return isRegistration;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public SignResult execute(Controller controller) {
        return isAuthentication
                ? authenticate(controller.getAccountsModel())
                : register(controller.getAccountsModel());
    }

    private SignResult authenticate(AccountsModel accounts) {
        String username = account.getUsername();
        Account account = accounts.getAccount(username);

        if (account == null) {
            return new SignResult(false, false);
        }

        String storedPassword = account.getPassword();
        String password = account.getPassword();

        if (storedPassword.equals(password)) {
            return new SignResult(true, false);
        }

        return new SignResult(false, false);
    }

    private SignResult register(AccountsModel accounts) {
        boolean registered = accounts.addAccount(account);

        return registered ?
                new SignResult(false, true)
                : new SignResult(false, false);
    }
}

