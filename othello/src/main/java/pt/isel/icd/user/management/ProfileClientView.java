package pt.isel.icd.user.management;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * View with user profile.
 */
public class ProfileClientView{

    JTextField profileEditPictureField;
    JTextField profileUserNameField;
    JPasswordField profilePasswordField;
    JTextField profileEditNationalityField;

    JFormattedTextField profileEditAgeField;

    JLabel profileEditDisplayError;

    NumberFormat numberFormat;

    boolean editStatus = false;

    JButton profileEditBUtton;

    JButton profileEditSubmitButton;

    JButton uploadButton;

    public ProfileClientView(Frame frame, UserClientController userClientController){

        JPanel profileViewPanel = new JPanel();

        profileViewPanel.setLayout(new BorderLayout());

        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel southPanel = new JPanel();
        profileViewPanel.add(southPanel, BorderLayout.SOUTH);
        JPanel midPanel = new JPanel(new BorderLayout());
        profileViewPanel.add(midPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();

        topPanel.add(new JLabel("Profile"));

        profileViewPanel.add(topPanel, BorderLayout.NORTH);

        JPanel imagePanel = new JPanel();
        GridBagLayout panelLayout = new GridBagLayout();
        midPanel.setLayout(panelLayout);

        GridBagConstraints panelConstrains = new GridBagConstraints();
        panelConstrains.gridx = 0;

        imagePanel.setLayout(bagLayout);
        midPanel.add(imagePanel, panelConstrains);

        panelConstrains.gridy = 1;

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(bagLayout);
        midPanel.add(infoPanel, panelConstrains);

        constraints.weighty = 1;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 5;

        JLabel profilePicture = new JLabel("Image", SwingConstants.CENTER);
        profilePicture.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        profilePicture.setPreferredSize(new Dimension(100, 100));
        profilePicture.setBackground(Color.yellow);
        profilePicture.setOpaque(true);


        constraints.anchor = GridBagConstraints.CENTER;
        imagePanel.add(profilePicture, constraints);

        uploadButton = new JButton("Upload");

        constraints.gridwidth = 5;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        imagePanel.add(uploadButton, constraints);
        uploadButton.setEnabled(false);

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                imageUploader.openFile();
            }
        });



        numberFormat = NumberFormat.getIntegerInstance();
        NumberFormatter integerFormatter = new NumberFormatter(numberFormat);
        integerFormatter.setValueClass(Integer.class);
        integerFormatter.setAllowsInvalid(false);
        integerFormatter.setCommitsOnValidEdit(true);

        JLabel nickLabel = new JLabel("Username");
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        infoPanel.add(nickLabel, constraints);

        profileUserNameField = new JTextField(20);
        profileUserNameField.setEnabled(false);
        constraints.gridwidth = 3;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;

        infoPanel.add(profileUserNameField, constraints);


        JLabel passLabel = new JLabel("Password");

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;

        infoPanel.add(passLabel, constraints);


        profilePasswordField = new JPasswordField(20);
        profilePasswordField.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;

        infoPanel.add(profilePasswordField, constraints);


        JLabel nationalityLabel = new JLabel("Nationality");

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;

        infoPanel.add(nationalityLabel, constraints);


        profileEditNationalityField = new JTextField(20);
        profileEditNationalityField.setEnabled(false);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;

        infoPanel.add(profileEditNationalityField, constraints);

        JLabel ageLabel = new JLabel("Age");

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;

        infoPanel.add(ageLabel, constraints);


        profileEditAgeField = new JFormattedTextField(numberFormat);
        profileEditAgeField.setEnabled(false);

        profileEditAgeField.setColumns(20);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;

        infoPanel.add(profileEditAgeField, constraints);

        profileEditDisplayError = new JLabel("Error", SwingConstants.CENTER);
        profileEditDisplayError.setForeground(Color.red);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(profileEditDisplayError, constraints);



        JButton profileEditBackButton = new JButton("Back");
        profileEditBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!editStatus){
//                    controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_BACK));
                }else{
                    toggleEditButtons(true);

                    profileEditBUtton.setEnabled(editStatus);
                    uploadButton.setEnabled(false);
                    editStatus = !editStatus;
                }


            }
        });

        southPanel.add(profileEditBackButton);

        profileEditBUtton = new JButton("Edit");

        profileEditBUtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                toggleEditButtons(editStatus);

                profileEditBUtton.setEnabled(editStatus);
                profileEditSubmitButton.setEnabled(!editStatus);
                uploadButton.setEnabled(true);

                editStatus = !editStatus;

            }
        });

        southPanel.add(profileEditBUtton);


        profileEditSubmitButton = new JButton("Submit");
        profileEditSubmitButton.setEnabled(false);
        profileEditSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(editStatus){

                    toggleEditButtons(true);
                    profileEditBUtton.setEnabled(true);
                    profileEditSubmitButton.setEnabled(false);
                    uploadButton.setEnabled(false);
                    //TODO tell controller to send new user profile data to server
                }
                    editStatus = !editStatus;
            }
        });

        southPanel.add(profileEditSubmitButton);

        frame.add(profileViewPanel);
    }

    public void toggleEditButtons(boolean editStatus){



        profileUserNameField.setEnabled(!editStatus);
        profilePasswordField.setEnabled(!editStatus);
        profileEditNationalityField.setEnabled(!editStatus);
        profileEditAgeField.setEnabled(!editStatus);
    }
}
