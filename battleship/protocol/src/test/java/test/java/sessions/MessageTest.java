package test.java.sessions;

import com.fasterxml.jackson.core.JsonProcessingException;
import controllers.commands.PingCommand;
import controllers.commands.ServerControllerCommand;
import org.junit.jupiter.api.Test;
import sessions.Message;
import sessions.Messenger;

class MessageTest {

    @Test
    void test() throws JsonProcessingException {
        String text;
        Message message;

        PingCommand ping = new PingCommand();
        text = Messenger.OBJECT_MAPPER.writeValueAsString(ping);

        System.out.println(ping.getClass().getSimpleName());
        System.out.println(ping.getClass().getName());

        message = new Message("ping", text);
        text = Messenger.OBJECT_MAPPER.writeValueAsString(message);

        System.out.println(text);

        // TODO: Fetch Message xsd.
        // TODO: Validate Message.
        message = Messenger.OBJECT_MAPPER.readValue(text, Message.class);

        String schema = message.getSchemaName();
        text = message.getContent();
        // TODO: Fetch Command xsd.
        // TODO: Validate Command.

        ServerControllerCommand command = Messenger.OBJECT_MAPPER.readValue(text, ServerControllerCommand.class);
    }
}