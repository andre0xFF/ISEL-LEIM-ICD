package pt.isel.icd.user.management;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

public class UserClientView {
    
    private final UserClientController userClientController;
    private JFrame frame;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel notificationLabel;
    
    
    
    JPanel startPanel;
    JPanel menuPanel;
    
    JPanel profilePanel;
    
    JPanel statsPanel;
    
    JPanel gamePanel;
    
    private JButton uploadButton;
    
    JTextField profileUserNameField;
    
    JPasswordField profilePasswordField;
    
    JTextField profileEditNationalityField;
    
    JFormattedTextField profileEditAgeField;
    
    JLabel notificationProfileLabel;
    private JButton profileEditBUtton;
    
    boolean editStatus = false;
    
    NumberFormat numberFormat;
    private JButton profileSubmitButton;
    
    JLabel p1ScoreVal;
    
    JLabel p1TimerVal;
    
    JLabel p1Img;
    JLabel p1Name;
    
    JLabel p2Img;
    JLabel p2Name;
    JLabel p2ScoreVal;
    JLabel p2TimerVal;
    
    private int rows = 10;
    private int cols = 10;

    public UserClientView(JFrame frame, UserClientController userClientController){
        
        this.frame = frame;
        this.userClientController = userClientController;
        
        createStartPanel();
        createMenuPanel();
        createProfilePanel();
        createStatsPanel();
        createGamePanel(rows, cols);
        
        
    }

    private void createGamePanel(int rows, int cols) {
        JPanel boardPanel = new JPanel(new GridLayout(rows, cols));
        
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        
        JPanel topContainer = new JPanel(new GridLayout(2,1));
        
        
        
        JPanel topPanel = new JPanel(new GridLayout(1,2));
        
        topContainer.add(topPanel);
        addBoardTitle(topContainer);
        
        JPanel p1Info = new JPanel(bagLayout);
        p1Info.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    
        JPanel p2Info = new JPanel(bagLayout);
        p2Info.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        topPanel.add(p1Info);
    
        topPanel.add(p2Info);
        
        
        addP1Photo(p1Info, constraints);
        addP1Name(p1Info, constraints);
        addP1Score(p1Info, constraints);
        addP1Timer(p1Info, constraints);
        
        addP2Photo(p2Info, constraints);
        addP2Name(p2Info, constraints);
        addP2Score(p2Info, constraints);
        addP2Timer(p2Info, constraints);
//        addPlayerLabels(topContainer);
        
    }

    private void addP2Timer(JPanel p2Info, GridBagConstraints constraints) {
        JLabel p2Timer = new JLabel("Timer", SwingConstants.LEFT);
       
        constraints.gridx = 6;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.fill=1;
        p2Info.add(p2Timer, constraints);
    
        p2TimerVal = new JLabel("0", SwingConstants.CENTER);
        constraints.gridx = 5;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        p2Info.add(p2TimerVal, constraints);
        
    }

    private void addP2Score(JPanel p2Info, GridBagConstraints constraints) {
        JLabel p2Score = new JLabel("Score");
       

        constraints.gridx = 5;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
    
        p2Info.add(p2Score, constraints);
    
    
        p2ScoreVal = new JLabel("0");
    
    
        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        p2Info.add(p2ScoreVal, constraints);
        
    }

    private void addP2Name(JPanel p2Info, GridBagConstraints constraints) {
        p2Name = new JLabel("PLAYER 2");
       
        
        constraints.gridx = 6;
        constraints.gridy = 1;
    
        p2Info.add(p2Name, constraints);
        
    }

    private void addP2Photo(JPanel p2Info, GridBagConstraints constraints) {
        p2Img = new JLabel("PHOTO");
        p2Img.setPreferredSize(new Dimension(50,50));
        p2Img.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        constraints.fill = 0;
        constraints.gridx = 8;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        p2Info.add(p2Img, constraints);
    }

    private void addP1Timer(JPanel p1Info, GridBagConstraints constraints) {
        JLabel p1Timer = new JLabel("Timer", SwingConstants.RIGHT);
        constraints.fill = 1;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        p1Info.add(p1Timer, constraints);
        
        p1TimerVal = new JLabel("0", SwingConstants.CENTER);
        constraints.gridx = 4;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        p1Info.add(p1TimerVal, constraints);
        
    }

