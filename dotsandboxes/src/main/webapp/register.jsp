<%-- Registo de utilizador. POST /api/users via fetch (vanilla JS). --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dots and Boxes — Register</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<main class="card">
    <h1>Dots and Boxes</h1>
    <h2>Create an account</h2>

    <p id="msg" class="error" hidden></p>

    <form id="registerForm">
        <label>Username (3–20 chars)
            <input type="text" name="username" required minlength="3" maxlength="20">
        </label>
        <label>Full name
            <input type="text" name="fullName">
        </label>
        <label>Password (8–20 chars)
            <input type="password" name="password" required minlength="8" maxlength="20">
        </label>
        <label>Confirm password
            <input type="password" name="confirm" required minlength="8" maxlength="20">
        </label>
        <label>Photo (optional)
            <input type="file" name="photo" accept="image/*">
        </label>
        <button type="submit">Register</button>
    </form>

    <p class="hint">Already registered?
        <a href="<%= request.getContextPath() %>/login.jsp">Sign in</a>.
    </p>
</main>

<script>
    const CONTEXT = "<%= request.getContextPath() %>";
    const form = document.getElementById("registerForm");
    const msg = document.getElementById("msg");

    function fail(text) { msg.hidden = false; msg.textContent = text; }

    // Le um ficheiro como Base64 (sem o prefixo data:), ou "" se nenhum.
    function readPhotoBase64(input) {
        return new Promise((resolve) => {
            const file = input.files && input.files[0];
            if (!file) { resolve(""); return; }
            const reader = new FileReader();
            reader.onload = () => resolve(String(reader.result).split(",")[1] || "");
            reader.onerror = () => resolve("");
            reader.readAsDataURL(file);
        });
    }

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        msg.hidden = true;
        if (form.password.value !== form.confirm.value) {
            fail("Passwords do not match.");
            return;
        }
        const photo = await readPhotoBase64(form.photo);
        const body = new URLSearchParams({
            username: form.username.value.trim(),
            password: form.password.value,
            fullName: form.fullName.value.trim(),
            photo: photo
        });
        const res = await fetch(CONTEXT + "/api/users", { method: "POST", body });
        if (res.status === 201) {
            window.location = CONTEXT + "/login.jsp";
        } else if (res.status === 409) {
            fail("That username is already taken.");
        } else {
            fail("Registration failed. Please try again.");
        }
    });
</script>
</body>
</html>
