package controllers;

import messages.Message;
import messages.SubmitCredentials;
import messages.SubmitPlayerDetails;

public class AccountController implements Controller<Message> {

    private static final String NAME = "AccountController";

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
}
