package pt.isel.icd.web;

import jakarta.servlet.ServletContext;

/**
 * Resolve o host/porta do servidor de jogo TCP que a camada Web contacta.
 * Ordem de resolucao: propriedade de sistema (ex.: CATALINA_OPTS no Docker) ->
 * context-param do web.xml -> valor por omissao (localhost:8000).
 */
public final class ServerConfig {

    public static final String HOST_KEY = "dab.server.host";
    public static final String PORT_KEY = "dab.server.port";
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8000;

    private ServerConfig() {}

    public static String host(ServletContext ctx) {
        String value = System.getProperty(HOST_KEY);
        if (value == null && ctx != null) {
            value = ctx.getInitParameter(HOST_KEY);
        }
        return value != null ? value : DEFAULT_HOST;
    }

    public static int port(ServletContext ctx) {
        String value = System.getProperty(PORT_KEY);
        if (value == null && ctx != null) {
            value = ctx.getInitParameter(PORT_KEY);
        }
        if (value == null) {
            return DEFAULT_PORT;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return DEFAULT_PORT;
        }
    }
}
