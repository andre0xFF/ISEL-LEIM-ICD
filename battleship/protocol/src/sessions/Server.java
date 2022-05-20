package sessions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import controllers.Controller;
import messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    public static ObjectMapper OBJECT_MAPPER = new XmlMapper();
    private ServerSocket serverSocket;
    private int port = 8888;
    private final HashMap<String, Controller<Message>> controllers = new HashMap<>();
    
    public Server(int port) {
        this.port = port;
    }
    
    public Server() {
        
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        
        while (true) {
            Socket clientSocket = serverSocket.accept();

            new Thread(() -> {
                try {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String input;

                    while ((input = in.readLine()) != null) {
                        Message message = OBJECT_MAPPER.readValue(input, Message.class);
                        controllers.get(message.getControllerName()).onMessage(message);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public void addController(Controller<Message> controller) {
        controllers.put(controller.getName(), controller);
    }
}
