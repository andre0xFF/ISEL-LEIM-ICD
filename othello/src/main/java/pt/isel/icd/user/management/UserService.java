package pt.isel.icd.user.management;

import pt.isel.icd.patterns.verticals.Service;

public class UserService implements Service {

    protected boolean validateUsername(String username) {
        return username != null
                && username.trim().length() >= 3
                && username.trim().length() <= 20;
    }

    protected boolean validatePassword(String password) {
        return password != null
                && password.trim().length() >= 8
                && password.trim().length() <= 20;
    }
}