    private void addP1Score(JPanel p1Info, GridBagConstraints constraints) {
        JLabel p1Score = new JLabel("Score");
      
        
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        
        p1Info.add(p1Score, constraints);
        
        p1ScoreVal = new JLabel("0");
      
        
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        p1Info.add(p1ScoreVal, constraints);
    }

    private void addP1Name(JPanel p1Info, GridBagConstraints constraints) {
        p1Name = new JLabel("PLAYER 1",  SwingConstants.CENTER);
        
        constraints.gridx = 2;
        constraints.gridy = 1;
        
        p1Info.add(p1Name, constraints);
    }

    private void addP1Photo(JPanel p1Info, GridBagConstraints constraints) {
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridwidth = 10;
        
        JLabel p1Img = new JLabel("PHOTO");
        p1Img.setPreferredSize(new Dimension(50,50));
        p1Img.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        constraints.weightx = 1;
        p1Info.add(p1Img, constraints);
        
    }

    private void addBoardTitle(JPanel topContainer) {
        topContainer.add(new JLabel("Game Board", SwingConstants.CENTER));
        
    }

    private void createStatsPanel() {
        
        statsPanel.setLayout(new GridLayout(0 ,3));
        
        JPanel historyPanel = new JPanel(new BorderLayout());
        JPanel navStatsPanel = new JPanel(new GridLayout(2, 0));
        historyPanel.add(navStatsPanel, BorderLayout.SOUTH);
        
        
        
        
        
        //statsInfoPanel = center container
        JPanel centerContainer = new JPanel();
        BoxLayout boxLayout = new BoxLayout(centerContainer, BoxLayout.Y_AXIS);
        centerContainer.setLayout(boxLayout);
        
        historyPanel.add(centerContainer, BorderLayout.CENTER);
        
        centerContainer.add(statsPanel, BorderLayout.CENTER);
        
        
        addStatsTittle(historyPanel);
        addStatsGameLabel(statsPanel);
        addStatsResultLabel(statsPanel);
        addStatsGameTimeLabel(statsPanel);
       
        
        addStatsNotificationLabel(navStatsPanel);
        addStatsBackButton(navStatsPanel);
        
        
    }

    private void addStatsGameTimeLabel(JPanel statsPanel) {
        JLabel gameStatsGameTime = new JLabel("TIME");
        gameStatsGameTime.setHorizontalAlignment(JLabel.CENTER);
        statsPanel.add(gameStatsGameTime);
    }

    private void addStatsResultLabel(JPanel statsPanel) {
        JLabel gameStatsGameResult = new JLabel("RESULT");
        gameStatsGameResult.setHorizontalAlignment(JLabel.CENTER);
        statsPanel.add(gameStatsGameResult);
    }

    private void addStatsGameLabel(JPanel statsPanel) {
        JLabel gameStatsGameLabel = new JLabel("GAME");
        gameStatsGameLabel.setHorizontalAlignment(JLabel.CENTER);
        statsPanel.add(gameStatsGameLabel);
    }

    private void addStatsBackButton(JPanel navStatsPanel) {
        JButton statsBackButton = new JButton("Back");
        navStatsPanel.add(statsBackButton);
    }

    private void addStatsNotificationLabel(JPanel navStatsPanel) {
        
        JLabel gameStatsDisplayError = new JLabel("Error");
        gameStatsDisplayError.setHorizontalAlignment(JLabel.CENTER);
        navStatsPanel.add(gameStatsDisplayError);
    }
    
    public void addGameStat(String gameID) {
        JLabel gameStat = new JLabel(gameID);
        gameStat.setHorizontalAlignment(JLabel.CENTER);
        this.statsPanel.add(gameStat);
    }

    private void addStatsInfo(JPanel southStatsPanel) {
        
        
        
        
    }

    private void addStatsTittle(JPanel historyPanel) {
        JLabel gameStatsTitleLabel = new JLabel("Game");
        gameStatsTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        historyPanel.add(gameStatsTitleLabel, BorderLayout.NORTH);
    }

