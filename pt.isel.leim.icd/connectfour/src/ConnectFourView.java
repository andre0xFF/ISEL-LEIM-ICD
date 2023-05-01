import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;

public class ConnectFourView {

    private static final int BUTTON_SIZE = 60;
    private JFrame frame;
    private JPanel authenticationPanel;
    private JTextField authUsernameField;

    private JPasswordField authPasswordField;

    private JTextField profUserNameField;
    private JPasswordField profPasswordField;

    //Log In
    private JButton logInBackButton;
    private JButton loginButton;
    private JPanel boardPanel;
    private JButton[][] boardTokenCells;
    private JLabel currentPlayerLabel;
    private JPanel profileEditPanel;

    // Profile Edit Panel


    private JPanel profileDisplayPanel;

    private JLabel profDispImageLabel;
    private JLabel profDispUserNameLabel;
    private JLabel profDispPasswordLabel;
    private JLabel profDispNationalityLabel;
    private JLabel profDispAgeLabel;

    private JTextField profEditnationalityField;
    private JTextField profEditAgeField;
    private JButton profEditBackButton;
    private JButton profEditSubmitButton;
    private JPanel gameHistoryPanel;
    private JPanel gameMenuPanel;

    private JPanel startingMenuPanel;

    // Starting Menu
    private JButton startSignUpButton;
    private JButton startLogInButton;

    // Sign Up
    private JPanel signUpPanel;
    private JTextField signUserNameField;
    private JPasswordField signPasswordField;
    private JTextField signNationalityField;
    private JTextField signAgeField;
    private JButton signSubmitButton;
    private JTextField signUpPictureField;
    private JButton signBackButton;

    private JButton profDispBackButton;

    private int signPictureVal;

    // Game History
    private JButton historyBackButton;

    // Main Menu
    private JButton menuNewGameButton;
    private JButton menuProfileJButton;
    private JButton menuGameHistoryButton;
    private JButton gameQuitButton;
    private JButton profDispEditButton;
    private final int rows;
    private final int columns;

    // Game Over
    private JPanel gameOverPanel;
    private JButton gameOverExitButton;
    private JLabel gameOverMessageLabel;
    private ActionListener listener;


    public ConnectFourView(int rows, int columns) {
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

//        this.frame.add(authenticationPanel, BorderLayout.CENTER);
//        this.frame.add(profilePanel, BorderLayout.CENTER);
//         this.frame.add(gameHistoryPanel, BorderLayout.CENTER);
        this.frame.add(gameMenuPanel, BorderLayout.CENTER);
//        this.frame.add(startingMenuPanel, BorderLayout.CENTER);
//        this.frame.add(gameOverPanel, BorderLayout.CENTER);

        this.frame.setVisible(true);

        this.rows = rows;
        this.columns = columns;
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

        profDispImageLabel = new JLabel();

        constraints.gridx = 0;
        constraints.gridy = 1;
        profDispImageLabel.setBackground(Color.red);
        profDispImageLabel.setOpaque(true);
        profDispImageLabel.setPreferredSize(new Dimension(60, 60));

        profileDisplayPanel.add(profDispImageLabel, constraints);
        constraints.gridwidth = 1;

        JLabel nickLabel = new JLabel("Username");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;

        profileDisplayPanel.add(nickLabel, constraints);


        profDispUserNameLabel = new JLabel();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;

        profileDisplayPanel.add(profDispUserNameLabel, constraints);


        JLabel passLabel = new JLabel("Password");

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;

        profileDisplayPanel.add(passLabel, constraints);


        profDispPasswordLabel = new JLabel();

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;

        profileDisplayPanel.add(profDispPasswordLabel, constraints);


        JLabel nationalityLabel = new JLabel("Nationality");

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.WEST;

        profileDisplayPanel.add(nationalityLabel, constraints);


        profDispNationalityLabel = new JLabel();

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;

        profileDisplayPanel.add(profDispNationalityLabel, constraints);

        JLabel ageLabel = new JLabel("Age");

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.WEST;

        profileDisplayPanel.add(ageLabel, constraints);


        profDispAgeLabel = new JLabel();

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.EAST;

        profileDisplayPanel.add(profDispAgeLabel, constraints);


        profDispBackButton = new JButton("Back");

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.WEST;

        profileDisplayPanel.add(profDispBackButton, constraints);


        profDispEditButton= new JButton("Edit");

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.EAST;

        profileDisplayPanel.add(profDispEditButton, constraints);



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


//        signUpPictureLabel.setPreferredSize(new Dimension(60, 60));

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
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.EAST;

        signUpPanel.add(signSubmitButton, constraints);

        signBackButton = new JButton("Back");
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.WEST;

        signUpPanel.add(signBackButton, constraints);

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


        startLogInButton = new JButton("Log In");

        startMenuPanel.add(startLogInButton, constraints);


        startLogInButton.setPreferredSize(new Dimension(100, 40));

        startSignUpButton = new JButton("Sign Up");

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.top = 10;

        startMenuPanel.add(startSignUpButton, constraints);
        startSignUpButton.setPreferredSize(new Dimension(100, 40));


        this.startingMenuPanel = startMenuPanel;

    }


