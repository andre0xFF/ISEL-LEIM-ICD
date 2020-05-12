package domain.requests;

import java.util.ArrayList;
import java.util.HashMap;

import application.Client;
import application.ServerThread;
import application.User;
import application.protocol.Request;
import application.protocol.Response;

public class Question implements Request {

    //
    // Functionality
    //
    User from;
    User to;
    String topic;
    String question;
    ArrayList<Answer> answers = new ArrayList<>();

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

    @Override
    public HashMap<String, String> get_request_params() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void set_request_params(HashMap<String, String> params) {
        // TODO Auto-generated method stub

    }

    @Override
    public Response execute_request(ServerThread server) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response execute_request(Client client) {
        // TODO Auto-generated method stub
        return null;
    }

    public void add_answer(Answer answer) {
        this.answers.add(answer);
    }
}