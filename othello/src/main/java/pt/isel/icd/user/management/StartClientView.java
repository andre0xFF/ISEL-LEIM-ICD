package pt.isel.icd.user.management;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * View with login and registration options.
 */
public class StartClientView {

    private final UserClientController userClientController;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel notificationLabel;

    public StartClientView(JFrame frame, UserClientController userClientController) {
        this.userClientController = userClientController;

        JPanel startViewPanel = createStartViewPanel();
        frame.add(startViewPanel);
    }

    private JPanel createStartViewPanel() {
        JPanel startViewPanel = new JPanel();

        startViewPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        addUsernameField(startViewPanel, constraints);
        addPasswordField(startViewPanel, constraints);
        addLoginButton(startViewPanel, constraints);
        addSignUpButton(startViewPanel, constraints);
        addNotificationLabel(startViewPanel, constraints);

        return startViewPanel;
    }

    private void addUsernameField(JPanel panel, GridBagConstraints constraints) {
        JLabel usernameLabel = new JLabel("Username");
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        panel.add(usernameLabel, constraints);

        usernameField = new JTextField(20);
        constraints.gridwidth = 2;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;

        panel.add(usernameField, constraints);
    }

    private void addPasswordField(JPanel panel, GridBagConstraints constraints) {
        JLabel passwordLabel = new JLabel("Password");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;

        panel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        passwordField.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;

        panel.add(passwordField, constraints);
    }

    private void addLoginButton(JPanel panel, GridBagConstraints constraints) {
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(this::handleLoginAction);
        loginButton.setPreferredSize(new Dimension(100, 40));

        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.top = 10;

        panel.add(loginButton, constraints);
    }

    private void addSignUpButton(JPanel panel, GridBagConstraints constraints) {
        JButton startSignUpButton = new JButton("Sign Up");

        startSignUpButton.addActionListener(this::handleSignUpAction);
        startSignUpButton.setPreferredSize(new Dimension(100, 40));

        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;

        panel.add(startSignUpButton, constraints);
    }

    private void addNotificationLabel(JPanel panel, GridBagConstraints constraints) {
        notificationLabel = new JLabel();

        notificationLabel.setForeground(Color.red);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;

        panel.add(notificationLabel, constraints);
    }

    private void handleLoginAction(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            userClientController.authenticate(new User(username, password));
        } catch (JsonProcessingException ex) {
            notificationLabel.setText("Error authenticating user");
        }
    }

    private void handleSignUpAction(ActionEvent e) {
        //TODO: inform controller to change current panel to SignUp JPanel
    }
}
