package domain.users;

import java.util.ArrayList;
import java.util.HashMap;

import application.Client;
import application.ClientListener;
import application.User;
import application.protocol.Message;
import domain.DomainListener;
import domain.requests.Question;

public class Teacher implements User, ClientListener {
    
    //
    // Functionality
    //
    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    HashMap<Student, ArrayList<Question>> asked_questions = new HashMap<>();
    Client client = new Client();
    ArrayList<DomainListener> listeners = new ArrayList<>();

    private boolean ask(Question question, Student student) {
        Question new_question = new Question(question.get_topic(), question.get_question());
        new_question.set_from(this);
        new_question.set_to(student);
        // TODO Set question timer

        ArrayList<Question> questions = this.asked_questions.getOrDefault(student, new ArrayList<>());
        questions.add(new_question);

        this.client.send(new Message(new_question));
        
        return true;
    }

    //
    // Interface
    //
    public void add(DomainListener listener) {
        this.listeners.add(listener);
    }

    public ArrayList<Student> get_students() {
        return this.students;
    }

    public boolean ask(int question_id, String student_name) {
        Student student = null;
        Question question = this.questions.get(question_id);

        for (Student s : this.students) {
            if (s.get_name().equals(student_name)) {
                student = s;
            }
        }

        if (student == null || question == null) {
            return false;
        }

        return this.ask(question, student);
    }

    public ArrayList<Question> get_questions() {
        return this.questions;
    }
}