package pt.isel.icd.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Recurso do quadro de honra (/api/honor-board): ranking de jogadores em XML.
 * Exige sessao (a pagina honor.jsp e parte da aplicacao autenticada), mas o
 * proprio ranking e informacao publica obtida via ServerProxy efemero.
 */
@WebServlet("/api/honor-board")
public class HonorBoardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String xml = GameServerGateway.honorBoardXml(getServletContext());
        if (xml == null) {
            resp.sendError(HttpServletResponse.SC_BAD_GATEWAY);
            return;
        }
        resp.setContentType("application/xml;charset=UTF-8");
        resp.getWriter().write(xml);
    }
}
