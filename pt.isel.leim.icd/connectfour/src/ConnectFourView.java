import models.Board;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.NumberFormat;

public class ConnectFourView {

    private static final int BUTTON_SIZE = 60;
    private JFrame frame;
    private JPanel authenticationPanel;
    private JTextField authenticationUsernameField;
    private JPasswordField authenticationPasswordField;
    private JTextField profileUserNameField;
    private JPasswordField profilePasswordField;

    // Login
    private JButton logInBackButton;
    private JButton loginButton;
    private JPanel boardPanel;
    private JButton[][] boardTokenCells;
    private JLabel currentPlayerLabel;
    private JPanel profileEditPanel;

    // Profile Display
    private JPanel profileDisplayPanel;
    private JLabel profileDisplayImageLabel;
    private JLabel profileDisplayUserNameLabel;
    private JLabel profileDisplayPasswordLabel;
    private JLabel profileDisplayNationalityLabel;
    private JLabel profileDisplayAgeLabel;
    private JButton profileDisplayBackButton;
    private JButton profileDisplayEditButton;

    // Profile Edit
    private JTextField profileEditNationalityField;
    private JTextField profileEditAgeField;
    private JButton profileEditBackButton;
    private JButton profileEditSubmitButton;
    private JPanel gameHistoryPanel;
    private JPanel gameMenuPanel;
    private JPanel startingMenuPanel;

    private JLabel displayError;
    private JLabel profilePicture;

    private JTextField profileEditPictureField;


    // Starting Menu
    private JButton startSignUpButton;
    private JButton startLoginButton;


    // Sign Up
    private JPanel signUpPanel;
    private JTextField signUserNameField;
    private JPasswordField signPasswordField;
    private JTextField signNationalityField;
    private JTextField signAgeField;
    private JButton signSubmitButton;
    private JTextField signUpPictureField;
    private JButton signBackButton;

    // Game Queue
    private JPanel queuePanel;
    private JButton queueCancelButton;


    private int signPictureVal;

    // Game Stats
    private JButton historyBackButton;
    private JPanel gameStatsPanel;

    // Main Menu
    private JButton menuNewGameButton;
    private JButton menuProfileJButton;
    private JButton menuGameHistoryButton;
    private JButton gameQuitButton;

    private final int rows;
    private final int columns;

    // Game Over
    private JPanel gameOverPanel;
    private JButton gameOverExitButton;
    private JLabel gameOverMessageLabel;

    private JButton gameBoardBackButton;
    private ActionListener listener;
    private JLabel profileEditDisplayError;
    private JLabel profileDisplayError;
    private JLabel signUpDisplayError;
    private JLabel authenticationDisplayError;
    private JLabel gameStatsDisplayError;
    private JLabel boardErrorDisplay;

    private NumberFormat numberFormat;


    private JFormattedTextField intFieldTest;


    public ConnectFourView() {
        int rows = Board.ROWS;
        int columns = Board.COLUMNS;

        createFrame(rows, columns);

        createAuthenticationPanel();
        createBoardPanel(rows, columns);
        createEditProfilePanel();
        createGameHistoryPanel();
        createGameMenuPanel();
        createStartingMenuPanel();
        createSignUpPanel();
        createGameOverPanel();
        createDispProfilePanel();
        createQueuePanel();


        numberFormat = NumberFormat.getIntegerInstance();
        NumberFormatter integerFormatter = new NumberFormatter(numberFormat);
        integerFormatter.setValueClass(Integer.class);
        integerFormatter.setAllowsInvalid(false);
        integerFormatter.setCommitsOnValidEdit(true);


//        this.frame.add(authenticationPanel, BorderLayout.CENTER);
//        this.frame.add(profilePanel, BorderLayout.CENTER);
//         this.frame.add(gameHistoryPanel, BorderLayout.CENTER);
        this.frame.add(gameMenuPanel, BorderLayout.CENTER);
//        this.frame.add(startingMenuPanel, BorderLayout.CENTER);
//        this.frame.add(gameOverPanel, BorderLayout.CENTER);
//        this.frame.add(queuePanel, BorderLayout.CENTER);
//        this.frame.add(boardPanel, BorderLayout.CENTER);

        this.frame.setVisible(true);

        this.rows = rows;
        this.columns = columns;
    }

