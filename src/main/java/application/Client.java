package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import application.models.Function;
import application.models.User;
import functions.client.Authentication;
import patterns.behavioral.observer.Publisher;
import patterns.behavioral.observer.Subscriber;

public class Client implements Runnable {
    public static final String DEFAULT_IP = "localhost";
    public static final Integer DEFAULT_PORT = 9999;
    public static final ObjectMapper DEFAULT_SERIALIZER = new XmlMapper();

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private Publisher publisher = new Publisher();

    public User connect(String username, String password) throws IOException {
        this.socket = new Socket(Client.DEFAULT_IP, Client.DEFAULT_PORT);
        this.output = new PrintWriter(this.socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

        // TODO: Authenticate
        Authentication authentication = new Authentication(username, password);
        this.invoke(authentication);

        Thread thread = new Thread(this);
        thread.start();

        // TODO
        return null;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Function<Client> function = listen(this.input);
                // TODO
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Function<Client> listen(BufferedReader input) throws IOException {
        String text = input.readLine();
        Function<Client> function = Client.DEFAULT_SERIALIZER.readValue(text, Function.class);

        return function;
    }

    // private void handle(Function function) {
    //     for (Invoker invoker : invokers) {
    //         try {
    //             invoker.onResult(function, function.getResult());
    //         } catch (ClassCastException e) {
    //             continue;
    //         }
    //     }

    // }

    // public void invoke(Function function) throws IOException {
    //     String text = Server.DEFAULT_SERIALIZER.writeValueAsString(function);
    //     this.output.println(text);

    //     for (Invoker invoker : invokers) {
    //         try {
    //             invoker.onInvoke(function);

    //         } catch (ClassCastException e) {
    //             continue;
    //         }
    //     }
    // }

    /**
     * Invokes a Function remotely and subscribes a callback subscriber.
     * @param function
     * @param subscriber
     * @throws IOException
     */
    public void invoke(Function<Server> function, Subscriber<?> subscriber) throws IOException {
        this.invoke(function);
        this.publisher.subscribe(function, subscriber);
    }

    /**
     * Invokes a Function remotely and doesn't subscribes a callback subscriber.
     * @param function
     * @throws IOException
     */
    public void invoke(Function<Server> function) throws IOException {
        // Message message = new Message(function);

        String text = Server.DEFAULT_SERIALIZER.writeValueAsString(function);
        this.output.println(text);
    }

}
