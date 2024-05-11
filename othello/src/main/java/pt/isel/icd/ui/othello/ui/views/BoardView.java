package pt.isel.icd.othello.ui.views;

import pt.isel.icd.othello.BoardCharacter;
import pt.isel.icd.othello.ui.ClickHandler;
import pt.isel.icd.othello.ui.TokenCell;
import pt.isel.icd.othello.ui.transition.Event;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardView extends OthelloView implements ClickHandler{
    
 
    int counter = 0;
    TokenCell[][] boardUI;
    private static final int BUTTON_SIZE = 60;
    private JButton gameBoardBackButton;
    private JLabel boardErrorDisplay;
    
    private JPanel skillPanel;
    int rows = 8;
    int cols = 8;
    
    private int maxSkills = 5;
    
   
    
    public BoardView(JFrame frame){
        super(frame);
        
        
        this.setLayout(new BorderLayout());
        JPanel boardPanel = new JPanel(new GridLayout(rows, cols));
        
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        
        JPanel topContainer = new JPanel(new GridLayout(2,1));
       

        topContainer.add(new JLabel("Game Board", SwingConstants.CENTER));
        
        JPanel topPanel = new JPanel(new GridLayout(1,2));
   
        
        topContainer.add(topPanel);
        JPanel p1Info = new JPanel(bagLayout);
        p1Info.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        JPanel p2Info = new JPanel(bagLayout);
        p2Info.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        topPanel.add(p1Info);
       
        topPanel.add(p2Info);
        

        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridwidth = 10;
        

        
        JLabel p1Img = new JLabel("PHOTO");
        p1Img.setPreferredSize(new Dimension(50,50));
        p1Img.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    
 
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        constraints.weightx = 1;
        p1Info.add(p1Img, constraints);
        
        JLabel p1Name = new JLabel("PLAYER 1",  SwingConstants.CENTER);
        
        constraints.gridx = 2;
        constraints.gridy = 1;
        
        p1Info.add(p1Name, constraints);
        
        JLabel p1Score = new JLabel("Score");
      
        
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        
        p1Info.add(p1Score, constraints);
        
        
        JLabel p1ScoreVal = new JLabel("0");
      
        
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        p1Info.add(p1ScoreVal, constraints);
        
        JLabel p1Timer = new JLabel("Timer", SwingConstants.RIGHT);
        constraints.fill = 1;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        p1Info.add(p1Timer, constraints);
        
        JLabel p1TimerVal = new JLabel("0", SwingConstants.CENTER);
        constraints.gridx = 4;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        p1Info.add(p1TimerVal, constraints);
        
        
        
        // player 2
        JLabel p2Img = new JLabel("PHOTO");
        p2Img.setPreferredSize(new Dimension(50,50));
        p2Img.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        constraints.fill = 0;
        constraints.gridx = 8;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        p2Info.add(p2Img, constraints);
        

        JLabel p2Name = new JLabel("PLAYER 2");
       
        
        constraints.gridx = 6;
        constraints.gridy = 1;

        p2Info.add(p2Name, constraints);

        JLabel p2Score = new JLabel("Score");
       

        constraints.gridx = 5;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        p2Info.add(p2Score, constraints);


        JLabel p2ScoreVal = new JLabel("0");
  

        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        p2Info.add(p2ScoreVal, constraints);

        JLabel p2Timer = new JLabel("Timer", SwingConstants.LEFT);
       
        constraints.gridx = 6;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.fill=1;
        p2Info.add(p2Timer, constraints);

        JLabel p2TimerVal = new JLabel("0", SwingConstants.CENTER);
        constraints.gridx = 5;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        p2Info.add(p2TimerVal, constraints);
        

        
        
        this.add(topContainer, BorderLayout.NORTH);
        
        
        
        
        
      
        
        


        JPanel southPanel = new JPanel(new GridLayout(2, 1));
        gameBoardBackButton = new JButton("Surrender");
        gameBoardBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateView(frame, (OthelloView) getTransitionMachine().getNextState(Event.CLICK_BACK));
                //TODO controller terminates game with a loss for current player
            }
        });
        southPanel.add(gameBoardBackButton);


        boardErrorDisplay = new JLabel("Error");
        boardErrorDisplay.setHorizontalAlignment(JLabel.CENTER);

        southPanel.add(boardErrorDisplay);
        
        

        JPanel skillContainer = new JPanel(new BorderLayout());
        this.add(skillContainer, BorderLayout.EAST);
        
        // Right side skill panel
        
        skillPanel = new JPanel();
      
        skillContainer.add(skillPanel, BorderLayout.CENTER);
        skillContainer.add(new JLabel("Bonus Skills"), BorderLayout.NORTH);
        
     

        this.add(southPanel, BorderLayout.SOUTH);

       
        
        
        boardUI = new TokenCell[rows][cols];

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                TokenCell cell = new TokenCell(row, col);

                cell.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
//                cell.setBackground(Color.WHITE);
                cell.setOpaque(true);
                cell.setEnabled(true);

                boardUI[row][col] = cell;

                boardPanel.add(cell);
            }
        }
        setInitBoardConfig();


        this.add(boardPanel, BorderLayout.CENTER);

        setCellListeners(this);
        
        addSkillToBoard("A", "flips opponents pieces");
        addSkillToBoard("B", "flips my pieces");;
    }
    
    public void addSkillToBoard(String skillName, String skillDescrip){
        
        GridBagConstraints skillConstraints = new GridBagConstraints();
        GridBagLayout skillLayout = new GridBagLayout();
        
        skillPanel.setLayout(skillLayout);
        skillConstraints.gridx = 0;
        skillConstraints.gridy = counter;
        skillConstraints.weighty = 1;
        skillConstraints.fill = GridBagConstraints.HORIZONTAL;
        skillConstraints.anchor = GridBagConstraints.PAGE_START;
        
        
            
            JButton skill = new JButton(skillName);
            skillPanel.add(skill, skillConstraints);
            
            skillConstraints.gridx = 1;
            
            JLabel skillDesc = new JLabel(skillDescrip);
            skillPanel.add(skillDesc, skillConstraints);

            counter++;
            
    }
    
    public void setInitBoardConfig(){
        
    
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                boardUI[row][col].setCellState(BoardCharacter.EMPTY);
            }
        }
        boardUI[3][3].setCellState(BoardCharacter.O);
        boardUI[4][3].setCellState(BoardCharacter.X);
        boardUI[4][4].setCellState(BoardCharacter.O);
        boardUI[3][4].setCellState(BoardCharacter.X);
        
    }
    
    
    
    public TokenCell getCell(int row, int col){
        return boardUI[row][col];
    }
    
    public void setCellListeners(ClickHandler listener){

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                boardUI[row][col].setCellListener(listener);
            }
        }
    }


    @Override
    public void Clicked(int row, int col, BoardCharacter state) {
        //TODO request controller to update board and send new board to server
        System.out.println("(" + row + "," + col + "," + state.name() + ")");
                
    }
}