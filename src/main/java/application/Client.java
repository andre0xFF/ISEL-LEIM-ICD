package application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    public static final String DEFAULT_IP = "localhost";
    public static final int DEFAULT_PORT = 9999;
    public static final ObjectMapper DEFAULT_SERIALIZER = new XmlMapper();

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private final int port = DEFAULT_PORT;
    private final String ip = DEFAULT_IP;
    private final ObjectMapper serializer = DEFAULT_SERIALIZER;

    public void connect() throws IOException {
        this.socket = new Socket(this.ip, this.port);
        this.output = new PrintWriter(this.socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public Result invoke(Function function) throws IOException {
        Message message = new Message(function);

        String text;

        text = this.serialize(message);
        this.output.println(text);

        text = this.input.readLine();
        function = this.deserialize(text);

        Result result = function.getResult();

        return result;
    }

    private String serialize(Message message) throws JsonProcessingException {
        String text = this.serializer.writeValueAsString(message);

        return text;
    }

    private Function deserialize(String text) throws JsonProcessingException {
        Message message = this.serializer.readValue(text, Message.class);
        Function function = message.getFunction();

        return function;
    }

    public static void main(String[] args) throws IOException {
        domain.Student andre = new domain.Student(
            39758,
            "André",
            "Fonseca",
            "afbfonseca@gmail.com",
            "andrefonseca",
            "12345asdf",
            "Portuguese",
            "Portuguese",
            "Male",
            "R. Carlos",
            "912062963",
            new ArrayList<String>() {
                {
                    add("Portuguese");
                    add("English");
                }
            }
        );

        domain.Student dani = new domain.Student();

        Channel channel = new Channel(
            new ArrayList<>() {{
                add(andre);
                add(dani);
            }},
            null
        );

        domain.SendMessage sendMessage = new domain.SendMessage(
                new domain.Message(
                    andre,
                    channel,
                    "Hello world!"
                )
        );


        Client client = new Client();
        Result result = client.invoke(sendMessage);

        System.out.println(result);
    }
}
