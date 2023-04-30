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
    private JButton loginButton;
    private JPanel boardPanel;
    private JButton[][] boardTokenCells;
    private JLabel currentPlayerLabel;
    private JPanel profilePanel;

    private JTextField newNameField;

    private JTextField newPasswordField;
    private JTextField nationalityField;
    private JTextField ageField;
    private JButton profileBackButton;
    private JButton profileSubmitButton;
    private JPanel gameHistoryPanel;
    private JPanel gameMenuPanel;

    private JPanel startingMenuPanel;
    private JButton historyBackButton;
    private JButton menuNewGameButton;
    private JButton menuProfileJButton;
    private JButton menuGameHistoryButton;
    private JButton gameQuitButton;
    private final int rows;
    private final int columns;
    private ActionListener listener;

    public ConnectFourView(int rows, int columns) {
        createFrame(rows, columns);

        createAuthenticationPanel();
        createBoardPanel(rows, columns);
        createProfilePanel("Daniel", "xpto", "PT", "90");
        createGameHistoryPanel();
        createGameMenuPanel();
        createStartingMenuPanel();
        createSignUpPanel();

        this.frame.add(authenticationPanel, BorderLayout.CENTER);
//        this.frame.add(profilePanel, BorderLayout.CENTER);
//         this.frame.add(gameHistoryPanel, BorderLayout.CENTER);
//        this.frame.add(gameMenuPanel, BorderLayout.CENTER);
//        this.frame.add(startingMenuPanel, BorderLayout.CENTER);

        this.frame.setVisible(true);

        this.rows = rows;
        this.columns = columns;
    }

    private void createSignUpPanel() {
    }

    private void createStartingMenuPanel() {

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

        JButton loginButton = new JButton("Login");

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        authenticationPanel.add(loginButton, constraints);

        this.authenticationPanel = authenticationPanel;
        this.loginButton = loginButton;
//        this.usernameField = usernameField;
//        this.passwordField = passwordField;
//        authenticationPanel.setBackground(Color.PINK);
        authenticationPanel.setOpaque(true);

        




        JPanel startMenuPanel = new JPanel(new BorderLayout());

        JLabel startingMenuTitleLabel = new JLabel("CONNECT FOUR");
        startingMenuTitleLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1));
        startMenuPanel.setBackground(Color.green);
        startMenuPanel.setOpaque(true);
        startMenuPanel.add(startingMenuTitleLabel, BorderLayout.NORTH);

        startMenuPanel.add(buttonPanel, BorderLayout.CENTER);

        JButton startLogInButton = new JButton("Log In");
        startLogInButton.setPreferredSize(new Dimension(30, 10));
        JButton startSignUpButton = new JButton("Sign Up");
        startSignUpButton.setPreferredSize(new Dimension(30, 10));

//        buttonPanel.pac

        buttonPanel.add(startLogInButton);
        buttonPanel.add(startSignUpButton);

        this.startingMenuPanel = startMenuPanel;

    }


    public void setActionListener(ActionListener listener) {
        this.listener = listener;

        this.loginButton.addActionListener(listener);

        this.gameQuitButton.addActionListener(listener);
        this.menuNewGameButton.addActionListener(listener);
        this.menuProfileJButton.addActionListener(listener);
        this.menuGameHistoryButton.addActionListener(listener);
        this.gameQuitButton.addActionListener(listener);

        //Profile Buttons
        this.profileBackButton.addActionListener(listener);
        this.profileSubmitButton.addActionListener(listener);

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

        JButton loginButton = new JButton("Login");

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        authenticationPanel.add(loginButton, constraints);

        this.authenticationPanel = authenticationPanel;
        this.loginButton = loginButton;
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

    public void createProfilePanel(String username, String password, String nationality, String age) {
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


        nationalityField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(nationalityField, constraints);

        JLabel ageLabel = new JLabel("Age");

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(ageLabel, constraints);


        ageField = new JTextField(10);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(ageField, constraints);


        profileBackButton = new JButton("Back");

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.WEST;

        profilePanel.add(profileBackButton, constraints);


        profileSubmitButton = new JButton("Submit");

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.EAST;

        profilePanel.add(profileSubmitButton, constraints);

        this.profilePanel = profilePanel;
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


    public JTextField newNameField() {
        return this.newNameField;
    }

    public JTextField newPassword() {
        return this.newPasswordField;
    }

    public JTextField nationalityField() {
        return this.nationalityField;
    }

    public JTextField ageField() {
        return this.ageField;
    }

    public JButton profileSubmitButton() {
        return this.profileSubmitButton;
    }

    public JButton profileBackButton() {
        return this.profileBackButton;
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


    public JButton profileButton() {
        return this.menuProfileJButton;
    }

    public JButton quitButton() {
        return this.gameQuitButton;
    }

    public JButton newGame() {
        return this.menuNewGameButton;
    }

    public void connectFourProfile() {
        this.frame.getContentPane().removeAll();
        this.frame.add(profilePanel, BorderLayout.CENTER);
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


}
