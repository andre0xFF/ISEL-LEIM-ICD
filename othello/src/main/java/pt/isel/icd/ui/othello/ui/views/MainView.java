package pt.isel.icd.othello.ui.views;

import com.sun.tools.javac.Main;
import pt.isel.icd.othello.ui.controller.OthelloController;
import pt.isel.icd.othello.ui.controller.StartMenuController;
import pt.isel.icd.othello.ui.transition.Event;
import pt.isel.icd.othello.ui.transition.StateMachine;
import pt.isel.icd.patterns.modelviewcontroller.Controller;
import pt.isel.icd.patterns.modelviewcontroller.View;

import javax.swing.*;
import java.awt.*;

public class MainView implements View {

    public Controller controller;

    private JFrame frame;

    private OthelloView currentView;

    private static final int BUTTON_SIZE = 60;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return null;
    }

    @Override
    public void setTransitionMachine(StateMachine stateMachine) {
        
    }

    public MainView(){
        createFrame(10, 10);

        this.frame.setVisible(true);
    }


    public void createFrame(int rows, int columns){
        frame = new JFrame("Othello");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(BUTTON_SIZE * columns, (BUTTON_SIZE * rows) + 40));

    }


    
    public void switchMainView(OthelloView view){
        this.currentView = view;
//        updateViewDisplay();
    }
    
    
    
    public static void main(String[] args){
        
        

        JFrame frame= new JFrame();
        frame = new JFrame("Othello");
        int columns = 10;
        int rows = 10;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(new Dimension(BUTTON_SIZE * columns, (BUTTON_SIZE * rows) + 40));
        
        
        OthelloController controller = new OthelloController();
        StartMenuView startView = new StartMenuView(frame);
        startView.setController(controller);
       
        
        AuthView authView = new AuthView(frame);
        authView.setController(controller);
        
        SignUpView signUpView = new SignUpView(frame);
        signUpView.setController(controller);
        
        GameMenuView gameMenuView = new GameMenuView(frame);
        gameMenuView.setController(controller);
        
        ProfileView profileView = new ProfileView(frame);
        profileView.setController(controller);
        
        GameHistoryView gameHistoryView = new GameHistoryView(frame);
        gameHistoryView.setController(controller);
        
        BoardView boardView = new BoardView(frame);
        boardView.setController(controller);
        
        QueueView queueView = new QueueView(frame);
        queueView.setController(controller);
        
        
        
        
        
        
        StateMachine startMenuViewTransition = new StateMachine();
        startMenuViewTransition.addStateTransition(Event.CLICK_LOGIN, authView);
        startMenuViewTransition.addStateTransition(Event.CLICK_SIGNUP, signUpView);
        
        
        
        startView.setTransitionMachine(startMenuViewTransition);
        
        
        StateMachine authViewTransition = new StateMachine();
        authViewTransition.addStateTransition(Event.CLICK_BACK, startView);
        authViewTransition.addStateTransition(Event.CLICK_SUBMIT, gameMenuView);
        authView.setTransitionMachine(authViewTransition);
        
        
        ViewSwitcher.update(frame, startView);
        
        
        
        
        StateMachine signUpViewTransition = new StateMachine();
        signUpViewTransition.addStateTransition(Event.CLICK_BACK, startView);
        signUpViewTransition.addStateTransition(Event.CLICK_SUBMIT, authView);
        signUpView.setTransitionMachine(signUpViewTransition);
        
        StateMachine mainMenuViewTransition = new StateMachine();
        mainMenuViewTransition.addStateTransition(Event.CLICK_PROFILE, profileView);
        mainMenuViewTransition.addStateTransition(Event.CLICK_HISTORY, gameHistoryView);
        mainMenuViewTransition.addStateTransition(Event.CLICK_NEWGAME, queueView);
        gameMenuView.setTransitionMachine(mainMenuViewTransition);
        
        StateMachine profileViewTransition = new StateMachine();
        profileViewTransition.addStateTransition(Event.CLICK_BACK, gameMenuView);
//        profileViewTransition.addStateTransition(Event.CLICK_EDIT, gameMenuView);
        
        profileView.setTransitionMachine(profileViewTransition);
        
        StateMachine gameHistoryTransition = new StateMachine();
        gameHistoryTransition.addStateTransition(Event.CLICK_BACK, gameMenuView);
        
        gameHistoryView.setTransitionMachine(gameHistoryTransition);
        
        StateMachine boardViewTransition = new StateMachine();
        boardViewTransition.addStateTransition(Event.CLICK_BACK, queueView);
        boardView.setTransitionMachine(boardViewTransition);
        
        
        StateMachine queueViewTransition = new StateMachine();
        queueViewTransition.addStateTransition(Event.CLICK_BACK, gameMenuView);
        queueViewTransition.addStateTransition(Event.SUBMIT, boardView);
        queueView.setTransitionMachine(queueViewTransition);
        
        

    }
}