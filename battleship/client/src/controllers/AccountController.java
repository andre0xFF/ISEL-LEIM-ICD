package controllers;

import messages.Message;
import messages.SubmitCredentials;
import messages.SubmitPlayerDetails;
import sessions.Client;

public class AccountController implements Controller<Message> {

    private static final String NAME = "AccountController";
    private AccountControllerListener listener;
    private final Client client;

    public AccountController(Client client) {
        this.client = client;
    }

    public void setListener(AccountControllerListener listener) {
        this.listener = listener;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onMessage(Message message) {

    }

    public void onMessage(SubmitCredentials submitCredentials) {

    }

    public void onMessage(SubmitPlayerDetails submitPlayerDetails) {

    }

    public void authenticate(String username, String password) {
        // Submit.
        listener.onCredentialsSubmitted();
    }
}

