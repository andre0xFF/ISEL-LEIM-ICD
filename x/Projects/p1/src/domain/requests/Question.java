package domain.requests;

import java.util.ArrayList;
import java.util.HashMap;

import application.Client;
import application.ServerThread;
import application.User;
import application.protocol.Request;

public class Question implements Request {

    //
    // Functionality
    //
    User from;
    User to;
    String topic;
    String question;
    String answer;

    //
    // Interface
    //
    public Question(String topic, String question) {
        this.topic = topic;
        this.question = question;
    }

    public void set_from(User user) {
        this.from = user;
    }

    public void set_to(User user) {
        this.to = user;
    }

    public String get_topic() {
        return this.topic;
    }

    public String get_question() {
        return this.question;
    }

    @Override
    public String get_request_name() {
        return "question";
    }

    public void answer(String answer) {
        this.answer = answer;
    }
}