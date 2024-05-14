package pt.isel.icd.user.management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * View with the player game history.
 */
public class HistoryClientView {

    JPanel gameStatsPanel;

    public HistoryClientView(JFrame frame, UserClientController userClientController){

        JPanel historyViewPanel = new JPanel();

        historyViewPanel.setLayout(new BorderLayout());

        JLabel gameStatsTitleLabel = new JLabel("Game Stats");
        gameStatsTitleLabel.setHorizontalAlignment(JLabel.CENTER);

        historyViewPanel.add(gameStatsTitleLabel, BorderLayout.NORTH);
        JPanel southContainer = new JPanel(new GridLayout(2, 0));
        JLabel gameStatsDisplayError = new JLabel("Error");
        gameStatsDisplayError.setHorizontalAlignment(JLabel.CENTER);
        southContainer.add(gameStatsDisplayError);

        JButton historyBackButton = new JButton("Back");
        historyBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_BACK));

            }
        });
        southContainer.add(historyBackButton);

        historyViewPanel.add(southContainer, BorderLayout.SOUTH);


        JPanel centerContainer = new JPanel();
        BoxLayout boxLayout = new BoxLayout(centerContainer, BoxLayout.Y_AXIS);

        centerContainer.setLayout(boxLayout);

        historyViewPanel.add(centerContainer, BorderLayout.CENTER);


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


        for (int i = 0; i < 80; i++) {
            addGameStat("Test");
        }


        frame.add(historyViewPanel);
    }

    public void addGameStat(String gameID) {
        JLabel gameStat = new JLabel(gameID);
        gameStat.setHorizontalAlignment(JLabel.CENTER);
        this.gameStatsPanel.add(gameStat);
    }
    
}
