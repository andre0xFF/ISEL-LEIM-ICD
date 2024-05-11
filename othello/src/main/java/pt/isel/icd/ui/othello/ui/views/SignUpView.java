package pt.isel.icd.othello.ui.views;


import pt.isel.icd.othello.ui.transition.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpView extends OthelloView {
    
    JTextField signUserNameField;
    JPasswordField signPasswordField;
    
    JButton signSubmitButton;
    
    JButton signBackButton;
    
    JLabel signUpDisplayError;
    
  
    
    public SignUpView(JFrame frame){
        super(frame);
        
        
        
        
//        JPanel signUpPanel = new JPanel();

        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();


        this.setLayout(bagLayout);

        JLabel titleLabel = new JLabel("Sign Up");


        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 1;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
//        constraints.insets.bottom = 10;

        this.add(titleLabel, constraints);

        constraints.weighty = 0;
        JLabel signUpPictureLabel = new JLabel("Image");

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;


        this.add(signUpPictureLabel, constraints);

        JTextField signUpPictureField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;

        this.add(signUpPictureField, constraints);


        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;

        this.add(new JLabel("Username"), constraints);


        signUserNameField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;

        this.add(signUserNameField, constraints);


        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;

        this.add(new JLabel("Password"), constraints);


        signPasswordField = new JPasswordField(10);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;

        this.add(signPasswordField, constraints);
        
       
        signSubmitButton = new JButton("Submit");
        
        signSubmitButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_SUBMIT));
                //TODO inform controller of the signUp request
            }
        });

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.EAST;

        this.add(signSubmitButton, constraints);

        signBackButton = new JButton("Back");
        
        signBackButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_BACK));
            }
        });
        
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;

        this.add(signBackButton, constraints);

       
        signUpDisplayError = new JLabel("Error");

        constraints.gridx = 1;
        constraints.gridy = 5;
        
        
        constraints.fill = GridBagConstraints.HORIZONTAL;

        this.add(signUpDisplayError, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.weighty = 1;
        JLabel filler = new JLabel();
//        filler.setVisible(false);
        this.add(filler, constraints);
    
    }

}