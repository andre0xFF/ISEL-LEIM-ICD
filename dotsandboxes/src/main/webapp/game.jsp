<%-- Ecra de jogo. Abre um WebSocket para /game e fala o protocolo XML. --%>
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
    <title>Dots and Boxes — Game</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<main class="card game">
    <div class="topbar">
        <span id="scoreA" class="score a">A: 0</span>
        <span id="turn" class="turn"></span>
        <span id="scoreB" class="score b">B: 0</span>
    </div>
    <p id="status" class="status">Connecting…</p>
    <p id="countdown" class="countdown"></p>
    <div id="board" class="board"></div>
    <div class="actions">
        <button id="leaveBtn" class="button secondary">Leave game</button>
    </div>
</main>

<script>
    // Caminho de contexto da aplicacao (vazio quando implantada como ROOT).
    const CONTEXT = "<%= request.getContextPath() %>";

    // === Estado do jogo (lado do browser) ===
    let ws = null;
    let gameId = null;
    let myMarker = null;
    let rows = 4, cols = 4;
    let myTurn = false;
    const TURN_SECONDS = 30;          // espelha o temporizador autoritativo do servidor
    let remaining = 0;
    let countdownTimer = null;
    const edges = new Set();              // "H_r_c" / "V_r_c"
    const boxOwner = {};                  // "br_bc" -> "A"/"B"
    const score = { A: 0, B: 0 };
    const lineButtons = [];               // botoes de linha ainda jogaveis

    // === Helpers XML (DOMParser / XMLSerializer, nativos) ===
    function buildCommand(name, fields) {
        const doc = document.implementation.createDocument("", "", null);
        const root = doc.createElement("Command");
        const cmd = doc.createElement(name);
        root.appendChild(cmd);
        if (fields) {
            for (const key of Object.keys(fields)) {
                const el = doc.createElement(key);
                el.textContent = String(fields[key]);
                cmd.appendChild(el);
            }
        }
        doc.appendChild(root);
        return new XMLSerializer().serializeToString(doc);
    }

    function parseCommand(xml) {
        const doc = new DOMParser().parseFromString(xml, "application/xml");
        return doc.documentElement.firstElementChild; // elemento do comando
    }

    function field(el, tag) {
        const n = el.getElementsByTagName(tag)[0];
        return n ? n.textContent : null;
    }

    function send(xml) {
        if (ws && ws.readyState === WebSocket.OPEN) ws.send(xml);
    }

    // === Ligacao WebSocket ===
    function connect() {
        const scheme = location.protocol === "https:" ? "wss:" : "ws:";
        ws = new WebSocket(scheme + "//" + location.host + CONTEXT + "/game");
        ws.onopen = () => {
            setStatus("Waiting for opponent…");
            send(buildCommand("JoinGameCommand", {}));
        };
        ws.onmessage = (ev) => handle(parseCommand(ev.data));
        ws.onclose = () => setStatus("Disconnected.");
        ws.onerror = () => setStatus("Connection error.");
    }

    function handle(cmd) {
        if (!cmd) return;
        switch (cmd.tagName) {
            case "JoinGameResponseCommand": return onJoined(cmd);
            case "PlaceLineResponseCommand": return onLinePlaced(cmd);
            case "GameOverCommand": return onGameOver(cmd);
            case "LeaveGameResponseCommand":
                window.location = CONTEXT + "/lobby.jsp";
                return;
            // AuthenticateUserResponseCommand e outros: ignorados (auth feita
            // do lado do servidor no onOpen do endpoint).
        }
    }

    // === Handlers de protocolo ===
    function onJoined(cmd) {
        if (field(cmd, "joined") !== "true") return;
        gameId = field(cmd, "gameId");
        myMarker = field(cmd, "marker");
        rows = parseInt(field(cmd, "boardRows") || "4", 10);
        cols = parseInt(field(cmd, "boardCols") || "4", 10);
        buildBoard();
        myTurn = (myMarker === "A");
        setStatus("You are Player " + myMarker);
        updateTurn();
        startCountdown();
    }

    function onLinePlaced(cmd) {
        if (field(cmd, "placed") !== "true") return;
        const d1r = +field(cmd, "dot1Row"), d1c = +field(cmd, "dot1Col");
        const d2r = +field(cmd, "dot2Row"), d2c = +field(cmd, "dot2Col");
        const marker = field(cmd, "marker");
        const extraTurn = field(cmd, "extraTurn") === "true";

        applyMove(d1r, d1c, d2r, d2c, marker);

        const justByMe = (marker === myMarker);
        myTurn = extraTurn ? justByMe : !justByMe;
        updateTurn();
        // Cada jogada valida inicia um novo turno: reinicia o contador (30s).
        startCountdown();
    }

    function onGameOver(cmd) {
        const hasWinner = field(cmd, "hasWinner") === "true";
        const winner = field(cmd, "winnerMarker");
        const a = field(cmd, "scoreA"), b = field(cmd, "scoreB");
        const timeout = field(cmd, "reason") === "TIMEOUT";
        stopCountdown();
        setLinesEnabled(false);
        document.getElementById("turn").textContent = "";
        const tail = (timeout ? " (timeout)" : "") + "  A: " + a + "  B: " + b;
        let msg;
        if (!hasWinner) msg = "Draw!" + tail;
        else if (winner === myMarker) msg = "You win!" + tail;
        else msg = "You lose!" + tail;
        setStatus(msg);
    }

    // === Logica do tabuleiro (espelha o servidor) ===
    function edgeKey(d1r, d1c, d2r, d2c) {
        if (d1r === d2r) return "H_" + d1r + "_" + Math.min(d1c, d2c);
        return "V_" + Math.min(d1r, d2r) + "_" + d1c;
    }

    function applyMove(d1r, d1c, d2r, d2c, marker) {
        const key = edgeKey(d1r, d1c, d2r, d2c);
        edges.add(key);
        markLineDrawn(key);

        // Caixas adjacentes que possam ter ficado completas com esta aresta.
        const boxRows = rows - 1, boxCols = cols - 1;
        const candidates = [];
        const parts = key.split("_");
        const type = parts[0], r = +parts[1], c = +parts[2];
        if (type === "H") {
            if (r - 1 >= 0) candidates.push([r - 1, c]);
            if (r <= boxRows - 1) candidates.push([r, c]);
        } else {
            if (c <= boxCols - 1) candidates.push([r, c]);
            if (c - 1 >= 0) candidates.push([r, c - 1]);
        }
        for (const [br, bc] of candidates) {
            const id = br + "_" + bc;
            if (!boxOwner[id] && boxComplete(br, bc)) {
                boxOwner[id] = marker;
                score[marker]++;
                paintBox(br, bc, marker);
            }
        }
        document.getElementById("scoreA").textContent = "A: " + score.A;
        document.getElementById("scoreB").textContent = "B: " + score.B;
    }

    function boxComplete(br, bc) {
        return edges.has("H_" + br + "_" + bc)
            && edges.has("H_" + (br + 1) + "_" + bc)
            && edges.has("V_" + br + "_" + bc)
            && edges.has("V_" + br + "_" + (bc + 1));
    }

    // === Construcao do tabuleiro (grelha (2r-1) x (2c-1)) ===
    function buildBoard() {
        const board = document.getElementById("board");
        board.innerHTML = "";
        const gridRows = 2 * rows - 1, gridCols = 2 * cols - 1;
        const track = (n) => Array.from({ length: n },
            (_, i) => (i % 2 === 0 ? "16px" : "56px")).join(" ");
        board.style.gridTemplateColumns = track(gridCols);
        board.style.gridTemplateRows = track(gridRows);
        lineButtons.length = 0;

        for (let gr = 0; gr < gridRows; gr++) {
            for (let gc = 0; gc < gridCols; gc++) {
                let cell;
                if (gr % 2 === 0 && gc % 2 === 0) {
                    cell = div("dot");
                } else if (gr % 2 === 0) {
                    const r = gr / 2, c = (gc - 1) / 2;
                    cell = lineButton("H_" + r + "_" + c, "h",
                        r, c, r, c + 1);
                } else if (gc % 2 === 0) {
                    const r = (gr - 1) / 2, c = gc / 2;
                    cell = lineButton("V_" + r + "_" + c, "v",
                        r, c, r + 1, c);
                } else {
                    cell = div("box");
                    cell.id = "box_" + ((gr - 1) / 2) + "_" + ((gc - 1) / 2);
                }
                board.appendChild(cell);
            }
        }
    }

    function div(cls) {
        const d = document.createElement("div");
        d.className = cls;
        return d;
    }

    function lineButton(key, orient, d1r, d1c, d2r, d2c) {
        const b = document.createElement("button");
        b.className = "line " + orient;
        b.dataset.key = key;
        b.addEventListener("click", () => {
            if (!myTurn) return;
            send(buildCommand("PlaceLineCommand", {
                dot1Row: d1r, dot1Col: d1c, dot2Row: d2r, dot2Col: d2c,
                gameId: gameId
            }));
        });
        lineButtons.push(b);
        return b;
    }

    function markLineDrawn(key) {
        const b = document.querySelector('.line[data-key="' + key + '"]');
        if (b) { b.classList.add("drawn"); b.disabled = true; b.dataset.drawn = "1"; }
    }

    function paintBox(br, bc, marker) {
        const el = document.getElementById("box_" + br + "_" + bc);
        if (el) el.classList.add(marker === "A" ? "owned-a" : "owned-b");
    }

    function setLinesEnabled(enabled) {
        for (const b of lineButtons) {
            if (!b.dataset.drawn) b.disabled = !enabled;
        }
    }

    function updateTurn() {
        setLinesEnabled(myTurn);
        document.getElementById("turn").textContent =
            myTurn ? "Your turn" : "Opponent's turn";
    }

    function setStatus(text) {
        document.getElementById("status").textContent = text;
    }

    // === Contagem decrescente (visual; o servidor e a autoridade) ===
    function startCountdown() {
        remaining = TURN_SECONDS;
        renderCountdown();
        if (!countdownTimer) {
            countdownTimer = setInterval(tickCountdown, 1000);
        }
    }

    function tickCountdown() {
        remaining = Math.max(0, remaining - 1);
        renderCountdown();
    }

    function stopCountdown() {
        if (countdownTimer) {
            clearInterval(countdownTimer);
            countdownTimer = null;
        }
        document.getElementById("countdown").textContent = "";
    }

    function renderCountdown() {
        const label = myTurn ? "Your move" : "Opponent";
        document.getElementById("countdown").textContent =
            label + ": " + remaining + "s";
    }

    // Aplica a cor de fundo preferida do utilizador (lida do perfil via REST).
    async function applyPreferredColor() {
        try {
            const res = await fetch(CONTEXT + "/api/users/me");
            if (!res.ok) return;
            const cmd = new DOMParser()
                .parseFromString(await res.text(), "application/xml")
                .documentElement.firstElementChild;
            const color = field(cmd, "preferredColor");
            if (color) document.body.style.background = color;
        } catch (e) {
            // sem cor preferida: mantem o fundo por omissao
        }
    }

    // === Arranque ===
    document.getElementById("leaveBtn").addEventListener("click", () => {
        if (gameId) send(buildCommand("LeaveGameCommand", { gameId: gameId }));
        else window.location = CONTEXT + "/lobby.jsp";
    });
    applyPreferredColor();
    connect();
</script>
</body>
</html>
