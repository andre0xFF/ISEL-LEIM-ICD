package pt.isel.icd.othello.ui;

import pt.isel.icd.othello.BoardCharacter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TokenCell extends JButton {
    
    int row;
    int col;
    
    private Color color = Color.green;
    
    boolean isVisible = false;
    
    BoardCharacter state;
    
    
    public TokenCell(int row, int col){
        this.row = row;
        this.col = col;
        this.state = BoardCharacter.EMPTY;
        
        
        
        setColor(Color.GREEN);
    }
    
    public void setCellListener(ClickHandler handler){
        actionListener = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.Clicked(row, col, state);
                
            }
        };
        
        this.addActionListener(actionListener);
        
    }
    
    public void setColor(Color color){
        this.color = color;
        this.setBackground(Color.GREEN);
//        this.setForeground(color);
        this.setOpaque(true);
        this.repaint();
//        this.setUI(new BasicButtonUI()); 
    }
    
    public void toggleCellVisibility(boolean flag){
        this.setVisible(flag);
    }
    
    public BoardCharacter getCellState(){
        return this.state;
    }
    
    public int getCellRow(){
        return this.row;
    }
    
    public int getCellCol(){
        return this.col;
    }
    
    public void setCellState(BoardCharacter state){
        this.state = state;
    }
    
    
}