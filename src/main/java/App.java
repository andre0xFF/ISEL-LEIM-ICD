import java.io.IOException;

import application.Client;
import application.Server;
import application.models.Function;
import application.models.Function.Result;
import functions.FunctionTest1;
import functions.FunctionTest2;
import functions.results.ResultTest1;
import functions.results.ResultTest2;
import application.models.Invoker;

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

        // server.start();
        // client.connect();

        InvokerFunctionTest1ResultTest1 i1 = new InvokerFunctionTest1ResultTest1();
        InvokerFunctionTest2ResultTest2 i2 = new InvokerFunctionTest2ResultTest2();

        client.register(i1);
        client.register(i2);

        FunctionTest1 f1 = new FunctionTest1();
        FunctionTest2 f2 = new FunctionTest2();

        client.invoke(f1);
        client.invoke(f2);

        f1.execute();
        f2.execute();

        System.out.println();

    }
}