    private void createQueuePanel() {
        JPanel gameQueuePanel = new JPanel(new BorderLayout());
        JLabel gameQueueLabel = new JLabel("Searching for Opponent");

        queueCancelButton = new JButton("Cancel Search");
        gameQueueLabel.setHorizontalAlignment(JLabel.CENTER);
        gameQueuePanel.add(gameQueueLabel, BorderLayout.CENTER);
        gameQueuePanel.add(queueCancelButton, BorderLayout.SOUTH);

        this.queuePanel = gameQueuePanel;
    }

    private void createDispProfilePanel() {
        JPanel profileDisplayPanel = new JPanel();

        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();


        profileDisplayPanel.setLayout(bagLayout);

        JLabel titleLabel = new JLabel("Profile");

        titleLabel.setOpaque(true);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 1;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 3;

        profileDisplayPanel.add(titleLabel, constraints);

        constraints.insets.top = 10;

        profileDisplayImageLabel = new JLabel();

        constraints.gridx = 0;
        constraints.gridy = 1;
        profileDisplayImageLabel.setBackground(Color.red);
        profileDisplayImageLabel.setOpaque(true);
        profileDisplayImageLabel.setPreferredSize(new Dimension(60, 60));

        profileDisplayPanel.add(profileDisplayImageLabel, constraints);
        constraints.gridwidth = 1;

        JLabel nickLabel = new JLabel("Username");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;

        profileDisplayPanel.add(nickLabel, constraints);


        profileDisplayUserNameLabel = new JLabel();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;

        profileDisplayPanel.add(profileDisplayUserNameLabel, constraints);


        JLabel nationalityLabel = new JLabel("Nationality");

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;

        profileDisplayPanel.add(nationalityLabel, constraints);


        profileDisplayNationalityLabel = new JLabel();

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;

        profileDisplayPanel.add(profileDisplayNationalityLabel, constraints);

        JLabel ageLabel = new JLabel("Age");

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.WEST;

        profileDisplayPanel.add(ageLabel, constraints);


        profileDisplayAgeLabel = new JLabel();

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;

        profileDisplayPanel.add(profileDisplayAgeLabel, constraints);


        profileDisplayBackButton = new JButton("Back");

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.WEST;

        profileDisplayPanel.add(profileDisplayBackButton, constraints);


        profileDisplayEditButton = new JButton("Edit");

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.EAST;

        profileDisplayPanel.add(profileDisplayEditButton, constraints);

        profileDisplayError = new JLabel("Error");


        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        profileDisplayPanel.add(profileDisplayError, constraints);


        this.profileDisplayPanel = profileDisplayPanel;
    }

    private void createGameOverPanel() {
        JPanel gameOverPanel = new JPanel(new BorderLayout());
        gameOverMessageLabel = new JLabel("Game Over");
        gameOverMessageLabel.setHorizontalAlignment(JLabel.CENTER);
        gameOverExitButton = new JButton("Exit");

        gameOverPanel.add(gameOverMessageLabel, BorderLayout.CENTER);
        gameOverPanel.add(gameOverExitButton, BorderLayout.SOUTH);

        this.gameOverPanel = gameOverPanel;
    }

    private void createSignUpPanel() {
        JPanel signUpPanel = new JPanel();

        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();


        signUpPanel.setLayout(bagLayout);

        JLabel titleLabel = new JLabel("Sign Up");


        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 1;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.insets.bottom = 10;

        signUpPanel.add(titleLabel, constraints);


        JLabel signUpPictureLabel = new JLabel("Image");

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;


        signUpPanel.add(signUpPictureLabel, constraints);

        signUpPictureField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;

        signUpPanel.add(signUpPictureField, constraints);


        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;

        signUpPanel.add(new JLabel("Username"), constraints);


        signUserNameField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;

        signUpPanel.add(signUserNameField, constraints);


        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;

        signUpPanel.add(new JLabel("Password"), constraints);


        signPasswordField = new JPasswordField(10);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;

        signUpPanel.add(signPasswordField, constraints);


        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.WEST;

        signUpPanel.add(new JLabel("Nationality"), constraints);


        signNationalityField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;

        signUpPanel.add(signNationalityField, constraints);


        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.WEST;

        signUpPanel.add(new JLabel("Age"), constraints);


        signAgeField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.EAST;

        signUpPanel.add(signAgeField, constraints);

        signSubmitButton = new JButton("Sign Up");

        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.EAST;

        signUpPanel.add(signSubmitButton, constraints);

        signBackButton = new JButton("Back");
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.WEST;

        signUpPanel.add(signBackButton, constraints);

        signUpDisplayError = new JLabel("Error");

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        signUpPanel.add(signUpDisplayError, constraints);


        this.signUpPanel = signUpPanel;

    }

