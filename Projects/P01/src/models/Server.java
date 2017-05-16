package models;

public interface Server {

    // Fields
    public final static int DEFAULT_PORT = 5555;

    // Functions
    public void connect(Client client);
    public void execute();

    // Modules
    interface InternalClient {

    }

    class Host {
        public final String ip;
        public final int port;

        public Host(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        public boolean validate() {
            // TODO
            return true;
        }
    }
}
