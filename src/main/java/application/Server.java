package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    public static final int DEFAULT_PORT = Client.DEFAULT_PORT;

    public Server() throws IOException {
        ServerSocket socket = new ServerSocket(DEFAULT_PORT);

        while (true) {
            Socket new_socket = socket.accept();

            Thread client = new Thread(() -> {
                try {
                    System.out.println("Welcome new client!");

                    PrintWriter output = new PrintWriter(new_socket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(new_socket.getInputStream()));

                    String text;
                    text = input.readLine();

                    // todo: validate xsd

                } catch (IOException ignored) {}
            });

            client.start();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}