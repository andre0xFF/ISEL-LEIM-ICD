package pt.isel.icd.messaging;

import pt.isel.icd.messaging.messages.Message;
import pt.isel.icd.patterns.observer.Subscriber;

public interface ConnectionSubscriber extends Subscriber<Message> {

    Class<? extends Message>[] messageTypes();
}
