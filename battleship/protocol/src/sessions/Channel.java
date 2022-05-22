package sessions;

import controllers_bkp.Message;

public interface Channel {
    void send(Message message);

    void start();
}
