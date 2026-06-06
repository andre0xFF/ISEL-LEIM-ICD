<%-- Lobby. Guarda de sessao por scriptlet minimo; restante UI com EL. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dots and Boxes — Lobby</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<main class="card">
    <h1>Dots and Boxes</h1>
    <p>Signed in as <strong>${sessionScope.username}</strong></p>

    <div class="actions">
        <a class="button" href="<%= request.getContextPath() %>/game.jsp">Play a game</a>
        <a class="button secondary" href="<%= request.getContextPath() %>/profile.jsp">My profile</a>
        <a class="button secondary" href="<%= request.getContextPath() %>/honor.jsp">Honor board</a>
        <button id="logoutBtn" class="button secondary">Sign out</button>
    </div>

    <p class="hint">
        Click "Play a game" to join the matchmaking queue. You will be paired
        with the next available player — a browser player or a desktop (GUI) one.
    </p>
</main>

<script>
    const CONTEXT = "<%= request.getContextPath() %>";
    document.getElementById("logoutBtn").addEventListener("click", async () => {
        await fetch(CONTEXT + "/api/session", { method: "DELETE" });
        window.location = CONTEXT + "/login.jsp";
    });
</script>
</body>
</html>
