package pt.isel.icd.othello.ui.views;

import pt.isel.icd.othello.ui.transition.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GameMenuView extends OthelloView{

    public GameMenuView(JFrame frame) {
        super(frame);
        
        
//        JPanel gameMenuPanel = new JPanel(new BorderLayout());
        this.setLayout(new BorderLayout());

        JPanel containerTop = new JPanel();

        JPanel containerMiddle = new JPanel();

        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        containerTop.setLayout(bagLayout);

        containerMiddle.setLayout(bagLayout);


        JLabel menuTitleLabel = new JLabel("MENU");

        menuTitleLabel.setHorizontalAlignment(JLabel.CENTER);





        containerTop.add(menuTitleLabel);



        this.add(Box.createRigidArea(new Dimension(100, 5)), BorderLayout.WEST);
        this.add(Box.createRigidArea(new Dimension(100, 5)), BorderLayout.EAST);


        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        JButton menuNewGameButton = new JButton("NEW GAME");
        
        menuNewGameButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_NEWGAME));
            }
        });
        menuNewGameButton.setPreferredSize(new Dimension(150, 30));


        containerMiddle.add(menuNewGameButton, constraints);


        constraints.gridy = 1;

        JButton menuProfileJButton = new JButton("PROFILE");
        menuProfileJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_PROFILE));
                
            }
        });
        menuProfileJButton.setPreferredSize(new Dimension(150, 30));




        containerMiddle.add(menuProfileJButton, constraints);

        constraints.gridy = 2;

        JButton menuGameHistoryButton = new JButton("GAME STATS");
        menuGameHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_HISTORY));
            }
        });
        menuGameHistoryButton.setPreferredSize(new Dimension(150, 30));




        containerMiddle.add(menuGameHistoryButton, constraints);

        constraints.gridy = 3;

        JButton gameQuitButton = new JButton("QUIT GAME");
        gameQuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        gameQuitButton.setPreferredSize(new Dimension(150, 30));


        containerMiddle.add(gameQuitButton, constraints);

        this.add(containerMiddle, BorderLayout.CENTER);


        this.add(containerTop, BorderLayout.NORTH);
       
        
        
        
        
        
    }
}