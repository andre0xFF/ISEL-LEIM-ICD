package pt.isel.icd.user.management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * View with login and registration options.
 */
public class StartClientView {

    private JTextField username;
    private JLabel passwordLabel;
    private JPasswordField passwordField;

    public StartClientView(JFrame frame, UserClientController userClientController) {
        JPanel startViewPanel = new JPanel();
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        startViewPanel.setLayout(bagLayout);

//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        constraints.anchor = GridBagConstraints.WEST;
//        constraints.insets.top = 10;

        JLabel usernameLabel = new JLabel("Username");
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        startViewPanel.add(usernameLabel, constraints);

        username = new JTextField(20);
//        nickLabel.setEnabled(false);
        constraints.gridwidth = 2;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;

        startViewPanel.add(username, constraints);

        passwordLabel = new JLabel("Password");

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;

        startViewPanel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        passwordField.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;

        startViewPanel.add(passwordField, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.top = 10;
        JButton startLoginButton = new JButton("Log In");

        startLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: inform controller to change current panel to LogIn JPanel

//                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_LOGIN));

            }
        });

        startViewPanel.add(startLoginButton, constraints);

        startLoginButton.setPreferredSize(new Dimension(100, 40));

        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;

        JButton startSignUpButton = new JButton("Sign Up");
        startSignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: inform controller to change current panel to SignUp JPanel
//                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_SIGNUP));
            }
        });

//        constraints.gridx = 0;
//        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;

        startViewPanel.add(startSignUpButton, constraints);
        startSignUpButton.setPreferredSize(new Dimension(100, 40));

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        JLabel displayMessage = new JLabel("Message asdasdawqwe");
        displayMessage.setForeground(Color.red);
        startViewPanel.add(displayMessage, constraints);

        frame.add(startViewPanel);
//        frame.setVisible(true);
    }
}
