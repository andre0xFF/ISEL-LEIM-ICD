<%-- Pagina raiz: encaminha para o lobby (se autenticado) ou para o login. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String target = (session.getAttribute("username") != null)
        ? "lobby.jsp"
        : "login.jsp";
    response.sendRedirect(request.getContextPath() + "/" + target);
%>
