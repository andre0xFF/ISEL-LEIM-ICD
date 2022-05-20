package controllers;

import messages.Message;

public class AccountController implements Controller<Message> {

    private static final String NAME = "AccountController";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onMessage(Message message) {

    }
}
