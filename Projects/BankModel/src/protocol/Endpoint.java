package protocol;

import protocol.Protocol.Encoding;
import protocol.encodings.XML;

import java.util.HashMap;

public abstract class Endpoint implements Command.CommonCommandHandler, Protocol.ProtocolHandler {

    private Protocol protocol;
    private Encoding encoding = new XML();
    protected abstract Command[] commands();
    protected abstract Encoding encoder();

    public Endpoint(Protocol protocol) {
        this.protocol = protocol;
        this.protocol.add_handler(this);
    }

    public boolean is_connected() {
        return this.protocol.is_connected();
    }

    public void terminate() {
        this.protocol.terminate();
    }

    public void send(Command command) {
        String message = this.encode(command);
        this.protocol.send(message);
    }

    public void send(Command.Response response) {
        String message = this.encode(response);
        this.protocol.send(message);
    }

    private String encode(Command command) {
        String name = command.name();

        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("name", name);

        String sub_element = this.encoding.encode(name, command.attributes());
        String message = this.encoding.encode("command", attributes, sub_element);

        return message;
    }

    private String encode(Command.Response response) {
        String name = response.name();
        String master = response.master().name();

        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("name", master);
        attributes.put("responder", name);

        String sub_element = this.encoding.encode(name, response.attributes());
        String message = this.encoding.encode("command", attributes, sub_element);

        return message;
    }

    private Command decode(String encoded_message) {
        HashMap<String, String> attributes = this.encoding.decode_attributes(encoded_message);

        String command_name = attributes.get("name");
        String response_name = attributes.get("responder");

        Command[] commands = this.commands();

        for (Command command : commands) {

            Command match;

            if (response_name != null) {
                match = command.search(response_name);
            }
            else {
                match = command.search(command_name);
            }

            if (match != null) {
                return match;
            }
        }

        return null;
    }

    @Override
    public void on_received_message(String message) {
        Command match = this.decode(message);

        if (match != null) {
            this.on_command_received(match);
        }
    }
}
