package sessions;

import behavioral.command.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import controllers.commands.CommandController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Communication {
    public static ObjectMapper OBJECT_MAPPER = new XmlMapper();
    public static final String IP = "127.0.0.1";
    public static final int PORT = 8888;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void start() {
        try {
            socket = new Socket(IP, PORT);
            System.out.println("Communication: Connected to server!");
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

    public CommandController readAsCommand() {
        String text = read();

        try {
            return OBJECT_MAPPER.readValue(text, CommandController.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(Command<?> command) {
        String text;

        try {
            text = OBJECT_MAPPER.writeValueAsString(command);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        write(text);
    }

    public void write(String text) {
        System.out.printf("Communication: Wrote %s%n", text);
        out.println(text);
    }

    public String read() {
        String text;

        try {
            text = in.readLine();
            System.out.printf("Communication: Read %s%n", text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return text;
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