    private void createStartingMenuPanel() {

        JPanel startMenuPanel = new JPanel();

        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        startMenuPanel.setLayout(bagLayout);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.top = 10;


        startLoginButton = new JButton("Log In");

        startMenuPanel.add(startLoginButton, constraints);


        startLoginButton.setPreferredSize(new Dimension(100, 40));

        startSignUpButton = new JButton("Sign Up");

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.top = 10;

        startMenuPanel.add(startSignUpButton, constraints);
        startSignUpButton.setPreferredSize(new Dimension(100, 40));


        this.startingMenuPanel = startMenuPanel;

    }


    /**
     * Sets the action listener for the buttons
     *
     * @param listener the action listener
     */
    public void setActionListener(ActionListener listener) {
        this.listener = listener;

        // GameBoard Buttons
        this.gameBoardBackButton.addActionListener(listener);

        // Queue Buttons
        this.queueCancelButton.addActionListener(listener);

        // Game Over Buttons
        this.gameOverExitButton.addActionListener(listener);

        // Sign Up Buttons
        signSubmitButton.addActionListener(listener);
        signBackButton.addActionListener(listener);

        // Start Menu Buttons
        this.startSignUpButton.addActionListener(listener);
        this.startLoginButton.addActionListener(listener);

        // Login Menu Buttons
        this.loginButton.addActionListener(listener);
        this.logInBackButton.addActionListener(listener);

        this.gameQuitButton.addActionListener(listener);
        this.menuNewGameButton.addActionListener(listener);
        this.menuProfileJButton.addActionListener(listener);
        this.menuGameHistoryButton.addActionListener(listener);
        this.gameQuitButton.addActionListener(listener);

        //Profile Buttons
        this.profileEditBackButton.addActionListener(listener);
        this.profileEditSubmitButton.addActionListener(listener);

        this.profileDisplayBackButton.addActionListener(listener);
        this.profileDisplayEditButton.addActionListener(listener);

        //Game History Buttons
        this.historyBackButton.addActionListener(listener);


        for (int column = 0; column < this.boardTokenCells[0].length; column++) {
            for (JButton[] boardTokenCell : this.boardTokenCells) {
                boardTokenCell[column].addActionListener(listener);
            }
        }
    }

