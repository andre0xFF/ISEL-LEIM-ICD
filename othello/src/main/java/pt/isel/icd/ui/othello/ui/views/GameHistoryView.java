package pt.isel.icd.othello.ui.views;

import pt.isel.icd.othello.ui.transition.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameHistoryView extends OthelloView{

    
    JPanel gameStatsPanel;
    
    public GameHistoryView(JFrame frame) {
        super(frame);
        
        
//        JPanel gameHistoryPanel = new JPanel(new BorderLayout());
        this.setLayout(new BorderLayout());

        JLabel gameStatsTitleLabel = new JLabel("Game Stats");
        gameStatsTitleLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(gameStatsTitleLabel, BorderLayout.NORTH);
        JPanel southContainer = new JPanel(new GridLayout(2, 0));
        JLabel gameStatsDisplayError = new JLabel("Error");
        gameStatsDisplayError.setHorizontalAlignment(JLabel.CENTER);
        southContainer.add(gameStatsDisplayError);

        JButton historyBackButton = new JButton("Back");
        historyBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_BACK));
                //TODO ask controller to request gamehistory results for client
            }
        });
        southContainer.add(historyBackButton);

        this.add(southContainer, BorderLayout.SOUTH);


        JPanel centerContainer = new JPanel();
        BoxLayout boxLayout = new BoxLayout(centerContainer, BoxLayout.Y_AXIS);

        centerContainer.setLayout(boxLayout);

        this.add(centerContainer, BorderLayout.CENTER);


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


        
    }
    
    public void addGameStat(String gameID) {
        JLabel gameStat = new JLabel(gameID);
        gameStat.setHorizontalAlignment(JLabel.CENTER);
        this.gameStatsPanel.add(gameStat);
    }
}