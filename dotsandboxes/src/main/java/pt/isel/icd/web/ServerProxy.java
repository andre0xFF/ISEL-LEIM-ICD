package pt.isel.icd.web;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Cliente TCP do servidor de jogo, do lado do Tomcat. E um relay quase
 * transparente do protocolo XML linha-a-linha existente: cada comando XML e uma
 * linha escrita/lida no socket. Para o servidor, um ServerProxy e
 * indistinguivel de um cliente JavaFX (resolve a lacuna L8).
 *
 * Usos:
 *  - persistente: uma ligacao por sessao de jogo no WebSocket (gameplay);
 *  - efemero: uma ligacao curta por pedido CRUD (REST).
 */
public class ServerProxy implements Closeable {

    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;

    public ServerProxy(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.reader = new BufferedReader(
            new InputStreamReader(socket.getInputStream())
        );
    }

    /** Envia um comando XML (uma linha) para o servidor de jogo. */
    public void send(String xmlLine) {
        writer.println(xmlLine);
    }

    /** Le a proxima linha (comando XML) vinda do servidor, ou null se fechou. */
    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException ignored) {
            // fecho best-effort
        }
    }
}
