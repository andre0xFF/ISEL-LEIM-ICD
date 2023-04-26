import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConnectFourView extends JFrame {

    private static final int BUTTON_SIZE = 60;
    private static final String CURRENT_PLAYER_LABEL = "Current player: %s";
    private JLabel currentPlayerLabel;
    private JButton[][] buttons;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final int rows;
    private final int columns;
    private ActionListener listener;

    public ConnectFourView(String playerName, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;


        // add(createBoardPanel(), BorderLayout.CENTER);
        // add(createControlPanel("André"), BorderLayout.SOUTH);

        add(createAuthenticationPanel());

        setTitle("Connect Four");
        setSize(columns * BUTTON_SIZE, rows * BUTTON_SIZE + 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // startNewGame();
    }

    private JPanel createAuthenticationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        JLabel usernameLabel = new JLabel("Username:");
        formPanel.add(usernameLabel);

        usernameField = new JTextField();
        formPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        formPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        panel.add(formPanel, BorderLayout.NORTH);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(listener);
        panel.add(loginButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBoardPanel() {
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

        return boardPanel;
    }

    public JPanel createControlPanel(String currentPlayerName) {
        JPanel controlPanel = new JPanel(new FlowLayout());
        currentPlayerLabel = new JLabel(String.format(CURRENT_PLAYER_LABEL, currentPlayerName));

        controlPanel.add(currentPlayerLabel);

        return controlPanel;
    }

    public void setActionListener(ActionListener listener) {
        this.listener = listener;
    }

    public void updateToken(int row, int column, Color color) {
        buttons[row][column].setBackground(color);
    }

    public void setCurrentPlayer(String playerName) {
        currentPlayerLabel.setText(String.format(CURRENT_PLAYER_LABEL, playerName));
    }

    public static void main(String[] args) {
        ConnectFourView connectFourView = new ConnectFourView("André", 6, 7);

        connectFourView.updateToken(1, 1, Color.RED);
        connectFourView.updateToken(1, 2, Color.RED);
        connectFourView.updateToken(1, 3, Color.RED);
        connectFourView.updateToken(1, 4, Color.RED);
    }
}
