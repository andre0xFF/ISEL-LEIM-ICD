package application;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import domain.MessagePending;
import domain.MessageRead;
import domain.MessageReceived;
import domain.MessageSent;

@JsonRootName(value = "result")
@JsonSubTypes({
    @JsonSubTypes.Type(value = MessageRead.class, name="message_read"),
    @JsonSubTypes.Type(value = MessageReceived.class, name="message_received"),
    @JsonSubTypes.Type(value = MessageSent.class, name="message_sent"),
    @JsonSubTypes.Type(value = MessagePending.class, name="message_pending"),
})
public interface Result {
    
}
