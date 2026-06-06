package pt.isel.icd.web;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

/**
 * Liga o handshake do WebSocket a HttpSession HTTP em curso, para que o
 * GameSocketEndpoint possa ler o utilizador autenticado (e as suas credenciais)
 * guardado na sessao pelo login. Handshakes sem sessao autenticada serao
 * rejeitados no onOpen.
 */
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    public static final String HTTP_SESSION_KEY = "dab.httpSession";

    @Override
    public void modifyHandshake(
        ServerEndpointConfig config,
        HandshakeRequest request,
        HandshakeResponse response
    ) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        if (httpSession != null) {
            config.getUserProperties().put(HTTP_SESSION_KEY, httpSession);
        }
    }
}
