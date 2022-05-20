package controllers;

import messages.Message;

public interface Controller<Message> {

    String getName();
    void onMessage(Message message);
}
