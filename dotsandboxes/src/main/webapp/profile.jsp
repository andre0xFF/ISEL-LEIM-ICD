<%-- Edicao de perfil. GET/PUT /api/users/me com representacao XML. --%>
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
    <title>Dots and Boxes — Profile</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<main class="card">
    <h1>My profile</h1>
    <p id="msg" class="hint"></p>

    <div class="profile-photo">
        <img id="photoPreview" alt="photo" />
    </div>

    <form id="profileForm">
        <label>Full name
            <input type="text" id="fullName">
        </label>
        <label>Nationality (ISO alpha-2, e.g. PT)
            <input type="text" id="nationality" maxlength="2" style="text-transform:uppercase">
        </label>
        <label>Age
            <input type="number" id="age" min="0" max="150">
        </label>
        <label>Preferred background color
            <input type="color" id="preferredColor">
        </label>
        <label>Change photo
            <input type="file" id="photo" accept="image/*">
        </label>
        <button type="submit">Save</button>
    </form>

    <div class="stats">
        <span>Wins: <strong id="wins">0</strong></span>
        <span>Losses: <strong id="losses">0</strong></span>
        <span>Games: <strong id="totalGames">0</strong></span>
        <span>Avg time: <strong id="avgTime">—</strong></span>
    </div>

    <div class="actions">
        <a class="button secondary" href="<%= request.getContextPath() %>/lobby.jsp">Back to lobby</a>
    </div>
</main>

<script>
    const CONTEXT = "<%= request.getContextPath() %>";
    const form = document.getElementById("profileForm");
    const msg = document.getElementById("msg");
    let currentPhoto = "";   // Base64 da foto atual (preservada se nao mudar)

    function field(el, tag) {
        const n = el.getElementsByTagName(tag)[0];
        return n ? n.textContent : null;
    }

    function fillFromXml(xml) {
        const cmd = new DOMParser()
            .parseFromString(xml, "application/xml")
            .documentElement.firstElementChild;
        if (!cmd || field(cmd, "hasProfile") !== "true") return;
        document.getElementById("fullName").value = field(cmd, "fullName") || "";
        document.getElementById("nationality").value = field(cmd, "nationality") || "";
        document.getElementById("age").value = field(cmd, "age") || "0";
        document.getElementById("preferredColor").value =
            field(cmd, "preferredColor") || "#ffffff";
        document.getElementById("wins").textContent = field(cmd, "wins") || "0";
        document.getElementById("losses").textContent = field(cmd, "losses") || "0";
        const games = parseInt(field(cmd, "totalGames") || "0", 10);
        const millis = parseInt(field(cmd, "totalTimeMillis") || "0", 10);
        document.getElementById("totalGames").textContent = games;
        document.getElementById("avgTime").textContent =
            games > 0 ? (millis / games / 1000).toFixed(1) + "s" : "—";

        currentPhoto = field(cmd, "photo") || "";
        const img = document.getElementById("photoPreview");
        img.src = currentPhoto ? ("data:image/*;base64," + currentPhoto) : "";
        img.style.display = currentPhoto ? "block" : "none";
    }

    async function load() {
        const res = await fetch(CONTEXT + "/api/users/me");
        if (res.ok) fillFromXml(await res.text());
        else msg.textContent = "Could not load profile.";
    }

    function readPhotoBase64(input) {
        return new Promise((resolve) => {
            const file = input.files && input.files[0];
            if (!file) { resolve(null); return; }
            const reader = new FileReader();
            reader.onload = () => resolve(String(reader.result).split(",")[1] || "");
            reader.onerror = () => resolve(null);
            reader.readAsDataURL(file);
        });
    }

    function esc(s) {
        return (s || "").replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
    }

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const picked = await readPhotoBase64(document.getElementById("photo"));
        const photo = picked !== null ? picked : currentPhoto;
        const xml =
            "<profile>" +
            "<fullName>" + esc(document.getElementById("fullName").value) + "</fullName>" +
            "<nationality>" + esc(document.getElementById("nationality").value.toUpperCase()) + "</nationality>" +
            "<age>" + esc(document.getElementById("age").value || "0") + "</age>" +
            "<photo>" + photo + "</photo>" +
            "<preferredColor>" + esc(document.getElementById("preferredColor").value) + "</preferredColor>" +
            "</profile>";
        const res = await fetch(CONTEXT + "/api/users/me", {
            method: "PUT",
            headers: { "Content-Type": "application/xml" },
            body: xml
        });
        if (res.ok) {
            fillFromXml(await res.text());
            msg.textContent = "Profile saved.";
        } else {
            msg.textContent = "Could not save profile.";
        }
    });

    load();
</script>
</body>
</html>
