import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * TransformAgenda — Reads agenda.xml into a DOM tree, applies business logic
 * to enrich it with computed fields, then passes the enriched DOM to an XSLT
 * stylesheet to produce an HTML output file.
 *
 * Pipeline:
 *   XML (raw data) → Java/DOM (business logic) → XSLT (presentation) → HTML
 *
 * Business logic applied:
 *   - Eventos:     adds <duracao> element (e.g. "9h00m")
 *   - Tarefas:     adds atrasada="true" attribute if overdue;
 *                   adds <diasRestantes> element with days until deadline
 *   - Inscrições:  adds totalUCs="N" attribute with the UC count
 *
 * Usage (from the tutorial-1 root directory):
 *   javac -d build src/TransformAgenda.java
 *   java -cp build TransformAgenda [xml-file] [xsl-file] [output-html]
 *
 * Defaults (no arguments):
 *   xml  = data/agenda.xml
 *   xsl  = xsl/agenda.xsl
 *   out  = output/agenda.html
 */
public class TransformAgenda {

    private static final DateTimeFormatter DT_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static void main(String[] args) {
        String xmlFile = args.length > 0 ? args[0] : "data/agenda.xml";
        String xslFile = args.length > 1 ? args[1] : "xsl/agenda.xsl";
        String outFile = args.length > 2 ? args[2] : "output/agenda.html";

        try {
            File xml = new File(xmlFile);
            File xsl = new File(xslFile);

            if (!xml.exists()) {
                System.err.println(
                    "ERROR: XML file not found: " + xml.getAbsolutePath()
                );
                System.exit(1);
            }
            if (!xsl.exists()) {
                System.err.println(
                    "ERROR: XSL file not found: " + xsl.getAbsolutePath()
                );
                System.exit(1);
            }

            // ── 1. Parse XML into DOM ──────────────────────────────────
            DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();

            // ── 2. Apply business logic ────────────────────────────────
            enrichEventos(doc);
            enrichTarefas(doc);
            enrichInscricoes(doc);

            System.out.println("Business logic applied successfully.");

            // ── 3. Transform enriched DOM with XSLT ───────────────────
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(
                new StreamSource(xsl)
            );

            // DOMSource instead of StreamSource — this is the key difference
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outFile));

            transformer.transform(source, result);

            System.out.println("Transformation complete: " + outFile);
        } catch (Exception e) {
            System.err.println("Transformation failed:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  EVENTOS: compute and add <duracao> (e.g. "9h00m", "1h30m")
    // ════════════════════════════════════════════════════════════════
    private static void enrichEventos(Document doc) {
        NodeList eventos = doc.getElementsByTagName("evento");

        for (int i = 0; i < eventos.getLength(); i++) {
            Element evento = (Element) eventos.item(i);

            String inicioStr = getTextContent(evento, "inicio");
            String fimStr = getTextContent(evento, "fim");

            if (inicioStr == null || fimStr == null) continue;

            LocalDateTime inicio = LocalDateTime.parse(inicioStr, DT_FORMAT);
            LocalDateTime fim = LocalDateTime.parse(fimStr, DT_FORMAT);
            Duration duracao = Duration.between(inicio, fim);

            long hours = duracao.toHours();
            long minutes = duracao.toMinutes() % 60;

            Element duracaoEl = doc.createElement("duracao");
            duracaoEl.setTextContent(String.format("%dh%02dm", hours, minutes));
            evento.appendChild(duracaoEl);
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  TAREFAS: mark overdue tasks, compute days remaining
    // ════════════════════════════════════════════════════════════════
    private static void enrichTarefas(Document doc) {
        NodeList tarefas = doc.getElementsByTagName("tarefa");
        LocalDate today = LocalDate.now();

        for (int i = 0; i < tarefas.getLength(); i++) {
            Element tarefa = (Element) tarefas.item(i);

            String prazoStr = getTextContent(tarefa, "prazo");
            String concluidaStr = tarefa.getAttribute("concluida");

            if (prazoStr == null) continue;

            LocalDate prazo = LocalDate.parse(prazoStr);
            boolean concluida = "true".equalsIgnoreCase(concluidaStr);
            long diasRestantes = ChronoUnit.DAYS.between(today, prazo);

            // Mark as overdue if past deadline and not concluded
            if (!concluida && diasRestantes < 0) {
                tarefa.setAttribute("atrasada", "true");
            }

            // Add <diasRestantes> element (negative = overdue)
            Element diasEl = doc.createElement("diasRestantes");
            diasEl.setTextContent(String.valueOf(diasRestantes));
            tarefa.appendChild(diasEl);
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  INSCRIÇÕES: count total UCs per inscricao
    // ════════════════════════════════════════════════════════════════
    private static void enrichInscricoes(Document doc) {
        NodeList inscricoes = doc.getElementsByTagName("inscricao");

        for (int i = 0; i < inscricoes.getLength(); i++) {
            Element inscricao = (Element) inscricoes.item(i);

            NodeList ucs = inscricao.getElementsByTagName("abrUC");
            inscricao.setAttribute("totalUCs", String.valueOf(ucs.getLength()));
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  UTILITY: get text content of a child element by tag name
    // ════════════════════════════════════════════════════════════════
    private static String getTextContent(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) return null;
        return nodes.item(0).getTextContent().trim();
    }
}
