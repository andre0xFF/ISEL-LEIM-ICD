package pt.isel.icd.othello.ui.views;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;

public class ViewSwitcher {
    
    
    public static void update(JFrame frame, JPanel newPanel){
        frame.getContentPane().removeAll();
        frame.add(newPanel, BorderLayout.CENTER);
        frame.repaint();
        frame.validate();
        
    }
    
    
    public static void update(JFrame frame, OthelloView newView){
        frame.getContentPane().removeAll();
        frame.add(newView, BorderLayout.CENTER);
        frame.repaint();
        frame.validate();
    }
    
}