package models;

public interface CommunicationProtocol {

    public void send(Command cmd);
    public void close();

    public interface Command {
        public void execute();
        public String get_name();
        public Command get_response();
        public Message get();
    }

    public interface Message {

        public String encode();

        public interface PlainText {
            public PlainText decode(String msg);
        }

        public interface XML {
            public XML decode(String msg);
        }

        public interface TwoDottedCodes {
            public TwoDottedCodes decode(String msg);
        }
    }
}
