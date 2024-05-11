package pt.isel.icd.othello.ui.views;

import pt.isel.icd.othello.ui.transition.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueView extends OthelloView {
    
    JLabel queueMessage;
    
    boolean inQueue = false;
    JButton submitButton;
    
    JButton backButton;
    

    public QueueView(JFrame frame) {
        super(frame);
        
        
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        
        JLabel tittleLabel = new JLabel("QUEUE MENU", SwingConstants.CENTER);
        
        topPanel.add(tittleLabel);
        this.add(topPanel, BorderLayout.NORTH);
        
        
        JPanel midPanel = new JPanel(new BorderLayout());
        
        queueMessage = new JLabel("", SwingConstants.CENTER);

        
        midPanel.add(queueMessage, BorderLayout.CENTER);
        
    
        midPanel.setAlignmentY(SwingConstants.CENTER);
        this.add(midPanel, BorderLayout.CENTER);
        
        
        
        JPanel southPanel = new JPanel();
        
        
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_BACK));
                
            }
        });
        
        
        
        submitButton = new JButton("Queue");
        
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inQueue){
                    queueMessage.setText("Searching for Opponent...");
                    submitButton.setText("Cancel");
                    //TODO tell controller to find opponent
                    inQueue = true;
                    backButton.setEnabled(false);
                    
                    controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.SUBMIT));
                }else{
                    queueMessage.setText("");
                    submitButton.setText("Queue");
                    inQueue = false;
                    backButton.setEnabled(true);
                    //TODO tell controller to cancel search for opponent
                }
            }
        });
        
        southPanel.add(backButton);
        southPanel.add(submitButton);
        this.add(southPanel, BorderLayout.SOUTH);
      
        
       
    }
    
    
}