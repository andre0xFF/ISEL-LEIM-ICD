package views;

import controllers.AccountController;
import controllers.AccountControllerListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AccountView implements AccountControllerListener {

    private final AccountController accountController;

    public AccountView(AccountController accountController) {
        this.accountController = accountController;
    }

    public void authenticate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Username: ");
        String username = reader.readLine();

        System.out.print("Password: ");
        String password = reader.readLine();

        accountController.authenticate(username, password);
    }

    @Override
    public void onAuthenticationSucceeded() {
        System.out.println("Succeeded");
    }

    @Override
    public void onAuthenticationFailed() {
        System.out.println("Failed");
    }

    @Override
    public void onCredentialsSubmitted() {
        System.out.println("Submitted");
    }
}
