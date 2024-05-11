package pt.isel.icd.patterns.modelviewcontroller;

import pt.isel.icd.othello.ui.transition.StateMachine;

import javax.swing.*;

public interface View {

    void setController(Controller controller);
    
    Controller getController();

    void setTransitionMachine(StateMachine stateMachine);

}
