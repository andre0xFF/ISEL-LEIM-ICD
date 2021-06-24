package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import application.models.Function;
import application.models.Function.Result;
import application.models.Invoker;

public class Client {

    public static final String DEFAULT_IP = "localhost";
    public static final Integer DEFAULT_PORT = 9999;
    public static final ObjectMapper DEFAULT_SERIALIZER = new XmlMapper();

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private ArrayList<Invoker<? extends Function, ? extends Result<? extends Function>>> invokers = new ArrayList<>();

    // public void register(Invoker invoker) {
    //     this.invokers.add(invoker);
    // }

    public void register(Invoker<? extends Function, ? extends Result<? extends Function>> invoker) {
        this.invokers.add(invoker);
        
    }

    public void connect() throws IOException {
        this.socket = new Socket(Client.DEFAULT_IP, Client.DEFAULT_PORT);
        this.output = new PrintWriter(this.socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

        Thread listener = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        listen();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        listener.start();

    }

    private void listen() throws IOException {
        String text = this.input.readLine();
        Function function = Client.DEFAULT_SERIALIZER.readValue(text, Function.class);

        // todo
        // has result?
        // true: handle result
        // false: execute function

    }

    public void invoke(Function function) throws IOException {
        String text = Server.DEFAULT_SERIALIZER.writeValueAsString(function);
        // this.output.println(text);

        for (Invoker invoker : invokers) {
            try {
                invoker.onInvoke(function);

            } catch (ClassCastException e) {
                continue;
            }

        }

    }
}
