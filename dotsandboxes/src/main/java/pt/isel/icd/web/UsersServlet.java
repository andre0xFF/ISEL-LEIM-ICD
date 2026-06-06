package pt.isel.icd.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;
import org.w3c.dom.Document;
import pt.isel.icd.serialization.XmlHelper;

/**
 * Recurso de utilizadores (/api/users/*), em representacao XML:
 *  - POST /api/users        -> registo (CreateUser)
 *  - GET  /api/users/me      -> perfil proprio (ReadUserProfile)
 *  - PUT  /api/users/me      -> editar perfil proprio (UpdateUser; corpo XML)
 *
 * Cada operacao e reencaminhada ao servidor de jogo por um ServerProxy efemero
 * (Design A): o servidor e que escreve os ficheiros.
 */
@WebServlet("/api/users/*")
public class UsersServlet extends HttpServlet {

    /** POST /api/users — registo. 201 em sucesso, 409 se ja existir. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        if (!isUsersRoot(req)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String photo = req.getParameter("photo");

        if (username == null || password == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        boolean created = GameServerGateway.register(
            getServletContext(),
            username,
            password,
            fullName,
            photo
        );
        if (created) {
            resp.setStatus(HttpServletResponse.SC_CREATED); // 201
            resp.setHeader(
                "Location",
                req.getContextPath() + "/api/users/" + username
            );
        } else {
            resp.sendError(HttpServletResponse.SC_CONFLICT); // 409
        }
    }

    /** GET /api/users/me — perfil proprio. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        if (!"/me".equals(req.getPathInfo())) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String[] creds = credentials(req);
        if (creds == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String xml = GameServerGateway.readOwnProfileXml(
            getServletContext(),
            creds[0],
            creds[1]
        );
        writeXmlOrError(resp, xml);
    }

    /** PUT /api/users/me — editar perfil proprio (corpo XML). */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        if (!"/me".equals(req.getPathInfo())) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String[] creds = credentials(req);
        if (creds == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String body = req
            .getReader()
            .lines()
            .collect(Collectors.joining("\n"));
        String fullName;
        String nationality;
        int age;
        String photo;
        String preferredColor;
        try {
            Document doc = XmlHelper.parse(body); // <profile>...</profile>
            var profile = doc.getDocumentElement();
            fullName = text(XmlHelper.getChildText(profile, "fullName"));
            nationality = text(XmlHelper.getChildText(profile, "nationality"));
            String ageStr = XmlHelper.getChildText(profile, "age");
            age = ageStr != null && !ageStr.isBlank()
                ? Integer.parseInt(ageStr.trim())
                : 0;
            photo = text(XmlHelper.getChildText(profile, "photo"));
            preferredColor = text(
                XmlHelper.getChildText(profile, "preferredColor")
            );
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); // 400 (XML invalido)
            return;
        }

        String xml = GameServerGateway.updateOwnProfileXml(
            getServletContext(),
            creds[0],
            creds[1],
            fullName,
            nationality,
            age,
            photo,
            preferredColor
        );
        writeXmlOrError(resp, xml);
    }

    // === Auxiliares ===

    private boolean isUsersRoot(HttpServletRequest req) {
        String path = req.getPathInfo();
        return path == null || path.equals("/");
    }

    /** Devolve [username, password] da sessao, ou null se nao autenticado. */
    private String[] credentials(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) return null;
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        if (username == null || password == null) return null;
        return new String[] { username, password };
    }

    private void writeXmlOrError(HttpServletResponse resp, String xml)
        throws IOException {
        if (xml == null) {
            resp.sendError(HttpServletResponse.SC_BAD_GATEWAY); // servidor indisponivel
            return;
        }
        resp.setContentType("application/xml;charset=UTF-8");
        resp.getWriter().write(xml);
    }

    private static String text(String value) {
        return value != null ? value : "";
    }
}
