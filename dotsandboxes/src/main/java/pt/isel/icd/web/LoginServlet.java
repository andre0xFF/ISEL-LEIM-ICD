package pt.isel.icd.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Login minimo (F2): autentica as credenciais junto do servidor de jogo e, em
 * caso de sucesso, guarda utilizador e credenciais na HttpSession. As
 * credenciais ficam na sessao porque as passwords sao em texto simples
 * (limitacao herdada) e os proxies precisam delas para autenticar no servidor.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String ctx = req.getContextPath();

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
            resp.sendRedirect(ctx + "/lobby.jsp");
        } else {
            resp.sendRedirect(ctx + "/login.jsp?error=1");
        }
    }
}
