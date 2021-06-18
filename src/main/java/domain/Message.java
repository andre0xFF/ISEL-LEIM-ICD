package domain;

import application.Channel;
import application.Resource;
import application.User;

import java.time.LocalDateTime;

public class Message implements Resource {

    private User sender;
    private Channel receiver;
    private String content;
    private LocalDateTime when = LocalDateTime.now();

    public Message(User sender, Channel receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

	public User getSender() {
		return this.sender;
	}

    public Channel getReceiver() {
        return this.receiver;
    }
}