    public void setActionListener(ActionListener listener) {
        this.listener = listener;

        // Game Over Buttons
        gameOverExitButton.addActionListener(listener);

        // Sign Up Buttons
        signSubmitButton.addActionListener(listener);
        signBackButton.addActionListener(listener);

        // Start Menu Buttons
        this.startSignUpButton.addActionListener(listener);
        this.startLogInButton.addActionListener(listener);

        // LogIn Menu Buttons
        this.loginButton.addActionListener(listener);
        this.logInBackButton.addActionListener(listener);

        this.gameQuitButton.addActionListener(listener);
        this.menuNewGameButton.addActionListener(listener);
        this.menuProfileJButton.addActionListener(listener);
        this.menuGameHistoryButton.addActionListener(listener);
        this.gameQuitButton.addActionListener(listener);

        //Profile Buttons
        this.profEditBackButton.addActionListener(listener);
        this.profEditSubmitButton.addActionListener(listener);

        this.profDispBackButton.addActionListener(listener);
        this.profDispEditButton.addActionListener(listener);

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

        authUsernameField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets.top = 10;

        authenticationPanel.add(authUsernameField, constraints);

        JLabel passwordLabel = new JLabel("Password: ");

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets.bottom = 10;
        authenticationPanel.add(passwordLabel, constraints);

        authPasswordField = new JPasswordField(10);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets.bottom = 10;
        authenticationPanel.add(authPasswordField, constraints);

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

//        this.usernameField = usernameField;
//        this.passwordField = passwordField;
//        authenticationPanel.setBackground(Color.PINK);
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

        this.boardPanel = boardPanel;
    }

    public void createControlPanel(String currentPlayerName) {
        JPanel controlPanel = new JPanel(new FlowLayout());
        currentPlayerLabel = new JLabel(currentPlayerName);

        controlPanel.add(currentPlayerLabel);
        controlPanel.setBackground(Color.RED);
        controlPanel.setOpaque(true);
        this.frame.add(controlPanel, BorderLayout.SOUTH);
    }

    public void createGameHistoryPanel() {

        JPanel gameHistoryPanel = new JPanel();

        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        gameHistoryPanel.setLayout(bagLayout);

        JLabel titleLabel = new JLabel("Game History");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.NORTH;

        gameHistoryPanel.add(titleLabel, constraints);

        constraints.gridwidth = 1;

        JLabel gamesLabel = new JLabel("Games");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;

        gameHistoryPanel.add(gamesLabel, constraints);


        JLabel winsLossesLabel = new JLabel("Wins/Losses");

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.left = 10;

        gameHistoryPanel.add(winsLossesLabel, constraints);

        JLabel gameTimeLabel = new JLabel("Game Time");

        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets.left = 10;

        gameHistoryPanel.add(gameTimeLabel, constraints);

        historyBackButton = new JButton("Back");

        constraints.gridx = 1;
        constraints.gridy = -1;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.insets.top = 10;

        gameHistoryPanel.add(historyBackButton, constraints);


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

        JLabel profilePicture = new JLabel("PlaceHolder");

        constraints.gridx = 0;
        constraints.gridy = 1;
        profilePicture.setBackground(Color.red);
        profilePicture.setOpaque(true);
        profilePicture.setPreferredSize(new Dimension(60, 60));

        profilePanel.add(profilePicture, constraints);
        constraints.gridwidth = 1;

        JLabel nickLabel = new JLabel("Username");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(nickLabel, constraints);


        profUserNameField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profUserNameField, constraints);


