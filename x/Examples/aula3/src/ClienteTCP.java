
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author PFilipe
 *
 */
public class ClienteTCP {

	public final static String DEFAULT_HOSTNAME = "localhost";

	public final static int DEFAULT_PORT = 5025;

	/**
	 * @param sock
	 */
	public static void menu(Socket sock) {
		char op;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println();
			System.out.println();
			System.out.println("*** Menu Cliente ***");
			System.out
					.println("C Consultar a lista de (titulos) de poemas partilhada.");
			System.out
					.println("O Obter um poema que inclua um dado conjunto de palavras.");
			System.out.println("0 - Terminar!");
			String str = sc.nextLine();
			if (str != null && str.length() > 0)
				op = str.charAt(0);
			else
				op = ' ';
			switch (op) {
			case 'C':
			case 'c':
				System.out
						.println("Consultar a lista de (titulos) de poemas partilhada.");
				Consultar(sock);
				break;
			case 'O':
			case 'o':
				System.out
						.println("Obter um poema que inclua um dado conjunto de palavras");
				System.out.println("Indique as palavras: ");
				ArrayList<String> palList = new ArrayList<String>();
				String pv = "";
				do {
					pv = sc.nextLine();
					if (pv.compareTo("") == 0)
						break;
					palList.add(pv);
					System.out
							.println("Indique outra palavra ou <enter> para terminar:");
				} while (true);
				String[] palArray = palList.toArray(new String[palList.size()]);
				Obter(sock, palArray);
				break;
			case '0':
				break;
			default:
				System.out.println("Opção invalida, esolha uma opção do menu.");
			}
		} while (op != '0');
		sc.close();
		System.out.println("Terminou a execução.");
		System.exit(0);
	}

	/**
	 * Envia e recebe a o comando associado ao servico Consultar
	 * @param sock
	 */
	private static void Consultar(Socket sock) {
		comando cmd = new comando();
		Document request = cmd.requestConsultar();

		// envia pedido
		XMLReadWrite.documentToSocket(request, sock);
		// obt�m resposta
		Document reply = XMLReadWrite.documentFromSocket(sock);

		System.out.println("\nLista de titulos:");
		NodeList titulos = reply.getElementsByTagName("titulo");
		for (int i = 0; i < titulos.getLength(); i++) {
			Node item = titulos.item(i);
			System.out.println(item.getTextContent());
		}
	}

	/**
	 * Envia e recebe a o comando associado ao servico Obter
	 * @param sock
	 */
	private static void Obter(Socket sock, String[] palavras) {

		comando cmd = new comando();
		Document request = cmd.requestObter(palavras);

		// envia pedido
		XMLReadWrite.documentToSocket(request, sock);
		// obt�m resposta
		Document reply = XMLReadWrite.documentFromSocket(sock);

		NodeList L = reply.getElementsByTagName("poema");
		if (L.getLength() == 0)
			System.out
					.println("\nNão existe nenhum poema com todas as palavras indicadas!");
		else {
			System.out.println("\nPoema:");
			Element pm = (Element) reply.getElementsByTagName("poema").item(0);
			Poema resposta = new Poema(pm);
			resposta.apresenta();
		}
	}

	public static void main(String[] args) {

		String host = DEFAULT_HOSTNAME; // M�quina onde reside a aplica��o
										// servidora
		int port = DEFAULT_PORT; // Porto da aplica��o servidora

		if (args.length > 0) {
			host = args[0];
		}

		if (args.length > 1) {
			try {
				port = Integer.parseInt(args[1]);
				if (port < 1 || port > 65535)
					port = DEFAULT_PORT;
			} catch (NumberFormatException e) {
				System.err.println("Erro no porto indicado");
			}
		}

		System.out.println("-> " + host + ":" + port);

		Socket socket = null;

		try {
			socket = new Socket(host, port);
			menu(socket);
		} catch (Exception e) {
			System.err.println("Erro na ligação " + e.getMessage());
		} finally {
			// No fim de tudo, fechar os streams e o socket
			try {
				// if (os != null) os.close();
				// if (is != null) is.close();
				if (socket != null)
					socket.close();
			} catch (Exception e) {
				// if an I/O error occurs when closing this socket
				System.err
						.println("Erro no fecho da ligação:" + e.getMessage());
			}
		} // end finally

	} // end main

} // end ClienteTCP