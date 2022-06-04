package sessions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import controllers.commands.CommandController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Messenger {
    public static ObjectMapper OBJECT_MAPPER = new XmlMapper();
    public static final String IP = "127.0.0.1";
    public static final int PORT = 8888;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void start() {
        try {
            socket = new Socket(IP, PORT);
            System.out.println("Messenger: Connected to server!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        start(socket);
    }

    public void start(Socket socket) {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.socket = socket;
    }

    public void write(CommandController command) {
        try {
            String schemaName = command.getClass().getSimpleName();
            String content = OBJECT_MAPPER.writeValueAsString(command);
            Message message = new Message(schemaName, content);

            write(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(Message message) throws JsonProcessingException {
        String content = OBJECT_MAPPER.writeValueAsString(message);
        out.println(content);

        System.out.printf("Messenger: Wrote %s%n", message);
    }

    public CommandController readAsCommand() {
        CommandController command;

        try {
            Message message = read();
            String schemaName = message.getSchemaName();
            String content = message.getContent();

            // TODO: Get CommandController XSD.
            // TODO: Validate CommandController.

            command = OBJECT_MAPPER.readValue(content, CommandController.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return command;
    }

    private Message read() throws IOException {
        String schemaName = Message.class.getSimpleName();
        String content = in.readLine();

        System.out.printf("Messenger: Read %s%n", content);

        // TODO: Get Message XSD.
        // TODO: Validate Message.

        return OBJECT_MAPPER.readValue(content, Message.class);
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
