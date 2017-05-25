package protocol;

import java.util.HashMap;

public abstract class Encoding {

    protected abstract String name();
    public abstract String encode(String root, HashMap<String, String> attributes);
    public abstract String decode(String encoded_map);
    public abstract HashMap<String, String> decode(String root, String encoded_map);
    public abstract String wrap(String root, String element, String message);
    public abstract String unwrap(String root, String wrapped_message);
    public abstract String key_value_encode(HashMap<String, String> attributes);


    public interface Encoder {
        HashMap<String, String> attributes();
        void attributes(HashMap<String, String> attributes);
    }


    public final static class XML extends Encoding {

        @Override
        public String encode(String root, HashMap<String, String> attributes) {
            String attributes_string = "";

            if (attributes == null || attributes.size() == 0) {
                return attributes_string;
            }

            for (String key : attributes.keySet()) {
                String value = attributes.get(key);
                attributes_string += String.format(" %s=\"%s\"", key, value);
            }

            return String.format("<%s%s/>", root, attributes_string);
        }

        public String decode(String encoded_map) {
            int a = encoded_map.indexOf(" ");
            return encoded_map.substring(1, a);
        }

        @Override
        public HashMap<String, String> decode(String root, String encoded_map) {
            // See if the string starts with the root name
            String initial = String.format("<%s", root);

            if (!encoded_map.startsWith(initial)) {
                return null;
            }

            encoded_map = encoded_map.replaceAll("<", "");
            encoded_map = encoded_map.replaceAll("/>", "");
            encoded_map = encoded_map.replaceAll("\"", "");
            encoded_map = encoded_map.replaceAll("=", " ");

            String[] elements = encoded_map.split(" ");
            HashMap<String, String> map = new HashMap<>();

            for (int i = 1; i < elements.length; i++) {
                map.put(elements[i], elements[++i]);
            }

            return map;
        }

        @Override
        public String wrap(String root, String element, String message) {
            return String.format("<%s name=\"%s\">%s</%s>", root, element, message, root);
        }

        @Override
        public String unwrap(String root, String wrapped_message) {
            int a = wrapped_message.indexOf(">");
            int b = wrapped_message.lastIndexOf(String.format("</%s>", root));
            return wrapped_message.substring(a + 1, b);
        }

        @Override
        public String key_value_encode(HashMap<String, String> attributes) {
            return null;
        }

        @Override
        protected String name() {
            return "XML";
        }
    }
}