    private void createProfilePanel() {
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        
        profilePanel.setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        
        
        JPanel midPanel = new JPanel();
        
        constraints.gridx = 0;
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(bagLayout);
        midPanel.add(imagePanel, constraints);
        
        constraints.gridy = 1;
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(bagLayout);
        midPanel.add(infoPanel, constraints);
        
        
        
        
        JPanel southPanel = new JPanel();
        
        addProfileTitle(topPanel);
        addProfileImg(imagePanel, constraints);
        addUploadButton(imagePanel, constraints);
        addProfileUsernameField(infoPanel, constraints);
        addProfilePasswordField(infoPanel, constraints);
        addProfileNatField(infoPanel, constraints);
        addProfileAgeField(infoPanel, constraints);
        addProfileNotificationLabel(infoPanel, constraints);
        addProfileBackButton(southPanel);
        addProfileEditButton(southPanel);
        addProfileSubmitButton(southPanel);
        
        profilePanel.add(topPanel, BorderLayout.NORTH);
        
        profilePanel.setLayout(new BorderLayout());
    }

    private void addProfileSubmitButton(JPanel southPanel) {
        profileSubmitButton = new JButton("Submit");
        profileSubmitButton.addActionListener(this::handleProfileSubmit);
        profileSubmitButton.setEnabled(false);
        
        southPanel.add(profileSubmitButton);
        
    }

    private void addProfileEditButton(JPanel southPanel) {
        profileEditBUtton = new JButton("Edit");
        profileEditBUtton.addActionListener(this::handleProfileEdit);
        
        southPanel.add(profileEditBUtton);
        
    }

    private void addProfileBackButton(JPanel southPanel) {
        JButton profileEditBackButton = new JButton("Back");
        profileEditBackButton.addActionListener(this::handleProfileBack);
        
        southPanel.add(profileEditBackButton);
        
    }

    private void addProfileNotificationLabel(JPanel infoPanel, GridBagConstraints constraints) {
        notificationProfileLabel = new JLabel("Error", SwingConstants.CENTER);
        notificationProfileLabel.setForeground(Color.red);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(notificationProfileLabel, constraints);
        
    }

    private void addProfileAgeField(JPanel infoPanel, GridBagConstraints constraints) {
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
        
    }

    private void addProfileNatField(JPanel infoPanel, GridBagConstraints constraints) {
        
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
        
    }

    private void addProfilePasswordField(JPanel infoPanel, GridBagConstraints constraints) {
        
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
        
    }

    private void addUploadButton(JPanel imagePanel, GridBagConstraints constraints) {
        uploadButton = new JButton("Upload");

        constraints.gridwidth = 5;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        imagePanel.add(uploadButton, constraints);
        uploadButton.setEnabled(false);
        
        uploadButton.addActionListener(this::handleUploadImgAction);
        
    }

    private void addProfileUsernameField(JPanel infoPanel, GridBagConstraints constraints) {
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
        
        
    }

    private void addProfileImg(JPanel imagePanel, GridBagConstraints constraints) {
        
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
    }

    private void addProfileTitle(JPanel topPanel) {
        
        topPanel.add(new JLabel("Profile"));
        profilePanel.add(topPanel, BorderLayout.NORTH);
    }

    private void createMenuPanel() {
        menuPanel = new JPanel();
        
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        
        JPanel titlePanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(bagLayout);
        
        menuPanel.setLayout(new BorderLayout());
        
        addMenuTitle(titlePanel);
        addNewGameButton(buttonsPanel, constraints);
        addProfileButton(buttonsPanel, constraints);
        addStatsButton(buttonsPanel, constraints);
        addExitButton(buttonsPanel, constraints);
        
        
        menuPanel.add(titlePanel, BorderLayout.NORTH);
        menuPanel.add(buttonsPanel, BorderLayout.CENTER);
        
    }

    private void addExitButton(JPanel buttonsPanel, GridBagConstraints constraints) {
        constraints.gridy = 3;

        JButton gameQuitButton = new JButton("QUIT GAME");
        gameQuitButton.setPreferredSize(new Dimension(150, 30));
        gameQuitButton.addActionListener(this::handleExitAction);
        
        buttonsPanel.add(gameQuitButton, constraints);
        
    }
    

    private void addStatsButton(JPanel buttonsPanel, GridBagConstraints constraints) {
        
        constraints.gridy = 2;

        JButton menuGameHistoryButton = new JButton("GAME STATS");
        menuGameHistoryButton.setPreferredSize(new Dimension(150, 30));
        menuGameHistoryButton.addActionListener(this::handleStatsAction);
        
        buttonsPanel.add(menuGameHistoryButton, constraints);
        
        
        
    }

  

