package protocol;

import commands.Command;

public abstract class Encoder {

    protected abstract String name();
    protected abstract String opening();
    protected abstract String opening_end();
    protected abstract String closing();

    public String wrap(String encoded_command, String command_name) {
        return Encoder.wrap(
                this.opening(),
                command_name,
                this.opening_end(),
                encoded_command,
                this.closing()
        );
    }

    public String unwrap(String encoded_command) {
        return Encoder.unwrap(
                this.opening_end(),
                encoded_command,
                this.closing()
        );
    }

    public static String wrap(String opening, String command_name, String opening_end, String encoded_command, String closing) {
        return String.format(
                "%s%s%s%s%s",
                opening,
                command_name,
                opening_end,
                encoded_command,
                closing
        );
    }

    public static String unwrap(String opening_end, String encoded_command, String closing) {
        String unwrap = encoded_command;
        int a = encoded_command.indexOf(opening_end);
        unwrap = unwrap.substring(a + 2);
        unwrap = unwrap.replaceAll(closing, "");
        return unwrap;
    }

    @Override
    public boolean equals(Object object) {
        return (
                object instanceof Encoder && ((Encoder) object).name().equals(this.name())
        );
    }

    public interface Encoding {

        Encoder encoder();
        Command command();
        String encode(Command command);

        default Command decode(String encoded_command) {
            String match = this.encode(command());
            return match.equalsIgnoreCase(encoded_command) ? command() : null;
        }
    }

    public final static class XML extends Encoder {

        @Override
        protected String opening() {
            return "<command name=\"";
        }

        @Override
        protected String opening_end() {
            return "\">";
        }

        @Override
        protected String closing() {
            return "</command>";
        }

        @Override
        protected String name() {
            return "XML";
        }
    }

    public final static class JSON extends Encoder {

        @Override
        protected String name() {
            return "JSON";
        }

        @Override
        protected String opening() {
            return "{ \"command\": { \"name\": ";
        }

        @Override
        protected String opening_end() {
            return ",\" ";
        }

        @Override
        protected String closing() {
            return "} }";
        }
    }

    public final static class Text extends Encoder {

        @Override
        public String unwrap(String encoded_command) {
            return null;
        }

        @Override
        protected String name() {
            return "Text";
        }

        @Override
        protected String opening() {
            return "command:";
        }

        @Override
        protected String opening_end() {
            return ",";
        }

        @Override
        protected String closing() {
            return ";";
        }
    }
}
