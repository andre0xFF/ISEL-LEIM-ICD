package pt.isel.icd.othello.ui.views;


import pt.isel.icd.othello.ui.controller.OthelloController;
import pt.isel.icd.othello.ui.controller.StartMenuController;
import pt.isel.icd.othello.ui.transition.Event;
import pt.isel.icd.patterns.modelviewcontroller.Controller;
import pt.isel.icd.patterns.modelviewcontroller.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenuView extends OthelloView {


 
    
    

    public StartMenuView(JFrame frame){
        
        super(frame);
        
        
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(bagLayout);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.top = 10;


        JButton startLoginButton = new JButton("Log In");
        startLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO inform controller to change current panel to LogIn JPanel

                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_LOGIN));
                
              
                
            }

        });

        this.add(startLoginButton, constraints);


        startLoginButton.setPreferredSize(new Dimension(100, 40));

        JButton startSignUpButton = new JButton("Sign Up");
        startSignUpButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO inform controller to change current panel to SignUp JPanel
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_SIGNUP));
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.top = 10;

        this.add(startSignUpButton, constraints);
        startSignUpButton.setPreferredSize(new Dimension(100, 40));
        
        
        frame.setVisible(true);
        
       

        
       
    }
    
}