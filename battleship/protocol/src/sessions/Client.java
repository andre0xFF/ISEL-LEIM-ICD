package sessions;

import com.fasterxml.jackson.core.JsonProcessingException;
import commands.Command;
import commands.Sign;
import commands.SignResult;
import models.Account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private final String ip = "127.0.0.1";
    private final int port = 8888;
    private PrintWriter out;
    private BufferedReader in;

    public void receive() throws IOException {
        String text = in.readLine();
        System.out.println(text);
    }

    public void start() {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String input;

            Command command = new Sign(
                    true,
                    false,
                    new Account("andre", "123")
            );

            send(command);
            input = in.readLine();
            // TODO: Validate schema.
            SignResult signResult = Server.OBJECT_MAPPER.readValue(input, SignResult.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public void send(Command command) {
        String text;

        try {
            text = Server.OBJECT_MAPPER.writeValueAsString(command);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        out.println(text);
    }
}
