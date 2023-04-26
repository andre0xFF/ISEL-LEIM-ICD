import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConnectFourView {

    private static final int BUTTON_SIZE = 60;
    private static final String CURRENT_PLAYER_LABEL = "Current player: %s";
    private JFrame frame;
    private JPanel authenticationPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel boardPanel;
    private JButton[][] buttons;
    private JLabel currentPlayerLabel;
    private final int rows;
    private final int columns;
    private ActionListener listener;

    public ConnectFourView(int rows, int columns) {
        createFrame(rows, columns);
        createAuthenticationPanel();

        this.frame.setVisible(true);
        this.rows = rows;
        this.columns = columns;
    }

    public void setActionListener(ActionListener listener) {
        this.listener = listener;
    }

    private void createFrame(int rows, int columns) {
        frame = new JFrame("Connect Four");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(BUTTON_SIZE * columns, (BUTTON_SIZE * rows) + 40));
    }

    private void createAuthenticationPanel() {
        authenticationPanel = new JPanel(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username: ");
        authenticationPanel.add(usernameLabel);

        JTextField usernameField = new JTextField(20);
        authenticationPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password: ");
        authenticationPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(20);
        authenticationPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        authenticationPanel.add(loginButton);

        this.frame.add(authenticationPanel, BorderLayout.NORTH);

        this.usernameField = usernameField;
        this.passwordField = passwordField;
    }

    private void createBoardPanel(int rows, int columns) {
        JPanel boardPanel = new JPanel(new GridLayout(rows, columns));

        buttons = new JButton[rows][columns];

        for (int row = rows - 1; row >= 0; row--) {
            for (int column = 0; column < columns; column++) {
                JButton button = new JButton();

                button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                button.setBackground(Color.WHITE);
                button.setOpaque(true);
                button.setEnabled(false);
                button.addActionListener(listener);

                buttons[row][column] = button;

                boardPanel.add(button);
            }
        }

        this.boardPanel = boardPanel;
        this.frame.add(boardPanel, BorderLayout.CENTER);
    }

    public void createControlPanel(String currentPlayerName) {
        JPanel controlPanel = new JPanel(new FlowLayout());
        currentPlayerLabel = new JLabel(String.format(CURRENT_PLAYER_LABEL, currentPlayerName));

        controlPanel.add(currentPlayerLabel);

        this.frame.add(controlPanel, BorderLayout.SOUTH);
    }

    public void updateToken(int row, int column, Color color) {
        this.buttons[row][column].setBackground(color);
    }

    public void updateCurrentPlayer(String playerName) {
        this.currentPlayerLabel.setText(String.format(CURRENT_PLAYER_LABEL, playerName));
    }

    public JTextField usernameField() {
        return usernameField;
    }

    public JPasswordField passwordField() {
        return passwordField;
    }

    public void startGame() {
        this.authenticationPanel.setVisible(false);
        createBoardPanel(rows, columns);
    }

    public static void main(String[] args) {
        ConnectFourView connectFourView = new ConnectFourView(6, 7);

        connectFourView.startGame();
        connectFourView.updateToken(1, 1, Color.RED);
        connectFourView.updateToken(1, 2, Color.RED);
        connectFourView.updateToken(1, 3, Color.RED);
        connectFourView.updateToken(1, 4, Color.RED);

        // createControlPanel("André");
    }
}
