package pt.isel.icd;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.game.management.JoinGameCommand;
import pt.isel.icd.serialization.XMLSerializer;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        XMLSerializer xml = new XMLSerializer();

        JoinGameCommand joinGameCommand = new JoinGameCommand();

        String line1 = xmlMapper.writeValueAsString(joinGameCommand);
        String line2 = xml.serialize(joinGameCommand);

        SimpleSocketCommand<?> simpleSocketCommand1 = xml.deserialize(line1, SimpleSocketCommand.class);
        SimpleSocketCommand<?> simpleSocketCommand2 = xml.deserialize(line2, SimpleSocketCommand.class);
        SimpleSocketCommand<?> simpleSocketCommand3 = xmlMapper.readValue(line1, SimpleSocketCommand.class);
        SimpleSocketCommand<?> simpleSocketCommand4 = xmlMapper.readValue(line2, SimpleSocketCommand.class);
    }
}
