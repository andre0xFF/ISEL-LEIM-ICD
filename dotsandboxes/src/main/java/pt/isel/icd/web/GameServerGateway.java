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
import pt.isel.icd.user.management.CreateUserCommand;
import pt.isel.icd.user.management.HonorBoardCommand;
import pt.isel.icd.user.management.ReadUserProfileCommand;
import pt.isel.icd.user.management.UpdateUserCommand;

/**
 * Operacoes sem estado contra o servidor de jogo, atraves de um ServerProxy
 * efemero (abre, [autentica,] envia, le a resposta, fecha). Reutiliza as classes
 * de comando do dominio para construir o XML, garantindo o mesmo protocolo do
 * TCP/GUI.
 *
 * Design A: o servidor de jogo continua a ser o unico escritor dos ficheiros;
 * a camada Web nunca toca nos ficheiros diretamente.
 */
public final class GameServerGateway {

    // Serializador para construir os comandos de saida (nao precisa de registo).
    private static final CommandSerializer SER = new CommandSerializer();
    // Timeout de leitura para as ligacoes CRUD efemeras.
    private static final int CRUD_TIMEOUT_MILLIS = 5000;

    private GameServerGateway() {}

    /** Autentica as credenciais junto do servidor de jogo. */
    public static boolean authenticate(
        ServletContext ctx,
        String username,
        String password
    ) {
        try (ServerProxy proxy = open(ctx)) {
            return authenticateOn(proxy, username, password);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Regista um novo utilizador (CreateUser). fullName/photo sao opcionais.
     *
     * @return true se o servidor confirmou a criacao (false se ja existia).
     */
    public static boolean register(
        ServletContext ctx,
        String username,
        String password,
        String fullName,
        String photo
    ) {
        String request = SER.serialize(
            new CreateUserCommand(new User(username, password), fullName, photo)
        );

        try (ServerProxy proxy = open(ctx)) {
            proxy.send(request);
            Element resp = readUntil(proxy, "CreateUserResponseCommand");

            return (
                resp != null &&
                "true".equals(XmlHelper.getChildText(resp, "created"))
            );
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Le o perfil do proprio utilizador autenticado. Devolve o XML completo da
     * resposta (ReadUserProfileResponseCommand) para a camada Web reencaminhar,
     * ou null em caso de falha/autenticacao invalida.
     */
    public static String readOwnProfileXml(
        ServletContext ctx,
        String username,
        String password
    ) {
        try (ServerProxy proxy = open(ctx)) {
            if (!authenticateOn(proxy, username, password)) {
                return null;
            }

            proxy.send(SER.serialize(new ReadUserProfileCommand()));
            return readLineUntil(proxy, "ReadUserProfileResponseCommand");
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Atualiza o perfil do utilizador autenticado e devolve o XML do perfil ja
     * atualizado (faz UpdateUser seguido de ReadUserProfile na mesma ligacao).
     */
    public static String updateOwnProfileXml(
        ServletContext ctx,
        String username,
        String password,
        String fullName,
        String nationality,
        int age,
        String photo,
        String preferredColor
    ) {
        try (ServerProxy proxy = open(ctx)) {
            if (!authenticateOn(proxy, username, password)) {
                return null;
            }

            proxy.send(
                SER.serialize(
                    new UpdateUserCommand(
                        fullName,
                        nationality,
                        age,
                        photo,
                        preferredColor
                    )
                )
            );

            // UpdateUser nao tem resposta propria: confirmamos relendo o perfil.
            proxy.send(SER.serialize(new ReadUserProfileCommand()));
            return readLineUntil(proxy, "ReadUserProfileResponseCommand");
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Obtem o quadro de honra (informacao publica; nao requer autenticacao).
     * Devolve o XML da resposta para a camada Web reencaminhar.
     */
    public static String honorBoardXml(ServletContext ctx) {
        try (ServerProxy proxy = open(ctx)) {
            proxy.send(SER.serialize(new HonorBoardCommand()));
            return readLineUntil(proxy, "HonorBoardResponseCommand");
        } catch (IOException e) {
            return null;
        }
    }

    // === Auxiliares ===

    private static ServerProxy open(ServletContext ctx) throws IOException {
        return new ServerProxy(
            ServerConfig.host(ctx),
            ServerConfig.port(ctx),
            CRUD_TIMEOUT_MILLIS
        );
    }

    private static boolean authenticateOn(
        ServerProxy proxy,
        String username,
        String password
    ) throws IOException {
        proxy.send(
            SER.serialize(
                new AuthenticateUserCommand(new User(username, password))
            )
        );

        Element resp = readUntil(proxy, "AuthenticateUserResponseCommand");

        return (
            resp != null &&
            "true".equals(XmlHelper.getChildText(resp, "authenticated"))
        );
    }

    /** Le ate encontrar o comando com o nome dado; devolve o seu Element. */
    private static Element readUntil(ServerProxy proxy, String name)
        throws IOException {
        for (int i = 0; i < 50; i++) {
            String line = proxy.readLine();

            if (line == null) {
                return null;
            }

            Element el = firstCommand(XmlHelper.parse(line));

            if (el != null && el.getTagName().equals(name)) {
                return el;
            }
        }

        return null;
    }

    /** Como readUntil, mas devolve a linha XML completa (envelope Command). */
    private static String readLineUntil(ServerProxy proxy, String name)
        throws IOException {
        for (int i = 0; i < 50; i++) {
            String line = proxy.readLine();

            if (line == null) {
                return null;
            }

            Element el = firstCommand(XmlHelper.parse(line));

            if (el != null && el.getTagName().equals(name)) {
                return line;
            }
        }

        return null;
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
