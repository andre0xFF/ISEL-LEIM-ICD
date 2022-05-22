package sessions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import commands.Sign;
import commands.SignResult;
import controllers.Controller;
import controllers.GameController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static ObjectMapper OBJECT_MAPPER = new XmlMapper();
    private ServerSocket serverSocket;
    private int port = 8888;
    private final Controller controller = new GameController();

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            Socket clientSocket;

            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            new Thread(() -> {
                try {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String input;
                    String output;

                    input = in.readLine();
                    // TODO: Validate schema.

                    Sign sign = Server.OBJECT_MAPPER.readValue(input, Sign.class);
                    SignResult signResult = sign.execute(controller);
                    output = Server.OBJECT_MAPPER.writeValueAsString(signResult);
                    out.println(output);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
