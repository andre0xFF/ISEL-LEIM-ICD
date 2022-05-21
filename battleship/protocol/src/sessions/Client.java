package sessions;

import controllers.Controller;
import messages.Message;

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
    private Controller<Message> controller;

    public void setController(Controller<Message> controller) {
        this.controller = controller;
    }

    public String send(String message) {
        out.println(message);
        return "";
    }

    public Message listen() throws IOException {
        in.readLine();

        return null;
    }

    public void start() throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
