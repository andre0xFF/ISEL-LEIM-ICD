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

public class Client implements Runnable {

    public static final String DEFAULT_IP = "localhost";
    public static final Integer DEFAULT_PORT = 9999;
    public static final ObjectMapper DEFAULT_SERIALIZER = new XmlMapper();

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private ArrayList<Invoker<? extends Function, ? extends Result<? extends Function>>> invokers = new ArrayList<>();

    public void register(Invoker<? extends Function, ? extends Result<? extends Function>> invoker) {
        this.invokers.add(invoker);
        
    }

    public void connect() throws IOException {
        this.socket = new Socket(Client.DEFAULT_IP, Client.DEFAULT_PORT);
        this.output = new PrintWriter(this.socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

        Thread thread = new Thread(this);
        thread.start();

    }
    
    @Override
    public void run() {
        try {
            while (true) {
                Function function = listen(this.input);

                if (function.getResult() != null) {
                    this.handle(function);
                }
                else {
                    this.invoke(function);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Function listen(BufferedReader input) throws IOException {
        String text = input.readLine();
        Function function = Client.DEFAULT_SERIALIZER.readValue(text, Function.class);

        return function;

    }

    private void handle(Function function) {
        for (Invoker invoker : invokers) {
            try {
                invoker.onResult(function, function.getResult());
            } catch (ClassCastException e) {
                continue;
            }
        }

    }

    public void invoke(Function function) throws IOException {
        String text = Server.DEFAULT_SERIALIZER.writeValueAsString(function);
        this.output.println(text);

        for (Invoker invoker : invokers) {
            try {
                invoker.onInvoke(function);

            } catch (ClassCastException e) {
                continue;
            }

        }

    }

}