        JLabel passLabel = new JLabel("Password");

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(passLabel, constraints);


        profPasswordField = new JPasswordField(10);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profPasswordField, constraints);


        JLabel nationalityLabel = new JLabel("Nationality");

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(nationalityLabel, constraints);


        profEditnationalityField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profEditnationalityField, constraints);

        JLabel ageLabel = new JLabel("Age");

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(ageLabel, constraints);


        profEditAgeField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profEditAgeField, constraints);


        profEditBackButton = new JButton("Back");

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(profEditBackButton, constraints);


        profEditSubmitButton = new JButton("Submit");

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profEditSubmitButton, constraints);

        this.profileEditPanel = profilePanel;
    }

    public void updateToken(int row, int column, Color color) {
        this.boardTokenCells[row][column].setBackground(color);
    }

    public void updateCurrentPlayer(String currentPlayerName) {
        this.currentPlayerLabel.setText(currentPlayerName);
    }

    public JTextField authUsernameField() {
        return this.authUsernameField;
    }

    public JPasswordField authPasswordField() {
        return this.authPasswordField;
    }

    public JButton loginButton() {
        return this.loginButton;
    }

    // Sign Up

    public JTextField signUserNameField(){
        return this.signUserNameField;
    }

    public JPasswordField signPasswordField(){
        return this.signPasswordField;
    }

    public  JTextField signNationalityField(){
        return this.signNationalityField;
    }

    public JTextField signAgeField(){
        return this.signAgeField;
    }

    public JTextField signUpPictureField(){
        return this.signUpPictureField;
    }



    // profile
    public JTextField profEditUserNameField() {
        return this.profUserNameField;
    }

    public JPasswordField profEditPasswordField() {
        return this.profPasswordField;
    }

    public JTextField profEditNationalityField() {
        return this.profEditnationalityField;
    }

    public JTextField profEditAgeField() {
        return this.profEditAgeField;
    }

    public JButton profEditSubmitButton() {
        return this.profEditSubmitButton;
    }

    public JButton profEditBackButton() {
        return this.profEditBackButton;
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

    public JButton logInBackButton(){
        return this.logInBackButton;
    }

    public JButton signSubmitButton(){
        return this.signSubmitButton;
    }

    public JButton gameOverExitButton(){
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

    public JButton startLogInButton(){
        return this.startLogInButton;
    }

    public  JButton  startSignUpButton(){
        return this.startSignUpButton;
    }

    public JButton signBackButton(){
        return this.signBackButton;
    }

    public void connectFourLogIn(){
        this.frame.getContentPane().removeAll();
        this.frame.add(authenticationPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.validate();
    }

    public void connectFourSignUp(){
        this.frame.getContentPane().removeAll();
        this.frame.add(signUpPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.validate();
    }

    public void connectFourStartingMenu(){
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

    public void connectFourGameOver(){
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


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        ConnectFourView connectFourView = new ConnectFourView(6, 7);
        ConnectFourClientModel clientModel = new ConnectFourClientModel();
        ConnectFourPresenter presenter = new ConnectFourPresenter(connectFourView, clientModel);

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
        return this.profDispEditButton;
    }

    public void connectFourProfileEdit() {
        this.frame.getContentPane().removeAll();
        this.frame.add(this.profileEditPanel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.revalidate();
    }

    public JButton profDispBackButton() {
        return this.profDispBackButton;
    }
}
