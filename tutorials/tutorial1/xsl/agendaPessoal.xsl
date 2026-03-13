<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output
        method="html"
        encoding="UTF-8"
        indent="yes"
        doctype-system="about:legacy-compat"
    />

  <xsl:template match="/">
    <html lang="pt">
      <head>
        <meta charset="UTF-8" />
        <title>Agenda</title>
        <style>
          * { box-sizing: border-box; margin: 0; padding: 0; }
          body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f0f2f5;
            color: #333;
            padding: 2rem;
          }
          h1 {
            text-align: center;
            margin-bottom: 2rem;
            color: #1a1a2e;
            font-size: 2rem;
          }
          h2 {
            color: #16213e;
            margin-bottom: 1rem;
            padding-bottom: 0.5rem;
            border-bottom: 3px solid #0f3460;
          }
          .section {
            background: #fff;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 2rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
          }
          table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 0.5rem;
          }
          th, td {
            text-align: left;
            padding: 0.75rem 1rem;
            border-bottom: 1px solid #e0e0e0;
          }
          th {
            background: #0f3460;
            color: #fff;
            font-weight: 600;
          }
          tr:last-child td { border-bottom: none; }
          tr:hover td { background: #f7f9fc; }
          .badge {
            display: inline-block;
            padding: 0.2rem 0.6rem;
            border-radius: 999px;
            font-size: 0.8rem;
            font-weight: 600;
            color: #fff;
          }
          .badge-conferencia { background: #e94560; }
          .badge-workshop   { background: #0f3460; }
          .badge-outro      { background: #7a7a7a; }
          .badge-alta       { background: #e94560; }
          .badge-media      { background: #f5a623; }
          .badge-baixa      { background: #4caf50; }
          .status-done      { color: #4caf50; font-weight: bold; }
          .status-pending   { color: #e94560; font-weight: bold; }
          .status-overdue   { color: #fff; background: #b71c1c; font-weight: bold;
                              padding: 0.2rem 0.6rem; border-radius: 999px; font-size: 0.8rem; }
          .days-remaining   { font-size: 0.85rem; color: #555; }
          .days-overdue     { font-size: 0.85rem; color: #b71c1c; font-weight: 600; }
          .turma-tag {
            display: inline-block;
            background: #e8eaf6;
            color: #283593;
            padding: 0.2rem 0.6rem;
            border-radius: 6px;
            margin: 0.15rem;
            font-size: 0.85rem;
            font-weight: 600;
          }
          .no-data { color: #999; font-style: italic; }
        </style>
      </head>
      <body>
        <h1>&#128197; Agenda</h1>

        <!-- Eventos -->
        <xsl:apply-templates select="agenda/eventos" />

        <!-- Tarefas -->
        <xsl:apply-templates select="agenda/tarefas" />

        <!-- Inscrições -->
        <xsl:apply-templates select="agenda/inscricoes" />
      </body>
    </html>
  </xsl:template>

  <!-- ======================== EVENTOS ======================== -->
  <xsl:template match="eventos">
    <div class="section">
      <h2>&#128204; Eventos</h2>
      <xsl:choose>
        <xsl:when test="evento">
          <table>
            <tr>
              <th>Título</th>
              <th>Categoria</th>
              <th>Início</th>
              <th>Fim</th>
              <th>Duração</th>
              <th>Local</th>
            </tr>
            <xsl:apply-templates select="evento" />
          </table>
        </xsl:when>
        <xsl:otherwise>
          <p class="no-data">Sem eventos registados.</p>
        </xsl:otherwise>
      </xsl:choose>
    </div>
  </xsl:template>

  <xsl:template match="evento">
    <tr>
      <td><xsl:value-of select="titulo" /></td>
      <td>
        <xsl:choose>
          <xsl:when test="@categoria = 'Conferência'">
            <span class="badge badge-conferencia"><xsl:value-of
                                select="@categoria"
                            /></span>
          </xsl:when>
          <xsl:when test="@categoria = 'Workshop'">
            <span class="badge badge-workshop"><xsl:value-of
                                select="@categoria"
                            /></span>
          </xsl:when>
          <xsl:when test="@categoria">
            <span class="badge badge-outro"><xsl:value-of
                                select="@categoria"
                            /></span>
          </xsl:when>
          <xsl:otherwise>
            <span class="no-data">N/A</span>
          </xsl:otherwise>
        </xsl:choose>
      </td>
      <td>
        <xsl:call-template name="format-datetime">
          <xsl:with-param name="dt" select="inicio" />
        </xsl:call-template>
      </td>
      <td>
        <xsl:call-template name="format-datetime">
          <xsl:with-param name="dt" select="fim" />
        </xsl:call-template>
      </td>
      <td>
        <xsl:choose>
          <xsl:when test="duracao">
            <xsl:value-of select="duracao" />
          </xsl:when>
          <xsl:otherwise>
            <span class="no-data">N/A</span>
          </xsl:otherwise>
        </xsl:choose>
      </td>
      <td>
        <xsl:choose>
          <xsl:when test="local">
            <xsl:value-of select="local" />
          </xsl:when>
          <xsl:otherwise>
            <span class="no-data">Não definido</span>
          </xsl:otherwise>
        </xsl:choose>
      </td>
    </tr>
  </xsl:template>

  <!-- ======================== TAREFAS ======================== -->
  <xsl:template match="tarefas">
    <div class="section">
      <h2>&#9989; Tarefas</h2>
      <xsl:choose>
        <xsl:when test="tarefa">
          <table>
            <tr>
              <th>Descrição</th>
              <th>Prazo</th>
              <th>Dias Restantes</th>
              <th>Prioridade</th>
              <th>Estado</th>
            </tr>
            <xsl:apply-templates select="tarefa" />
          </table>
        </xsl:when>
        <xsl:otherwise>
          <p class="no-data">Sem tarefas registadas.</p>
        </xsl:otherwise>
      </xsl:choose>
    </div>
  </xsl:template>

  <xsl:template match="tarefa">
    <tr>
      <td><xsl:value-of select="descricao" /></td>
      <td>
        <xsl:call-template name="format-date">
          <xsl:with-param name="d" select="prazo" />
        </xsl:call-template>
      </td>
      <td>
        <xsl:choose>
          <xsl:when test="@concluida = 'true'">
            <span class="days-remaining">—</span>
          </xsl:when>
          <xsl:when test="diasRestantes and diasRestantes &lt; 0">
            <span class="days-overdue"><xsl:value-of
                                select="diasRestantes"
                            /> dias (atrasada)</span>
          </xsl:when>
          <xsl:when test="diasRestantes">
            <span class="days-remaining"><xsl:value-of
                                select="diasRestantes"
                            /> dias</span>
          </xsl:when>
          <xsl:otherwise>
            <span class="no-data">N/A</span>
          </xsl:otherwise>
        </xsl:choose>
      </td>
      <td>
        <xsl:choose>
          <xsl:when test="prioridade = 'Alta'">
            <span class="badge badge-alta"><xsl:value-of
                                select="prioridade"
                            /></span>
          </xsl:when>
          <xsl:when test="prioridade = 'Média'">
            <span class="badge badge-media"><xsl:value-of
                                select="prioridade"
                            /></span>
          </xsl:when>
          <xsl:when test="prioridade = 'Baixa'">
            <span class="badge badge-baixa"><xsl:value-of
                                select="prioridade"
                            /></span>
          </xsl:when>
          <xsl:otherwise>
            <span class="badge badge-outro"><xsl:value-of
                                select="prioridade"
                            /></span>
          </xsl:otherwise>
        </xsl:choose>
      </td>
      <td>
        <xsl:choose>
          <xsl:when test="@concluida = 'true'">
            <span class="status-done">&#10003; Concluída</span>
          </xsl:when>
          <xsl:when test="@atrasada = 'true'">
            <span class="status-overdue">&#9888; Atrasada</span>
          </xsl:when>
          <xsl:otherwise>
            <span class="status-pending">&#9711; Pendente</span>
          </xsl:otherwise>
        </xsl:choose>
      </td>
    </tr>
  </xsl:template>

  <!-- ======================== INSCRIÇÕES ======================== -->
  <xsl:template match="inscricoes">
    <div class="section">
      <h2>&#128218; Inscrições</h2>
      <xsl:choose>
        <xsl:when test="inscricao">
          <table>
            <tr>
              <th>Turma</th>
              <th>Unidades Curriculares</th>
              <th>Total UCs</th>
            </tr>
            <xsl:apply-templates select="inscricao" />
          </table>
        </xsl:when>
        <xsl:otherwise>
          <p class="no-data">Sem inscrições registadas.</p>
        </xsl:otherwise>
      </xsl:choose>
    </div>
  </xsl:template>

  <xsl:template match="inscricao">
    <tr>
      <td><strong><xsl:value-of select="@turma" /></strong></td>
      <td>
        <xsl:for-each select="abrUC">
          <span class="turma-tag"><xsl:value-of select="." /></span>
        </xsl:for-each>
      </td>
      <td>
        <xsl:choose>
          <xsl:when test="@totalUCs">
            <strong><xsl:value-of select="@totalUCs" /></strong>
          </xsl:when>
          <xsl:otherwise>
            <span class="no-data">N/A</span>
          </xsl:otherwise>
        </xsl:choose>
      </td>
    </tr>
  </xsl:template>

  <!-- ============== UTILITY: format datetime ============== -->
  <!-- Turns '2026-04-10T09:00:00' into '10/04/2026 09:00' -->
  <xsl:template name="format-datetime">
    <xsl:param name="dt" />
    <xsl:variable name="date-part" select="substring-before($dt, 'T')" />
    <xsl:variable name="time-part" select="substring-after($dt, 'T')" />

    <xsl:call-template name="format-date">
      <xsl:with-param name="d" select="$date-part" />
    </xsl:call-template>

    <xsl:if test="$time-part">
      <xsl:text> </xsl:text>
      <!-- HH:MM only -->
      <xsl:value-of select="substring($time-part, 1, 5)" />
    </xsl:if>
  </xsl:template>

  <!-- Turns 'YYYY-MM-DD' into 'DD/MM/YYYY' -->
  <xsl:template name="format-date">
    <xsl:param name="d" />
    <xsl:value-of select="substring($d, 9, 2)" />
    <xsl:text>/</xsl:text>
    <xsl:value-of select="substring($d, 6, 2)" />
    <xsl:text>/</xsl:text>
    <xsl:value-of select="substring($d, 1, 4)" />
  </xsl:template>

</xsl:stylesheet>
