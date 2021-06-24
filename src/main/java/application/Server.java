package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.models.Function;

public class Server {
    public static final Integer DEFAULT_PORT = Client.DEFAULT_PORT;
    public static final ObjectMapper DEFAULT_SERIALIZER = Client.DEFAULT_SERIALIZER;

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(DEFAULT_PORT);

        while (true) {
            Socket new_socket = socket.accept();

            Thread client = new Thread(() -> {
                try {
                    System.out.println("Welcome new client!");

                    PrintWriter output = new PrintWriter(new_socket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(new_socket.getInputStream()));

                    String text = input.readLine();
                    Function newest = Server.DEFAULT_SERIALIZER.readValue(text, Function.class);
                    
                    // todo: validate xsd
                    // todo: execute function
                    // todo: send result
                } catch (IOException ignored) {
                }
            });

            client.start();
        }
    }
}
