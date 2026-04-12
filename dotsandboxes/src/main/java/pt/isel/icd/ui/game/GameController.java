package pt.isel.icd.ui.game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import pt.isel.icd.ClientController;
import pt.isel.icd.GameEventListener;
import pt.isel.icd.game.logic.Board;
import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.game.logic.Line;
import pt.isel.icd.game.logic.PlayerMarker;
import pt.isel.icd.ui.ViewController;
import pt.isel.icd.ui.ViewManager;
import pt.isel.icd.ui.menu.MainMenuController;

import java.util.ArrayList;
import java.util.List;


public class GameController implements ViewController, GameEventListener {

    private static final int DOT_SIZE = 12;
    private static final int BOX_SIZE = 60;

    @FXML private Label lblScoreA;
    @FXML private Label lblScoreB;
    @FXML private Label lblTurn;
    @FXML private GridPane boardGrid;
    @FXML private Label lblStatus;

    private ClientController clientController;
    private ViewManager viewManager;

    private PlayerMarker myMarker;
    private boolean isMyTurn;
    private int scoreA;
    private int scoreB;
    private Board localBoard;
    private Button[][] hLines;
    private Button[][] vLines;
    private Pane[][] boxPanes;
    private List<Button> lineButtons = new ArrayList<>();




    @Override
    public void setClientController(ClientController controller) {
        this.clientController = controller;
        this.clientController.setListener(this);
        lblStatus.setText("Waiting for opponent...");
        lblTurn.setText("");
        clientController.joinGame();
    }

    @Override
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public String getFxmlPath() {
        return "/pt/isel/icd/ui/game/GameView.fxml";
    }



    // GameEventListener

    @Override
    public void onGameJoined(PlayerMarker marker) {
        Platform.runLater(() ->{

            myMarker = marker;
            scoreA = 0;
            scoreB = 0;
            int rows = clientController.getGame().board().rows();
            int cols = clientController.getGame().board().cols();

            buildBoard(rows, cols);
            isMyTurn = (marker == PlayerMarker.A);
            setLineButtonsEnabled(isMyTurn);
            lblTurn.setText(isMyTurn ? "Your turn" : "Opponent's turn");
            lblStatus.setText("You are Player " + marker.name());

        });

    }

    @Override
    public void onLinePlaced(Dot dot1, Dot dot2, String marker, boolean extraTurn) {
        Platform.runLater(() ->{
            drawLine(dot1, dot2);

            try{
                Line line = new Line(dot1, dot2);

                PlayerMarker pm = PlayerMarker.valueOf(marker);
                int boxesClosed = localBoard.placeLine(line, pm);

                if(pm == PlayerMarker.A){
                    scoreA += boxesClosed;
                }else{
                    scoreB += boxesClosed;
                }
            }catch (Exception ignored){

            }
            refreshBoxes();
            lblScoreA.setText("A: " + scoreA);
            lblScoreB.setText("B: " + scoreB);

            boolean justPlacedByMe = marker.equals(myMarker.name());
            isMyTurn = extraTurn ? justPlacedByMe : !justPlacedByMe;

            setLineButtonsEnabled(isMyTurn);
            lblTurn.setText(isMyTurn ? "Your turn" : "Opponent's turn");

        });
    }

    @Override
    public void onGameOver(boolean hasWinner, String winnerMarker, int scoreA, int scoreB) {
        Platform.runLater(() -> {
            setLineButtonsEnabled(false);
            lblTurn.setText("");
            if (!hasWinner) {
                lblStatus.setText("Draw! A: " + scoreA + "  B: " + scoreB);
            } else if (winnerMarker.equals(myMarker.name())) {
                lblStatus.setText("You Win! A: " + scoreA + "  B: " + scoreB);
            } else {
                lblStatus.setText("You Lose! A: " + scoreA + "  B: " + scoreB);
            }
        });
    }

    @Override
    public void onGameLeft() {
        Platform.runLater(() -> {
            viewManager.show(new MainMenuController());
        });
    }

    @FXML
    public void onLeaveClicked(){
        clientController.leaveGame();
    }

    // Board helpers

    private Button createLineButton(boolean horizontal) {
        Button btn = new Button();
        btn.setPrefSize(
            horizontal ? BOX_SIZE : DOT_SIZE,
            horizontal ? DOT_SIZE : BOX_SIZE
        );
        btn.setMaxSize(
            horizontal ? BOX_SIZE : DOT_SIZE,
            horizontal ? DOT_SIZE : BOX_SIZE
        );
        btn.setStyle("-fx-background-color: #cccccc; -fx-background-radius: 0; -fx-padding: 0;");
        return btn;
    }

