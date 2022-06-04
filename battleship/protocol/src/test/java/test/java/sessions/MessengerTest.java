package test.java.sessions;

import behavioral.command.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import controllers.commands.PingCommand;
import org.junit.jupiter.api.Test;
import sessions.Message;
import sessions.Messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class MessengerTest {

    private final ServerSocket server;
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final Messenger messenger;

    MessengerTest() throws IOException {
        server = new ServerSocket(Messenger.PORT);

        messenger = new Messenger();
        messenger.start();

        socket = server.accept();
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Test
    void readAsCommand() throws JsonProcessingException {
        String content;

        PingCommand expectedCommand = new PingCommand();
        content = Messenger.OBJECT_MAPPER.writeValueAsString(expectedCommand);

        Message expectedMessage = new Message(expectedCommand.getClass().getSimpleName(), content);
        content = Messenger.OBJECT_MAPPER.writeValueAsString(expectedMessage);

        out.println(content);

        PingCommand actualCommand = (PingCommand) messenger.readAsCommand();

        assertInstanceOf(Command.class, actualCommand);
    }

    @Test
    void writeCommand() throws IOException {
        String expectedString;

        PingCommand expectedCommand = new PingCommand();
        expectedString = Messenger.OBJECT_MAPPER.writeValueAsString(expectedCommand);

        Message expectedMessage = new Message(expectedCommand.getClass().getSimpleName(), expectedString);
        expectedString = Messenger.OBJECT_MAPPER.writeValueAsString(expectedMessage);

        messenger.write(expectedCommand);

        String actualString = in.readLine();

        assertEquals(expectedString, actualString);
    }
}
