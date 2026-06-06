package pt.isel.icd.communication;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Path;
import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SchemaValidator {

    /**
     * Recurso do XSD no classpath. Carregar a partir do classpath (em vez de um
     * caminho relativo ao diretorio de trabalho) permite que o servidor corra a
     * partir de qualquer diretorio (resolve a lacuna L7).
     */
    public static final String DEFAULT_XSD_CLASSPATH_RESOURCE =
        "/schemas/Commands.xsd";

    /**
     * O Schema e imutavel e thread-safe; o Validator NAO e. Por isso guardamos o
     * Schema e criamos um Validator novo por cada validacao, evitando corrupcao
     * de estado quando varias ligacoes validam em simultaneo.
     */
    private final Schema schema;

    public SchemaValidator() {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(
            XMLConstants.W3C_XML_SCHEMA_NS_URI
        );
        URL schemaUrl = SchemaValidator.class.getResource(
            DEFAULT_XSD_CLASSPATH_RESOURCE
        );
        if (schemaUrl == null) {
            throw new RuntimeException(
                "XSD nao encontrado no classpath: " +
                    DEFAULT_XSD_CLASSPATH_RESOURCE
            );
        }
        try {
            schema = schemaFactory.newSchema(schemaUrl);
        } catch (SAXException e) {
            throw new RuntimeException(
                "Falha ao carregar o XSD do classpath: " + schemaUrl,
                e
            );
        }
    }

    public SchemaValidator(Path xsdPath) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(
            XMLConstants.W3C_XML_SCHEMA_NS_URI
        );
        java.io.File schemaFile = xsdPath.toFile();

        try {
            schema = schemaFactory.newSchema(schemaFile);
        } catch (SAXException e) {
            throw new RuntimeException(
                "Failed to load XSD schema: " + xsdPath,
                e
            );
        }
    }

    public void validate(String xmlContent) throws IOException, SAXException {
        // Um Validator novo por chamada: seguro com varias threads concorrentes.
        Validator validator = schema.newValidator();
        validator.validate(
            new SAXSource(new InputSource(new StringReader(xmlContent)))
        );
    }
}
