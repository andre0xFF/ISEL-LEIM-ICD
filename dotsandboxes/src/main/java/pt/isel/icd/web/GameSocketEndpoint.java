package pt.isel.icd.web;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.isel.icd.serialization.CommandSerializer;
import pt.isel.icd.user.logic.User;
import pt.isel.icd.user.management.AuthenticateUserCommand;

/**
 * Ponte de gameplay: um WebSocket por utilizador Web autenticado. Liga cada
 * sessao do browser a uma ligacao TCP persistente ao servidor de jogo
 * (ServerProxy) e faz de relay quase transparente do protocolo XML.
 *
 * Um unico WebSocket transporta todos os jogos em simultaneo do utilizador,
 * multiplexados pelo gameId (o servidor cuida do encaminhamento por sessao).
 */
@ServerEndpoint(value = "/game", configurator = HttpSessionConfigurator.class)
public class GameSocketEndpoint {

    private static final Logger LOGGER = Logger.getLogger(
        GameSocketEndpoint.class.getName()
    );
    private static final String PROXY_KEY = "dab.proxy";

    private final CommandSerializer serializer = new CommandSerializer();

    @OnOpen
    public void onOpen(Session wsSession, EndpointConfig config) {
        HttpSession http = (HttpSession) config
            .getUserProperties()
            .get(HttpSessionConfigurator.HTTP_SESSION_KEY);

        // Rejeita handshakes sem sessao autenticada (requisito de seguranca).
        String username =
            http == null ? null : (String) http.getAttribute("username");

        String password =
            http == null ? null : (String) http.getAttribute("password");

        if (username == null || password == null) {
            closeQuietly(wsSession, "Nao autenticado");
            return;
        }

        try {
            ServerProxy proxy = new ServerProxy(
                ServerConfig.host(http.getServletContext()),
                ServerConfig.port(http.getServletContext())
            );
            wsSession.getUserProperties().put(PROXY_KEY, proxy);

            // Autentica a ligacao em nome do utilizador (credenciais da sessao).
            proxy.send(
                serializer.serialize(
                    new AuthenticateUserCommand(new User(username, password))
                )
            );

            // Thread leitora: servidor -> browser (encaminha cada linha XML).
            Thread reader = new Thread(() ->
                pumpServerToBrowser(proxy, wsSession)
            );

            reader.setDaemon(true);
            reader.start();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Falha ao ligar ao servidor de jogo", e);
            closeQuietly(wsSession, "Servidor de jogo indisponivel");
        }
    }

    @OnMessage
    public void onMessage(String xml, Session wsSession) {
        ServerProxy proxy = (ServerProxy) wsSession
            .getUserProperties()
            .get(PROXY_KEY);

        if (proxy != null) {
            // browser -> servidor: uma mensagem WebSocket = um comando XML.
            proxy.send(xml);
        }
    }

    @OnClose
    public void onClose(Session wsSession) {
        closeProxy(wsSession);
    }

    @OnError
    public void onError(Session wsSession, Throwable error) {
        LOGGER.log(Level.FINE, "Erro no WebSocket de jogo", error);
        closeProxy(wsSession);
    }

    /** Encaminha continuamente as linhas do servidor para o browser. */
    private void pumpServerToBrowser(ServerProxy proxy, Session wsSession) {
        try {
            String line;

            while ((line = proxy.readLine()) != null) {
                if (!wsSession.isOpen()) {
                    break;
                }

                wsSession.getBasicRemote().sendText(line);
            }
        } catch (IOException ignored) {
            // ligacao terminada
        } finally {
            closeQuietly(wsSession, "Ligacao ao servidor terminada");
        }
    }

    private void closeProxy(Session wsSession) {
        ServerProxy proxy = (ServerProxy) wsSession
            .getUserProperties()
            .remove(PROXY_KEY);

        if (proxy != null) {
            proxy.close();
        }
    }

    private void closeQuietly(Session wsSession, String reason) {
        try {
            if (wsSession.isOpen()) {
                wsSession.close(
                    new CloseReason(
                        CloseReason.CloseCodes.CANNOT_ACCEPT,
                        reason
                    )
                );
            }
        } catch (IOException ignored) {
            // best-effort
        }
    }
}