    private void createFrame(int rows, int columns) {
        frame = new JFrame("Connect Four");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(BUTTON_SIZE * columns, (BUTTON_SIZE * rows) + 40));
    }

    private void createAuthenticationPanel() {
        JPanel authenticationPanel = new JPanel();

        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        authenticationPanel.setLayout(bagLayout);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets.top = 10;

        JLabel usernameLabel = new JLabel("Username: ");

        authenticationPanel.add(usernameLabel, constraints);

        authenticationUsernameField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets.top = 10;

        authenticationPanel.add(authenticationUsernameField, constraints);

        JLabel passwordLabel = new JLabel("Password: ");

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets.bottom = 10;
        authenticationPanel.add(passwordLabel, constraints);

        authenticationPasswordField = new JPasswordField(10);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets.bottom = 10;
        authenticationPanel.add(authenticationPasswordField, constraints);

        loginButton = new JButton("Login");

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        authenticationPanel.add(loginButton, constraints);


        this.authenticationPanel = authenticationPanel;


        logInBackButton = new JButton("Back");

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;

        authenticationPanel.add(logInBackButton, constraints);

        authenticationDisplayError = new JLabel("Error");


        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.SOUTH;

        authenticationPanel.add(authenticationDisplayError, constraints);

        authenticationPanel.setOpaque(true);
    }

    private void createGameMenuPanel() {
        JPanel gameMenuPanel = new JPanel();
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        gameMenuPanel.setLayout(bagLayout);

        JLabel menuTitleLabel = new JLabel("MENU", JLabel.CENTER);

        constraints.anchor = GridBagConstraints.NORTH;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.weighty = 1;


        gameMenuPanel.add(menuTitleLabel, constraints);

        constraints.weighty = 0;


        menuNewGameButton = new JButton("NEW GAME");

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets.top = 5;

        gameMenuPanel.add(menuNewGameButton, constraints);

        menuProfileJButton = new JButton("PROFILE");

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;


        gameMenuPanel.add(menuProfileJButton, constraints);

        menuGameHistoryButton = new JButton("GAME STATS");


        constraints.gridx = 0;
        constraints.gridy = 3;


        gameMenuPanel.add(menuGameHistoryButton, constraints);


        gameQuitButton = new JButton("QUIT GAME");

        constraints.gridx = 0;
        constraints.gridy = 4;

        constraints.insets.top = 20;

        gameMenuPanel.add(gameQuitButton, constraints);


        this.gameMenuPanel = gameMenuPanel;

    }


    private void createBoardPanel(int rows, int columns) {

        JPanel container = new JPanel(new BorderLayout());
        JPanel boardPanel = new JPanel(new GridLayout(rows, columns));

        this.boardTokenCells = new JButton[rows][columns];

        for (int row = rows - 1; row >= 0; row--) {
            for (int column = 0; column < columns; column++) {
                JButton button = new JButton();

                button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                button.setBackground(Color.WHITE);
                button.setOpaque(true);
                button.setEnabled(true);

                boardTokenCells[row][column] = button;

                boardPanel.add(button);
            }
        }
        container.add(boardPanel, BorderLayout.CENTER);


        JPanel playerOneInfoContainer = new JPanel(new BorderLayout());
        playerOneInfoContainer.setPreferredSize(new Dimension(60, 60));

        JPanel playerOneGridInfo = new JPanel();


        playerOneInfoContainer.add(playerOneGridInfo, BorderLayout.CENTER);

        JLabel imageLabelOne = new JLabel("PlaceHolder");
        imageLabelOne.setPreferredSize(new Dimension(60, 60));
        playerOneGridInfo.add(imageLabelOne);
        playerOneGridInfo.add(new JLabel("Player 1"));

        //Right side
        JPanel playerTwoInfoContainer = new JPanel(new BorderLayout());
        playerTwoInfoContainer.setPreferredSize(new Dimension(60, 60));

        JPanel playerTwoGridInfo = new JPanel();

        playerTwoInfoContainer.add(playerTwoGridInfo, BorderLayout.CENTER);

        JLabel imageLabelTwo = new JLabel("PlaceHolder");
        imageLabelTwo.setPreferredSize(new Dimension(60, 60));
        playerTwoGridInfo.add(imageLabelTwo);
        playerTwoGridInfo.add(new JLabel("Player 2"));


        JPanel southContainer = new JPanel(new GridLayout(2, 1));
        gameBoardBackButton = new JButton("Surrender");
        southContainer.add(gameBoardBackButton);


        boardErrorDisplay = new JLabel("Error");
        boardErrorDisplay.setHorizontalAlignment(JLabel.CENTER);

        southContainer.add(boardErrorDisplay);

        container.add(playerOneInfoContainer, BorderLayout.WEST);
        container.add(playerTwoInfoContainer, BorderLayout.EAST);

        container.add(southContainer, BorderLayout.SOUTH);

        JLabel boardTitleLabel = new JLabel("Game Board");
        boardTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        container.add(boardTitleLabel, BorderLayout.NORTH);

        this.boardPanel = container;
    }

    public void createControlPanel(String currentPlayerName) {
        JPanel controlPanel = new JPanel(new FlowLayout());
        currentPlayerLabel = new JLabel(currentPlayerName);

        controlPanel.add(currentPlayerLabel);
        controlPanel.setBackground(Color.RED);
        controlPanel.setOpaque(true);
        this.frame.add(controlPanel, BorderLayout.SOUTH);
    }

    public void addGameStat(String gameID) {
        JLabel gameStat = new JLabel(gameID);
        gameStat.setHorizontalAlignment(JLabel.CENTER);
        this.gameStatsPanel.add(gameStat);
    }


    public void createGameHistoryPanel() {


        JPanel gameHistoryPanel = new JPanel(new BorderLayout());

        JLabel gameStatsTitleLabel = new JLabel("Game Stats");
        gameStatsTitleLabel.setHorizontalAlignment(JLabel.CENTER);

        gameHistoryPanel.add(gameStatsTitleLabel, BorderLayout.NORTH);
        JPanel southContainer = new JPanel(new GridLayout(2, 0));
        gameStatsDisplayError = new JLabel("Error");
        gameStatsDisplayError.setHorizontalAlignment(JLabel.CENTER);
        southContainer.add(gameStatsDisplayError);

        historyBackButton = new JButton("Back");
        southContainer.add(historyBackButton);

        gameHistoryPanel.add(southContainer, BorderLayout.SOUTH);


        JPanel centerContainer = new JPanel();
        BoxLayout boxLayout = new BoxLayout(centerContainer, BoxLayout.Y_AXIS);

        centerContainer.setLayout(boxLayout);

        gameHistoryPanel.add(centerContainer, BorderLayout.CENTER);


        gameStatsPanel = new JPanel(new GridLayout(0, 3));

        centerContainer.add(gameStatsPanel, BorderLayout.CENTER);

        JLabel gameStatsGameLabel = new JLabel("GAME");
        gameStatsGameLabel.setHorizontalAlignment(JLabel.CENTER);
        gameStatsPanel.add(gameStatsGameLabel);


        JLabel gameStatsGameResult = new JLabel("RESULT");
        gameStatsGameResult.setHorizontalAlignment(JLabel.CENTER);
        gameStatsPanel.add(gameStatsGameResult);

        JLabel gameStatsGameTime = new JLabel("TIME");
        gameStatsGameTime.setHorizontalAlignment(JLabel.CENTER);
        gameStatsPanel.add(gameStatsGameTime);

//        JScrollPane  scroll = new JScrollPane(gameStatsPanel);
//
//        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

//        gameStatsPanel.setPreferredSize(new Dimension(200, 200));
//        scroll.setPreferredSize(new Dimension(scroll.getPreferredSize().width,
//                scroll.getComponents().length * scroll.getComponents()[0].getPreferredSize().height));

//        gameStatsPanel.add(scroll);

        for (int i = 0; i < 80; i++) {
            addGameStat("Test");
        }


        this.gameHistoryPanel = gameHistoryPanel;


    }

    public void createEditProfilePanel() {
        //TODO receives Image
        JPanel profilePanel = new JPanel();

        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();


        profilePanel.setLayout(bagLayout);

        JLabel titleLabel = new JLabel("Profile");

        titleLabel.setOpaque(true);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 1;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 3;

        profilePanel.add(titleLabel, constraints);

        constraints.insets.top = 10;

        profilePicture = new JLabel("Image");

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(profilePicture, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;

        profileEditPictureField = new JTextField(10);

        profilePanel.add(profileEditPictureField, constraints);
        constraints.gridwidth = 1;

        JLabel nickLabel = new JLabel("Username");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(nickLabel, constraints);


        profileUserNameField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profileUserNameField, constraints);


        JLabel passLabel = new JLabel("Password");

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(passLabel, constraints);


        profilePasswordField = new JPasswordField(10);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profilePasswordField, constraints);


        JLabel nationalityLabel = new JLabel("Nationality");

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(nationalityLabel, constraints);


        profileEditNationalityField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profileEditNationalityField, constraints);

        JLabel ageLabel = new JLabel("Age");

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(ageLabel, constraints);


        profileEditAgeField = new JFormattedTextField(numberFormat);
        System.out.println("Test error");


        profileEditAgeField.setColumns(10);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profileEditAgeField, constraints);


        profileEditBackButton = new JButton("Back");

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(profileEditBackButton, constraints);


        profileEditSubmitButton = new JButton("Submit");

        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profileEditSubmitButton, constraints);

        profileEditDisplayError = new JLabel("Error");
        profileEditDisplayError.setForeground(Color.red);
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        profilePanel.add(profileEditDisplayError, constraints);


        this.profileEditPanel = profilePanel;
    }

    public void updateToken(int row, int column, Color color) {
        this.boardTokenCells[row][column].setBackground(color);
    }

    public void updateCurrentPlayer(String currentPlayerName) {
        this.currentPlayerLabel.setText(currentPlayerName);
    }

    public JTextField authUsernameField() {
        return this.authenticationUsernameField;
    }

    public JPasswordField authPasswordField() {
        return this.authenticationPasswordField;
    }

    public JButton loginButton() {
        return this.loginButton;
    }

    // Sign Up

    public JTextField signUserNameField() {
        return this.signUserNameField;
    }

    public JPasswordField signPasswordField() {
        return this.signPasswordField;
    }

    public JTextField signNationalityField() {
        return this.signNationalityField;
    }

    public JTextField signAgeField() {
        return this.signAgeField;
    }

    public JTextField signUpPictureField() {
        return this.signUpPictureField;
    }


    // profile
    public JTextField profEditUserNameField() {
        return this.profileUserNameField;
    }

    public JPasswordField profEditPasswordField() {
        return this.profilePasswordField;
    }

    public JTextField profEditNationalityField() {
        return this.profileEditNationalityField;
    }

    public JTextField profEditAgeField() {
        return this.profileEditAgeField;
    }

    public JButton profEditSubmitButton() {
        return this.profileEditSubmitButton;
    }

    public JButton queueCancelButton() {
        return this.queueCancelButton;
    }

    public JButton profEditBackButton() {
        return this.profileEditBackButton;
    }

    public JButton historyBackButton() {
        return this.historyBackButton;
    }

    public JButton[][] boardTokenCells() {
        return this.boardTokenCells;
    }

    public JButton menuGameHistoryButton() {
        return this.menuGameHistoryButton;
    }

    public JButton gameBoardBackButton() {
        return this.gameBoardBackButton;
    }

    public JButton logInBackButton() {
        return this.logInBackButton;
    }

    public JButton signSubmitButton() {
        return this.signSubmitButton;
    }

    public JButton gameOverExitButton() {
        return this.gameOverExitButton;
    }

    public JButton profileButton() {
        return this.menuProfileJButton;
    }

    public JButton quitButton() {
        return this.gameQuitButton;
    }

    public JButton newGame() {
        return this.menuNewGameButton;
    }

    public JButton startLoginButton() {
        return this.startLoginButton;
    }

    public JButton startSignUpButton() {
        return this.startSignUpButton;
    }

    public JButton signBackButton() {
        return this.signBackButton;
    }

    public void connectFourLogin() {
        this.frame.getContentPane().removeAll();
        this.frame.add(authenticationPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.validate();
    }

    public void connectFourSignUp() {
        this.frame.getContentPane().removeAll();
        this.frame.add(signUpPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.validate();
    }

    public void connectFourStartingMenu() {
        this.frame.getContentPane().removeAll();
        this.frame.add(startingMenuPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.revalidate();
    }

    public void connectFourDisplayProfile() {
        this.frame.getContentPane().removeAll();
        this.frame.add(profileDisplayPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.validate();
    }

    public void connectFourGameHistoryPanel() {
        this.frame.getContentPane().removeAll();
        this.frame.add(gameHistoryPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.validate();
    }

    public void connectFourGameMenu() {
        this.frame.getContentPane().removeAll();
        this.frame.add(this.gameMenuPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.validate();
    }

    public void connectFourGameOver() {
        this.frame.getContentPane().removeAll();
        this.frame.add(this.gameOverPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.validate();
    }

    public void connetFourCloseUI() {
        this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
    }


    public void connectFourStartGame() {
        this.frame.getContentPane().removeAll();
        this.frame.add(boardPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.validate();
    }


    public JLabel getProfileEditDisplayError() {
        return this.profileEditDisplayError;
    }


    public static void main(String[] args) throws IOException {
        ConnectFourView connectFourView = new ConnectFourView();
        ConnectFourClientModel connectFourClientModel = new ConnectFourClientModel();
        ConnectFourPresenter presenter = new ConnectFourPresenter(connectFourView, connectFourClientModel);

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        connectFourView.startGame();
//        connectFourView.updateToken(1, 1, Color.RED);
//        connectFourView.updateToken(1, 2, Color.RED);
//        connectFourView.updateToken(1, 3, Color.RED);
//        connectFourView.updateToken(1, 4, Color.RED);

        // createControlPanel("André");
    }

    public JLabel gameOverLabel() {
        return this.gameOverMessageLabel;
    }

    public JButton profDispEditButton() {
        return this.profileDisplayEditButton;
    }


    public void connectFourProfileEdit() {
        this.frame.getContentPane().removeAll();
        this.frame.add(this.profileEditPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.revalidate();
    }

    public JButton profDispBackButton() {
        return this.profileDisplayBackButton;
    }

    public void connectFourQueueGame() {
        this.frame.getContentPane().removeAll();
        this.frame.add(this.queuePanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.revalidate();
    }

    public void setSignUpDisplayError(String errorMsg) {
        this.signUpDisplayError.setText(errorMsg);
    }

    public void setLoginDisplayError(String errorMsg) {
        this.authenticationDisplayError.setText(errorMsg);
    }

    public void setProfileDisplayError(String errorMsg) {
        this.profileDisplayError.setText(errorMsg);
    }

    public void setGameStatsDisplayError(String erroMsg) {
        this.gameStatsDisplayError.setText(erroMsg);
    }

    public void setGameBoardDisplayError(String erroMsg) {
        this.boardErrorDisplay.setText(erroMsg);
    }

    public void setProfileEditDisplayError(String errorMsg) {
        this.profileEditDisplayError.setText(errorMsg);
    }

    public JTextField profileEditPicture() {
        return this.profileEditPictureField;
    }
}
