package protocol.encodings;

import protocol.Protocol;

import java.util.HashMap;


public final class XML implements Protocol.Encoding {

    @Override
    public String encode(String root, HashMap<String, String> attributes) {
        return String.format("<%s%s/>", root, parse(attributes));
    }

    @Override
    public String encode(String root, HashMap<String, String> attributes, String sub_element) {
        return String.format("<%s%s>%s</%s>", root, parse(attributes), sub_element, root);
    }

    @Override
    public HashMap<String, String> decode_attributes(String message) {
        int a = message.indexOf(" ");
        int b = message.indexOf(">");
        String attributes = message.substring(a + 1, b);
        return parse(attributes);
    }

    @Override
    public String decode_element(String message) {
        int a = message.indexOf(" ");
        return message.substring(1, a);
    }

    @Override
    public String decode_sub_element(String message) {
        int a = message.indexOf(">");
        int b = message.lastIndexOf("<");
        return message.substring(a + 1, b);
    }

    @Override
    public String parse(HashMap<String, String> attributes) {
        String s = "";

        if (attributes != null) {
            for (String attribute : attributes.keySet()) {
                s += String.format(" %s=\"%s\"", attribute, attributes.get(attribute));
            }
        }

        return s;
    }

    @Override
    public HashMap<String, String> parse(String attributes) {
        attributes = attributes.replaceAll("\"", "");
        attributes = attributes.replaceAll("=", " ");

        String[] elements = attributes.split(" ");
        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < elements.length; i++) {
            map.put(elements[i], elements[++i]);
        }

        return map;
    }

    @Override
    public String name() {
        return "XML";
    }
}