    private void drawLine(Dot dot1, Dot dot2) {
        Line line = new Line(dot1, dot2);
        Dot d = line.dot1();
        Button btn = line.isHorizontal()
            ? hLines[d.row()][d.col()]
            : vLines[d.row()][d.col()];
        if (btn != null) {
            btn.setStyle("-fx-background-color: #222222; -fx-background-radius: 0; -fx-padding: 0;");
            btn.setDisable(true);
            btn.setUserData("placed");
        }
    }

    private void refreshBoxes() {
        for (int r = 0; r < localBoard.boxRows(); r++) {
            for (int c = 0; c < localBoard.boxCols(); c++) {
                PlayerMarker owner = localBoard.getBoxOwner(r, c);
                if (owner == PlayerMarker.A)
                    boxPanes[r][c].setStyle("-fx-background-color: #aaddff;");
                else if (owner == PlayerMarker.B)
                    boxPanes[r][c].setStyle("-fx-background-color: #ffaaaa;");
            }
        }
    }

    private void setLineButtonsEnabled(boolean enabled) {
        for (Button btn : lineButtons) {
            if (btn.getUserData() == null)
                btn.setDisable(!enabled);
        }
    }

    // Board building
    private void buildBoard(int rows, int cols){
        boardGrid.getChildren().clear();
        boardGrid.getColumnConstraints().clear();
        boardGrid.getRowConstraints().clear();
        lineButtons.clear();

        localBoard = new Board(rows, cols);
        hLines = new Button[rows][cols - 1];
        vLines = new Button[rows - 1][cols];
        boxPanes = new Pane[rows - 1][cols -1];

        int gridRows = 2 * rows - 1;
        int gridCols = 2 * cols - 1;

        for(int col = 0; col < gridCols; col++){

            ColumnConstraints colConstrains = new ColumnConstraints();
            colConstrains.setPrefWidth(col % 2 == 0 ? DOT_SIZE : BOX_SIZE);
            boardGrid.getColumnConstraints().add(colConstrains);
        }

        for(int row = 0; row < gridRows; row++){

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight( row % 2 == 0 ? DOT_SIZE : BOX_SIZE);
            boardGrid.getRowConstraints().add(rowConstraints);
        }

        for(int grow = 0; grow < gridRows; grow++){
            for(int gcol = 0; gcol < gridCols; gcol++){
                if(grow % 2 == 0 && gcol % 2 == 0){
                    // dot
                    Pane dot = new Pane();

                    dot.setPrefSize(DOT_SIZE, DOT_SIZE);
                    dot.setStyle("-fx-background-color: Black; -fx-background-radius: 50%;");
                    boardGrid.add(dot, gcol, grow);

                }else if (grow % 2 == 0){
                    // horizontal line slot
                    int dotRow = grow / 2;
                    int dotCol = gcol / 2;
                    Button btn = createLineButton(true);
                    btn.setOnAction(e -> clientController.placeLine(
                            new Dot(dotRow, dotCol),
                            new Dot(dotRow, dotCol + 1)
                    ));
                    hLines[dotRow][dotCol] = btn;
                    lineButtons.add(btn);
                    boardGrid.add(btn, gcol, grow);

                }else if (gcol % 2 == 0){
                    // Vertical line slot
                    int dotRow = grow / 2;
                    int dotCol = gcol / 2;
                    Button btn = createLineButton(false);
                    btn.setOnAction(e -> clientController.placeLine(
                            new Dot(dotRow, dotCol),
                            new Dot(dotRow + 1, dotCol)
                    ));
                    vLines[dotRow][dotCol] = btn;
                    lineButtons.add(btn);
                    boardGrid.add(btn, gcol, grow);

                }else {
                    // Box
                    int boxRow = grow / 2;
                    int boxCol = gcol / 2;
                    Pane box = new Pane();
                    box.setPrefSize(BOX_SIZE, BOX_SIZE);
                    box.setStyle("-fx-background-color: #e8e8e8;");
                    boxPanes[boxRow][boxCol] = box;
                    boardGrid.add(box, gcol, grow);
                }
            }
        }

    }



}
