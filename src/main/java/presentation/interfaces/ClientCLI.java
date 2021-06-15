//package presentation.interfaces;
//
//import application.Client;
//import application.calls.Login;
//import presentation.ClientInterface;
//import presentation.UserInterface;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//public class ClientCLI implements UserInterface, ClientInterface {
//
//    public ClientCLI() throws IOException {
//        Client client = new Client();
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Welcome.");
//
//        boolean response;
//
//        do {
//            System.out.print("Username: ");
//            String username = scanner.nextLine();
//
//            System.out.print("\n");
//            System.out.print("Password: ");
//            String password = scanner.nextLine();
//
//            Login login = new Login(username, password);
//        } while (true);
//
//        /*
//        * Client:
//        * <request method=get|create|update>
//        *   <login>
//        *       <username>andrefonseca</username
//        *       <password>1234fgh</password>
//        *   </login>
//        *   <profile>
//        *       <username>andrefonseca</username>
//        *   </profile>
//        * </request>
//        *
//        *
//        * Server:
//        * <response status=true|false>
//        *   <resource>
//        *   <profile>
//        *       <id>1</id>
//        *       <username>andrefonseca</username>
//        *       <type>Teacher</type>
//        *       <name>Andre Fonseca</name>
//        *       <age>30</age>
//        *   </profile>
//        * </response>
//        *
//        *
//        *
//        *
//        * Client:
//        * <request method=update>
//        *   <login>
//        *       <username>andrefonseca</username
//        *       <password>1234fgh</password>
//        *   </login>
//        *   <profile id=1>
//        *       <name>Miguel Fonseca</name>
//        *   </profile>
//        * </request>
//        *
//        * <response status=true/>
//        *
//        * */
//
//    }
//}
