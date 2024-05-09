package pt.isel.icd;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import pt.isel.icd.communication.ConnectionCommand;
import pt.isel.icd.game.management.JoinGameCommand;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.verticals.Controller;
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

        ConnectionCommand<?> connectionCommand1 = xml.deserialize(line1, ConnectionCommand.class);
        ConnectionCommand<?> connectionCommand2 = xml.deserialize(line2, ConnectionCommand.class);
        ConnectionCommand<?> connectionCommand3 = xmlMapper.readValue(line1, ConnectionCommand.class);
        ConnectionCommand<?> connectionCommand4 = xmlMapper.readValue(line2, ConnectionCommand.class);
    }
}
