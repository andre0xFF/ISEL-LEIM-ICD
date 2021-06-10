package p1.application;

import java.util.HashMap;

public final class Client {

    public void send(Message message) {

    }

    public static void main(String[] args) {
        TextFormat format = new TextFormat();

        format.register((Message) new Ping(12, 12));

        String xml = format.serialize((Message) new Ping(12, 12));

        Message message = format.deserialize(xml);

        System.out.println(message);
    }
}

final class TextFormat implements Format {

    HashMap<String, Message> container = new HashMap<>();

    public void register(Message message) {
        container.put(message.getClass().getName(), message);
    }

    public String serialize(Message message) {
        HashMap<String, String> parameters = message.get_parameters();

        // TODO: Check if message is registered

        String str = String.format("%s", message.getClass().getName());

        for (String key : parameters.keySet()) {
            str = String.format("%s %s %s", str, key, parameters.get(key));
        }

        return str;
    }

    public Message deserialize(String str) {
        String[] split = str.split(" ");

        Message message = container.get(split[0]);

        HashMap<String, String> parameters = new HashMap<>();

        for (int i = 1; i < split.length; i++) {
            parameters.put(split[i], split[++i]);
        }

        return message.recreate(parameters);
    }
}

class Ping implements Message {

    private int key1 = 0;
    private int key2 = 10;

    public Ping(int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    public HashMap<String, String> get_parameters() {
        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("key1", String.valueOf(key1));
        parameters.put("key2", String.valueOf(key2));

        return parameters;
    }

    @Override
    public Message recreate(HashMap<String, String> parameters) {
        return new Ping(
                Integer.valueOf(parameters.get("key1")),
                Integer.valueOf(parameters.get("key2"))
        );
    }

    @Override
    public String toString() {
        return "Ping{" +
                "key1=" + key1 +
                ", key2=" + key2 +
                '}';
    }
}

interface Message {
    
    HashMap<String, String> get_parameters();
    Message recreate(HashMap<String, String> parameters);
}

interface Format {
    
    String serialize(Message message);
    Message deserialize(String string);
}