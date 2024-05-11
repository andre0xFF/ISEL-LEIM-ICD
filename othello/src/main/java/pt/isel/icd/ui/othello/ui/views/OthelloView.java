package pt.isel.icd.othello.ui.views;

import pt.isel.icd.othello.ui.controller.OthelloController;
import pt.isel.icd.othello.ui.transition.StateMachine;
import pt.isel.icd.patterns.modelviewcontroller.Controller;
import pt.isel.icd.patterns.modelviewcontroller.View;

import javax.swing.*;

public class OthelloView extends JPanel implements View {
    
    private JFrame frame;
    
    public OthelloController controller;
    
    private StateMachine viewTransitionMachine = new StateMachine();
    
    
    public OthelloView(JFrame frame){
        this.frame = frame;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = (OthelloController) controller;
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public void setTransitionMachine(StateMachine stateMachine) {
        this.viewTransitionMachine = stateMachine;
    }
    
    public StateMachine getTransitionMachine(){
        return this.viewTransitionMachine;
    }
}