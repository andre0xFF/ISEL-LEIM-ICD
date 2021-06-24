package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.models.Function;

public class Server implements Runnable {
    public static final Integer DEFAULT_PORT = Client.DEFAULT_PORT;
    public static final ObjectMapper DEFAULT_SERIALIZER = Client.DEFAULT_SERIALIZER;
    private ServerSocket socket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public void start() throws IOException {
        this.socket = new ServerSocket(DEFAULT_PORT);
        Thread thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        while (true) {
            Socket new_socket;
            try {
                new_socket = socket.accept();

                ClientHandler clientHandler = new ClientHandler(new_socket);
                clientHandler.connect();

                this.clients.add(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    class ClientHandler implements Runnable {

        private final Socket socket;
        private PrintWriter output;
        private BufferedReader input;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void connect() throws IOException {
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            Thread thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            System.out.println("Welcome new client!");

            while (true) {
                try {
                    Function function = Client.listen(this.input);
                    System.out.println();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // this.invoke(function);
            }

        }
    }
    
}
