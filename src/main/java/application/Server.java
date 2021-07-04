package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.models.Function;
import application.models.User;
import functions.client.Authentication;
import patterns.behavioral.observer.Publisher;
import patterns.behavioral.observer.Subscriber;

public class Server implements Runnable {
    public static final Integer DEFAULT_PORT = Client.DEFAULT_PORT;
    public static final ObjectMapper DEFAULT_SERIALIZER = Client.DEFAULT_SERIALIZER;
    
    private final String databasePath = "src/main/resources/database";

    private ServerSocket socket;
    private HashMap<User, Socket> users = new HashMap<>();
    private Publisher publisher = new Publisher();

    public void start() throws IOException {
        this.socket = new ServerSocket(DEFAULT_PORT);
        Thread thread = new Thread(this);
        thread.start();
    } 

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = socket.accept();
                User user = authenticateUser(client);

                if (user == null) {
                    continue;
                }

                addUser(user, client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private User authenticateUser(Socket client) {
        System.out.println("Authenticating user.");

        Authentication authentication;

        try {
            authentication = (Authentication) listen(client);

            authentication.setReceiver(this);
            authentication.execute();
    
            if (!authentication.isAuthenticated()) {
                return null;
            }
    
            System.out.println("Authenticated.");
            User user = authentication.getUser();
    
            return user;
        } 
        catch (ClassCastException e) {
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    private void addUser(User user, Socket client) {
        this.users.put(user, client);

        Server server = this;

        Thread thread = new Thread(
            new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            Function<Server> function = listen(client);
                            function.setReceiver(server);
                            function.execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        );

        thread.start();
    }

    public Function<Server> listen(Socket socket) throws IOException {
        System.out.println("Listening to client.");

        BufferedReader input = new BufferedReader(
            new InputStreamReader(
                    socket.getInputStream()
            )
        );
        
        String text = input.readLine();
        Function<Server> function = Client.DEFAULT_SERIALIZER.readValue(text, Function.class);
        
        return function;
    }

    public void invoke(Function<Client> function, PrintWriter output) throws JsonProcessingException {
        String text = Client.DEFAULT_SERIALIZER.writeValueAsString(function);
        output.println(text);
    }

    public void invoke(Function<Client> function, PrintWriter output, Subscriber<?> subscriber) throws JsonProcessingException {
        this.invoke(function, output);
        this.publisher.subscribe(function, subscriber);
    }

    public User loadUser(String username) throws IOException {
        String path = String.format("%s/users/%s.xml", this.databasePath, username);
        File file = new File(path);
        User user = Client.DEFAULT_SERIALIZER.readValue(file, User.class);

        return user;
    }
}