    private void addProfileButton(JPanel buttonsPanel, GridBagConstraints constraints) {
        constraints.gridy = 1;
        JButton menuProfileJButton = new JButton("PROFILE");
        menuProfileJButton.setPreferredSize(new Dimension(150, 30));
        menuProfileJButton.addActionListener(this::handleProfileAction);
        
        buttonsPanel.add(menuProfileJButton, constraints);
    }

    private void addNewGameButton(JPanel buttonsPanel, GridBagConstraints constraints) {
        
       
        
        
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        
        JButton menuNewGameButton = new JButton("NEW GAME");
        
        menuNewGameButton.setPreferredSize(new Dimension(150, 30));
        menuNewGameButton.addActionListener(this::handleNewGameAction);
        
        buttonsPanel.add(menuNewGameButton, constraints);
    }

    private void addMenuTitle(JPanel titlePanel) {
        
        JLabel menuTitleLabel = new JLabel("MENU");
        
        menuTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        titlePanel.add(menuTitleLabel);
    }

    private void createStartPanel() {
        
        startPanel = new JPanel();
        startPanel.setLayout(new GridBagLayout());
    
        GridBagConstraints constraints = new GridBagConstraints();
    
        addUsernameField(startPanel, constraints);
        addPasswordField(startPanel, constraints);
        addLoginButton(startPanel, constraints);
        addSignUpButton(startPanel, constraints);
        addNotificationLabel(startPanel, constraints);
        
        
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


    private void addSignUpButton(JPanel startPanel, GridBagConstraints constraints) {
        JButton startSignUpButton = new JButton("Sign Up");

        startSignUpButton.addActionListener(this::handleSignUpAction);
        startSignUpButton.setPreferredSize(new Dimension(100, 40));
    
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
    
        startPanel.add(startSignUpButton, constraints);
        
    }

    private void addLoginButton(JPanel startPanel, GridBagConstraints constraints) {
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(this::handleLoginAction);
        loginButton.setPreferredSize(new Dimension(100, 40));
    
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.top = 10;
    
        startPanel.add(loginButton, constraints);
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
    
    private void handleLoginAction(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // try {
        //     userClientController.authenticate(new User(username, password));
        // } catch (JsonProcessingException ex) {
        //     notificationLabel.setText("Error authenticating user");
        // }
    }
    
    private void handleSignUpAction(ActionEvent e) {
        //TODO: inform controller to change current panel to SignUp JPanel
    }
    
    private void handleNewGameAction(ActionEvent e){
        //TODO: inform controller to change current panel to new game JPanel
    }
    
    private void handleProfileAction(ActionEvent e){
        //TODO: inform controller to change current panel to profile JPanel
    }
    
    private void handleStatsAction(ActionEvent actionEvent) {
        //TODO: inform controller to change current panel to Game States JPanel
    }
    
    private void handleExitAction(ActionEvent actionEvent) {
        //TODO: inform controller to terminate game
    }
    
    private void handleUploadImgAction(ActionEvent actionEvent){
        //TODO: upload image to profile
    }
    
    private void handleProfileSubmit(ActionEvent actionEvent){
        //TODO: inform controller to submit new profile data
    }
    
    private void handleProfileBack(ActionEvent actionEvent){
        
        if(!editStatus){
//          controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_BACK));
        }else{
            toggleEditButtons(true);

            profileEditBUtton.setEnabled(editStatus);
            uploadButton.setEnabled(false);
            editStatus = !editStatus;
        }
        
    }
    
    private void handleProfileEdit(ActionEvent actionEvent){
        toggleEditButtons(editStatus);

        profileEditBUtton.setEnabled(editStatus);
        profileSubmitButton.setEnabled(!editStatus);
        uploadButton.setEnabled(true);
        
        editStatus = !editStatus;
    }
    
    
    public void toggleEditButtons(boolean editStatus){



        profileUserNameField.setEnabled(!editStatus);
        profilePasswordField.setEnabled(!editStatus);
        profileEditNationalityField.setEnabled(!editStatus);
        profileEditAgeField.setEnabled(!editStatus);
    }

}
