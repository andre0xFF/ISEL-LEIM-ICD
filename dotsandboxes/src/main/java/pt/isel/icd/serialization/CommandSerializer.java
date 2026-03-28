package pt.isel.icd.serialization;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.communication.SimpleSocketCommand;

/**
 * Serializes/deserializes SimpleSocketCommand objects to/from XML using DOM.
 * Each command must know how to convert itself to/from an XML Element (via XmlSerializable).
 */
public class CommandSerializer {

    /**
     * Interface that commands implement to support DOM-based serialization.
     */
    public interface XmlSerializable {
        void toXml(Document doc, Element element);
        void fromXml(Element element);
    }

    private final Map<
        String,
        Function<Element, SimpleSocketCommand<?>>
    > deserializers = new HashMap<>();

    public void register(
        String commandName,
        Function<Element, SimpleSocketCommand<?>> factory
    ) {
        deserializers.put(commandName, factory);
    }

    public String serialize(SimpleSocketCommand<?> command) {
        Document doc = XmlHelper.createDocument();
        Element root = doc.createElement("Command");
        doc.appendChild(root);

        Element commandElement = doc.createElement(command.commandName());
        root.appendChild(commandElement);

        if (command instanceof XmlSerializable serializable) {
            serializable.toXml(doc, commandElement);
        }

        return XmlHelper.serialize(doc);
    }

    @SuppressWarnings("unchecked")
    public SimpleSocketCommand<?> deserialize(String xml) {
        Document doc = XmlHelper.parse(xml);
        Element root = doc.getDocumentElement();

        Element commandElement = null;
        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            if (root.getChildNodes().item(i) instanceof Element el) {
                commandElement = el;
                break;
            }
        }

        if (commandElement == null) {
            throw new RuntimeException(
                "Invalid command XML: no command element found"
            );
        }

        String commandName = commandElement.getTagName();
        Function<Element, SimpleSocketCommand<?>> factory = deserializers.get(
            commandName
        );

        if (factory == null) {
            throw new RuntimeException("Unknown command type: " + commandName);
        }

        return factory.apply(commandElement);
    }
}
