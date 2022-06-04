package models;

import java.util.HashMap;

public class AccountsModel {

    private HashMap<String, Account> accounts = new HashMap<>() {{
        put("andre", new Account("andre", "1234"));
    }};

    public Account getAccount(String username) {
        return accounts.getOrDefault(username, null);
    }

    public boolean contains(Account account) {
        return accounts.containsValue(account);
    }

    public boolean addAccount(Account account) {
        if (contains(account)) {
            return false;
        }

        accounts.put(account.getUsername(), account);

        // TODO: Save in database.

        return true;
    }
}
