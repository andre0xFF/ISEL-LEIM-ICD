package pt.isel.icd.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Recurso de sessao (/api/session): login/logout/utilizador atual.
 *
 * A autenticacao usa HttpSession (API com estado, por desenho). As credenciais
 * ficam na sessao porque as passwords sao em texto simples (limitacao herdada)
 * e os proxies precisam delas para autenticar no servidor de jogo.
 */
@WebServlet("/api/session")
public class SessionServlet extends HttpServlet {

    /** POST /api/session — login. 201 em sucesso, 401 caso contrario. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean ok =
            username != null &&
            password != null &&
            GameServerGateway.authenticate(
                getServletContext(),
                username,
                password
            );

        if (ok) {
            HttpSession session = req.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            resp.setStatus(HttpServletResponse.SC_CREATED); // 201
            writeUserXml(resp, username);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 401
        }
    }

    /** GET /api/session — utilizador autenticado, ou 401. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        HttpSession session = req.getSession(false);

        String username =
            session == null ? null : (String) session.getAttribute("username");

        if (username == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        writeUserXml(resp, username);
    }

    /** DELETE /api/session — logout. 204. */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204
    }

    private void writeUserXml(HttpServletResponse resp, String username)
        throws IOException {
        resp.setContentType("application/xml;charset=UTF-8");
        resp.getWriter().write(
            "<user><username>" + escape(username) + "</username></user>"
        );
    }

    /** Escapa caracteres especiais de XML (mitiga XSS em nomes livres). */
    static String escape(String s) {
        if (s == null) {
            return "";
        }

        return s
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;");
    }
}
