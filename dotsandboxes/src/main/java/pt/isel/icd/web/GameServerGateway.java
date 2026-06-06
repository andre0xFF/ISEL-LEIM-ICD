package pt.isel.icd.web;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pt.isel.icd.serialization.CommandSerializer;
import pt.isel.icd.serialization.XmlHelper;
import pt.isel.icd.user.logic.User;
import pt.isel.icd.user.management.AuthenticateUserCommand;

/**
 * Operacoes sem estado contra o servidor de jogo, atraves de um ServerProxy
 * efemero (abre, envia, le a resposta, fecha). Reutiliza as classes de comando
 * do dominio para construir o XML, garantindo o mesmo protocolo do TCP/GUI.
 *
 * Design A: o servidor de jogo continua a ser o unico escritor dos ficheiros;
 * a camada Web nunca toca nos ficheiros diretamente.
 */
public final class GameServerGateway {

    private GameServerGateway() {}

    /**
     * Autentica as credenciais junto do servidor de jogo.
     *
     * @return true se o servidor confirmou a autenticacao.
     */
    public static boolean authenticate(
        ServletContext ctx,
        String username,
        String password
    ) {
        CommandSerializer serializer = new CommandSerializer();
        String request = serializer.serialize(
            new AuthenticateUserCommand(new User(username, password))
        );
        try (
            ServerProxy proxy = new ServerProxy(
                ServerConfig.host(ctx),
                ServerConfig.port(ctx)
            )
        ) {
            proxy.send(request);
            String line = proxy.readLine();
            if (line == null) {
                return false;
            }
            Element command = firstCommand(XmlHelper.parse(line));
            return command != null &&
                "true".equals(XmlHelper.getChildText(command, "authenticated"));
        } catch (IOException e) {
            return false;
        }
    }

    /** Devolve o elemento de comando (filho de &lt;Command&gt;), ou null. */
    static Element firstCommand(Document doc) {
        NodeList children = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element element) {
                return element;
            }
        }
        return null;
    }
}
