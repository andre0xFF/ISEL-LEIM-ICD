package pt.isel.icd.user.management;

import javax.swing.*;
import java.awt.*;

public class UserFrame {
    private static final int BUTTON_SIZE = 60;
    private static final int COLUMNS = 10;
    private static final int ROWS = 10;

    public JFrame getFrame() {
        JFrame frame = new JFrame("Othello");

        frame.setSize(new Dimension(BUTTON_SIZE * COLUMNS, (BUTTON_SIZE * ROWS) + 40));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }
}
