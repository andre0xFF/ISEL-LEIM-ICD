package domain.users;

import java.util.ArrayList;
import java.util.HashMap;

import application.Client;
import application.ClientListener;
import application.User;
import application.protocol.Message;
import domain.requests.Question;

public class Teacher implements User, ClientListener {
    
    //
    // Functionality
    //
    ArrayList<Student> students = new ArrayList<>();
    HashMap<Question, ArrayList<String>> questions = new HashMap<>();
    HashMap<Student, ArrayList<Question>> asked_questions = new HashMap<>();
    Client client = new Client();

    //
    // Interface
    //
    public ArrayList<Student> get_students() {
        return this.students;
    }

    public boolean ask(Question question, Student student) {
        Question new_question = new Question(question.get_topic(), question.get_question());
        new_question.set_from(this);
        new_question.set_to(student);
        // TODO Set question timer

        ArrayList<Question> questions = this.asked_questions.getOrDefault(student, new ArrayList<>());
        questions.add(new_question);

        this.client.send(new_question);
        
        return true;
    }

    public HashMap<Question, ArrayList<String>> get_questions() {
        return this.questions;
    }
}