import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.Client;
import application.Server;
import application.models.User;
import resources.Student;

class App {
    public static void main(String[] args) throws IOException {
        Student andre = new Student(
            39758,
            "André",
            "Fonseca",
            "afbfonseca@gmail.com",
            "andrefonseca",
            "12345",
            "Portuguese",
            "Portuguese",
            "Male",
            "R. Carlos",
            "912062963",
            new ArrayList<String>() {
                {
                    add("Portuguese");
                    add("English");
                }
            }
        );

        File file = new File("src/main/resources/database/users/andrefonseca.xml");
        Client.DEFAULT_SERIALIZER.writeValue(file, andre);

        Server server = new Server();
        Client client = new Client();

        server.start();
        client.connect("andrefonseca", "12345");

        // InvokerFunctionTest1ResultTest1 i1 = new InvokerFunctionTest1ResultTest1();
        // InvokerFunctionTest2ResultTest2 i2 = new InvokerFunctionTest2ResultTest2();

        // client.register(i1);
        // client.register(i2);

        // FunctionTest1 f1 = new FunctionTest1();
        // FunctionTest2 f2 = new FunctionTest2();

        // String t1 = Server.DEFAULT_SERIALIZER.writeValueAsString(f1);
        // String t2 = Server.DEFAULT_SERIALIZER.writeValueAsString(f2);

        // Function f3 = Server.DEFAULT_SERIALIZER.readValue(t1, Function.class);
        // Function f4 = Server.DEFAULT_SERIALIZER.readValue(t2, Function.class);
        
        // client.invoke(f1);
        // client.invoke(f2);

        // f1.execute();
        // f2.execute();

        // String t3 = Server.DEFAULT_SERIALIZER.writeValueAsString(f1);
        // String t4 = Server.DEFAULT_SERIALIZER.writeValueAsString(f2);

        // Function f5 = Server.DEFAULT_SERIALIZER.readValue(t3, Function.class);
        // Function f6 = Server.DEFAULT_SERIALIZER.readValue(t4, Function.class);

        // client.invoke(f1);
        // client.invoke(f2);

        // System.out.println();

        // Channel channel = new Channel(
        //     "qwerty",
        //     new ArrayList<User>() {
        //         {
        //             add(andre);
        //             add(andre);
        //         }
        //     },
        //     "Andre-Andre"
        // );

        // Message message = new Message("Hello World!");

        // SendMessage sendMessage = new SendMessage(
        //     andre,
        //     channel,
        //     message
        // );

        // client.invoke(sendMessage);

    }
}
