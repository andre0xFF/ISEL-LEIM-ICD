<%-- Pagina de entrada (login). Apenas EL e um scriptlet minimo. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dots and Boxes — Login</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<main class="card">
    <h1>Dots and Boxes</h1>
    <h2>Sign in</h2>

    <%-- Sem JSTL: scriptlet minimo para mostrar o erro de autenticacao. --%>
    <% if (request.getParameter("error") != null) { %>
        <p class="error">Invalid username or password.</p>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/login">
        <label>Username
            <input type="text" name="username" required autofocus>
        </label>
        <label>Password
            <input type="password" name="password" required>
        </label>
        <button type="submit">Sign in</button>
    </form>
</main>
</body>
</html>
