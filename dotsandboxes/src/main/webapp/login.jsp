<%-- Pagina de login. Usa a API REST /api/session via fetch (vanilla JS). --%>
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

    <p id="msg" class="error" hidden></p>

    <form id="loginForm">
        <label>Username
            <input type="text" name="username" required autofocus>
        </label>
        <label>Password
            <input type="password" name="password" required>
        </label>
        <button type="submit">Sign in</button>
    </form>

    <p class="hint">No account?
        <a href="<%= request.getContextPath() %>/register.jsp">Create one</a>.
    </p>
</main>

<script>
    const CONTEXT = "<%= request.getContextPath() %>";
    const form = document.getElementById("loginForm");
    const msg = document.getElementById("msg");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        msg.hidden = true;
        const body = new URLSearchParams({
            username: form.username.value,
            password: form.password.value
        });
        const res = await fetch(CONTEXT + "/api/session", {
            method: "POST", body
        });
        if (res.status === 201) {
            window.location = CONTEXT + "/lobby.jsp";
        } else {
            msg.hidden = false;
            msg.textContent = "Invalid username or password.";
        }
    });
</script>
</body>
</html>
