import java.io.IOException;
import java.util.ArrayList;

import application.Client;
import application.Server;
import application.models.Function;
import application.models.Invoker;
import functions.FunctionTest1;
import functions.FunctionTest2;
import functions.SendMessage;
import functions.results.ResultTest1;
import functions.results.ResultTest2;
import resources.Channel;
import resources.Message;
import resources.Student;
import resources.models.User;

class InvokerFunctionTest1ResultTest1 implements Invoker<FunctionTest1, ResultTest1> {

    @Override
    public void onInvoke(FunctionTest1 function) {
        System.out.println("InvokerFunctionTest1ResultTest1");
        System.out.println(function);
        
    }

    @Override
    public void onResult(FunctionTest1 function, ResultTest1 result) {
        System.out.println("InvokerFunctionTest1ResultTest1");
        System.out.println(function);
        System.out.println(result);
        
    }

}

class InvokerFunctionTest2ResultTest2 implements Invoker<FunctionTest2, ResultTest2> {

    @Override
    public void onInvoke(FunctionTest2 function) {
        System.out.println("InvokerFunctionTest2ResultTest2");
        System.out.println(function);
        
    }

    @Override
    public void onResult(FunctionTest2 function, ResultTest2 result) {
        System.out.println("InvokerFunctionTest2ResultTest2");
        System.out.println(function);
        System.out.println(result);
        
    }

}

class App {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        Client client = new Client();

        server.start();
        client.connect();

        InvokerFunctionTest1ResultTest1 i1 = new InvokerFunctionTest1ResultTest1();
        InvokerFunctionTest2ResultTest2 i2 = new InvokerFunctionTest2ResultTest2();

        client.register(i1);
        client.register(i2);

        FunctionTest1 f1 = new FunctionTest1();
        FunctionTest2 f2 = new FunctionTest2();

        String t1 = Server.DEFAULT_SERIALIZER.writeValueAsString(f1);
        String t2 = Server.DEFAULT_SERIALIZER.writeValueAsString(f2);

        Function f3 = Server.DEFAULT_SERIALIZER.readValue(t1, Function.class);
        Function f4 = Server.DEFAULT_SERIALIZER.readValue(t2, Function.class);
        
        client.invoke(f1);
        client.invoke(f2);

        f1.execute();
        f2.execute();

        String t3 = Server.DEFAULT_SERIALIZER.writeValueAsString(f1);
        String t4 = Server.DEFAULT_SERIALIZER.writeValueAsString(f2);

        Function f5 = Server.DEFAULT_SERIALIZER.readValue(t3, Function.class);
        Function f6 = Server.DEFAULT_SERIALIZER.readValue(t4, Function.class);

        client.invoke(f1);
        client.invoke(f2);

        System.out.println();

        Student andre = new Student(
            "abscgs",
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

        Channel channel = new Channel(
            "qwerty",
            new ArrayList<User>() {
                {
                    add(andre);
                    add(andre);
                }
            },
            "Andre-Andre"
        );

        Message message = new Message("Hello World!");

        SendMessage sendMessage = new SendMessage(
            andre,
            channel,
            message
        );

        client.invoke(sendMessage);

    }
}
