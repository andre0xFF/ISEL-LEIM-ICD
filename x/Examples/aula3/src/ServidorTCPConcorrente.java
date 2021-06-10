/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter; */
import java.net.ServerSocket;
import java.net.Socket;

import org.w3c.dom.Document;

public class ServidorTCPConcorrente {

    public final static int DEFAULT_PORT = 5025; 
    
    public static void main(String[] args) 
    {
        int port = DEFAULT_PORT; 

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.err.println("Erro no porto indicado");
            }
        }
        
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);

            Socket newSock    = null;

            for( ; ; ) {
                System.out.println("Servidor TCP concorrente aguarda ligacao no porto " + port + "..." );

                // Espera connect do cliente
                newSock = serverSocket.accept(); 

                Thread th = new HandleConnectionThread(newSock);
                th.start();
            }
        } 
        catch (Exception e) {
            System.err.println("Excepção no servidor: " + e);
        }
    } // end main

} // end ServidorTCP


class HandleConnectionThread extends Thread {

    private Socket connection;


    public HandleConnectionThread(Socket connection) {
        this.connection = connection;
    }
        
	public void run() { 
		try {
			// circuito virtual estabelecido: socket cliente na variavel newSock
			System.out.println("Thread " + this.getId() + ": "
					+ connection.getRemoteSocketAddress());
			
			// a tarefa s� termina quando o circuito virtual for quebrado
			for (;;) { // forever
				// le pedido processa e envia a resposta

				Document xmlRequest = XMLReadWrite
						.documentFromSocket(connection);

				Document xmlReply = new comando(xmlRequest).reply();

				XMLReadWrite.documentToSocket(xmlReply, connection);
			}
		} catch (Exception e) {
			System.err.println("Terminou a ligação " + connection + ": "
					+ e.getMessage());
		} finally {
			// garantir que o socket � fechado
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.err.println("Erro no fecho da ligação: "
						+ e.getMessage());
			}
		}
	} // end run

} // end HandleConnectionThread
