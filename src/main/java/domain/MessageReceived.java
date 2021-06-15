package domain;

import application.Result;

import java.time.LocalDateTime;

public class MessageReceived implements Result, MessageState {

    @Override
    public LocalDateTime getWhen() {
        return null;
    }
}
