package controllers;

import messages.Message;

public class BattleshipController implements Controller<Message> {

    private static final String NAME = "BattleshipController";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onMessage(Message message) {

    }
}
