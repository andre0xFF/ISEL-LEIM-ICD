<%-- Quadro de honra. GET /api/honor-board e renderiza ranking ordenado. --%>
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
    <title>Dots and Boxes — Honor Board</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<main class="card">
    <h1>Honor board</h1>
    <p id="msg" class="hint"></p>

    <table class="leaderboard">
        <thead>
            <tr>
                <th>#</th><th></th><th>Player</th>
                <th>W</th><th>L</th><th>Avg</th>
            </tr>
        </thead>
        <tbody id="rows"></tbody>
    </table>

    <div class="actions">
        <a class="button secondary" href="<%= request.getContextPath() %>/lobby.jsp">Back to lobby</a>
    </div>
</main>

<script>
    const CONTEXT = "<%= request.getContextPath() %>";

    function field(el, tag) {
        const n = el.getElementsByTagName(tag)[0];
        return n ? n.textContent : "";
    }

    // Converte um codigo ISO alfa-2 na bandeira (regional indicator emoji).
    function flag(iso) {
        if (!iso || iso.length !== 2) return "";
        const base = 0x1F1E6;
        return String.fromCodePoint(
            base + iso.toUpperCase().charCodeAt(0) - 65,
            base + iso.toUpperCase().charCodeAt(1) - 65
        );
    }

    function avg(games, millis) {
        return games > 0 ? (millis / games / 1000).toFixed(1) + "s" : "—";
    }

    async function load() {
        const res = await fetch(CONTEXT + "/api/honor-board");
        if (!res.ok) { document.getElementById("msg").textContent =
            "Could not load the honor board."; return; }
        const cmd = new DOMParser()
            .parseFromString(await res.text(), "application/xml")
            .documentElement.firstElementChild;
        const entries = cmd ? cmd.getElementsByTagName("entry") : [];
        const rows = document.getElementById("rows");
        rows.innerHTML = "";
        if (entries.length === 0) {
            document.getElementById("msg").textContent = "No players yet.";
            return;
        }
        let rank = 1;
        for (const e of entries) {
            const photo = field(e, "photo");
            const fullName = field(e, "fullName");
            const username = field(e, "username");
            const games = parseInt(field(e, "totalGames") || "0", 10);
            const millis = parseInt(field(e, "totalTimeMillis") || "0", 10);
            const name = fullName && fullName.trim() ? fullName : username;

            const tr = document.createElement("tr");
            const img = photo
                ? '<img class="avatar" src="data:image/*;base64,' + photo + '">'
                : '<span class="avatar placeholder"></span>';
            tr.innerHTML =
                "<td>" + (rank++) + "</td>" +
                "<td>" + img + "</td>" +
                "<td>" + flag(field(e, "nationality")) + " " + escapeHtml(name) +
                    ' <span class="muted">(' + escapeHtml(username) + ")</span></td>" +
                "<td>" + field(e, "wins") + "</td>" +
                "<td>" + field(e, "losses") + "</td>" +
                "<td>" + avg(games, millis) + "</td>";
            rows.appendChild(tr);
        }
    }

    function escapeHtml(s) {
        return (s || "")
            .replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
    }

    load();
</script>
</body>
</html>
