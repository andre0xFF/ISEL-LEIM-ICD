import sessions.Messenger;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Messenger messenger = new Messenger();
        messenger.start();
    }
}
