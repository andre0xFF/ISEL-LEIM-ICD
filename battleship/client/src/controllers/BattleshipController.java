package controllers;

import messages.Message;
import messages.StartGame;

public class BattleshipController implements Controller<Message> {

    private static final String NAME = "BattleshipController";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onMessage(Message message) {

    }

    public void onMessage(StartGame startGame) {

    }
}
