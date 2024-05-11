package pt.isel.icd.othello.ui.views;



import pt.isel.icd.othello.ui.controller.OthelloController;
import pt.isel.icd.othello.ui.transition.Event;
import pt.isel.icd.patterns.modelviewcontroller.Controller;
import pt.isel.icd.patterns.modelviewcontroller.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthView extends OthelloView {
    

    JTextField authenticationUsernameField;

    JPasswordField authenticationPasswordField;

    JButton loginButton;

    JButton logInBackButton;
    

    
    public AuthView(JFrame frame){
    
        super(frame);
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(bagLayout);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets.top = 10;

        JLabel usernameLabel = new JLabel("Username: ");

        this.add(usernameLabel, constraints);

        authenticationUsernameField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets.top = 10;

        this.add(authenticationUsernameField, constraints);

        JLabel passwordLabel = new JLabel("Password: ");

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets.bottom = 10;
        this.add(passwordLabel, constraints);

        authenticationPasswordField = new JPasswordField(10);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets.bottom = 10;
        this.add(authenticationPasswordField, constraints);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                
//                controller.updateView(frame, this); //TODO send auth request to server.
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_SUBMIT));
            }
        });

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        this.add(loginButton, constraints);





        logInBackButton = new JButton("Back");
        logInBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                ((UiController) controller).navStartMenu();
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_BACK) );
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;

        this.add(logInBackButton, constraints);

        JLabel authenticationDisplayError = new JLabel("Error");


        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.SOUTH;

        this.add(authenticationDisplayError, constraints);

        this.setOpaque(true);
    }
    
}