package pt.isel.icd.user.management;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * View with login and registration options.
 */
public class StartClientView extends JPanel{

    JTextField username;
    JLabel passLabel;

    JPasswordField passwordField;


    public StartClientView(JFrame frame, UserClientController userClientController) {

        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(bagLayout);

//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        constraints.anchor = GridBagConstraints.WEST;
//        constraints.insets.top = 10;



        JLabel nickLabel = new JLabel("Username");
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        this.add(nickLabel, constraints);

        username = new JTextField(20);
//        nickLabel.setEnabled(false);
        constraints.gridwidth = 2;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;

        this.add(username, constraints);


        passLabel = new JLabel("Password");

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;

        this.add(passLabel, constraints);


        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;

        this.add(passwordField, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.top = 10;
        JButton startLoginButton = new JButton("Log In");
        startLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO inform controller to change current panel to LogIn JPanel

//                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_LOGIN));



            }

        });

        this.add(startLoginButton, constraints);


        startLoginButton.setPreferredSize(new Dimension(100, 40));

        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;

        JButton startSignUpButton = new JButton("Sign Up");
        startSignUpButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO inform controller to change current panel to SignUp JPanel
//                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_SIGNUP));
            }
        });

//        constraints.gridx = 0;
//        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;


        this.add(startSignUpButton, constraints);
        startSignUpButton.setPreferredSize(new Dimension(100, 40));

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        JLabel displayMessage = new JLabel("Message asdasdawqwe");
        displayMessage.setForeground(Color.red);
        this.add(displayMessage, constraints);



        

    }
}
