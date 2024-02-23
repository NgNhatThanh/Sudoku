package sudoku;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Random;

public class GameGrid extends AnchorPane {
    static public final int easyLevel = 38;
    static public final int hardLevel = 30;
    static public final int masterLevel = 26;
    private static int startAmount = easyLevel;
    public void setLevel(int level){
        startAmount = level;
    }
    private int[][] grid = new int[9][9];
    private int[][] sol = new int[9][9];
    private int[][] solu = new int[9][9];
    private final GridCell[][] displayGrid = new GridCell[9][9];
    private int cnt = 0;
    private boolean solvable = true;
    Button backButton = new Button("Back");
    Button pauseButton = new Button("Pause");
    TimeLabel timeLabel = new TimeLabel();
    private boolean isPlaying = true;
    Random rand = new Random();
    void backToMenu(){
        HelloApplication.scene.setRoot(HelloApplication.menu);
    }

    private boolean completeCheck(){
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                if(grid[i][j] == 0) return false;
            }
        }
        return true;
    }

    public GameGrid(){
//        backButton.getStyleClass().add("backButton");
        getChildren().add(backButton);
        getChildren().add(pauseButton);
        getChildren().add(timeLabel);
        pauseButton.setOnAction(event ->{
            if(isPlaying){
                HelloApplication.grid.timeLabel.pauseTime();
                pauseButton.setText("Continue");
                for(int i = 0; i < 9; ++i){
                    for(int j = 0; j < 9; ++j){
//                    displayGrid[i][j].setStyle("-fx-border-color: black; -fx-border-width: 0.2px;");
                        displayGrid[i][j].setText("");
                    }
                }
                isPlaying = false;
            }
            else{
                isPlaying = true;
                HelloApplication.grid.timeLabel.startTime();
                pauseButton.setText("Pause");
                for(int i = 0; i < 9; ++i){
                    for(int j = 0; j < 9; ++j){
                        if(grid[i][j] > 0) displayGrid[i][j].setText(Integer.toString(grid[i][j]));
                    }
                }
            }
        });
        backButton.setOnAction(actionEvent ->  backToMenu());
        String css = getClass().getResource("/gamegrid.css").toExternalForm();
        getStylesheets().add(css);
        timeLabel.setLayoutX(150);
        pauseButton.setLayoutX(300);
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
//                grid[i][j] = 0;
                displayGrid[i][j] = new GridCell();
                displayGrid[i][j].setLayoutX(i * 50);
                displayGrid[i][j].setLayoutY(j * 50 + 100);
                displayGrid[i][j].setStyle("-fx-border-color: black; -fx-border-width: 0.2px;");
//                displayGrid[i][j].getStyleClass().
                String origin = displayGrid[i][j].getStyle();
                int finalI = i;
                int finalJ = j;
                displayGrid[i][j].setOnMouseClicked(mouseEvent -> {
                    int stx = (finalI / 3) * 3;
                    int sty = (finalJ / 3) * 3;
                    for(int m = 0; m < 9; ++m){
                        for(int n = 0; n < 9; ++n){
                            if(m == finalI || n == finalJ || (m >= stx && m < stx + 3 && n >= sty && n < sty + 3)) displayGrid[m][n].setStyle("-fx-background-color: #e2ebf3; -fx-border-color: black; -fx-border-width: 0.2px;");
                            else displayGrid[m][n].setStyle(origin);
                            if(!displayGrid[m][n].isCorrect()) displayGrid[m][n].setStyle("-fx-background-color: #e2ebf3; -fx-border-color: black; -fx-border-width: 0.2px; -fx-text-fill: red;");
                        }
                    }
                    if(isPlaying && displayGrid[finalI][finalJ].isEditable()){
                        displayGrid[finalI][finalJ].requestFocus();
                        displayGrid[finalI][finalJ].setOnKeyPressed(keyEvent -> {
                            if(keyEvent.getCode().isDigitKey()){
                                displayGrid[finalI][finalJ].setText(keyEvent.getText());
                                grid[finalI][finalJ] = Integer.parseInt(keyEvent.getText());
                                displayGrid[finalI][finalJ].setCorrect(true);
                                if(grid[finalI][finalJ] != solu[finalI][finalJ]){
                                    displayGrid[finalI][finalJ].setStyle("-fx-background-color: #e2ebf3; -fx-border-color: black; -fx-border-width: 0.2px; -fx-text-fill: red;");
                                    displayGrid[finalI][finalJ].setCorrect(false);
                                }
                                else{
                                    displayGrid[finalI][finalJ].setStyle("-fx-background-color: #e2ebf3; -fx-border-color: black; -fx-border-width: 0.2px");
                                }
                                if(completeCheck()){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    HelloApplication.grid.timeLabel.pauseTime();
                                    alert.setContentText("You won!");
                                    alert.showAndWait();
                                    backToMenu();
                                }
                            }
                            else if(keyEvent.getCode() == KeyCode.BACK_SPACE){
                                displayGrid[finalI][finalJ].setText("");
                                grid[finalI][finalJ] = 0;
                            }
                            else displayGrid[finalI][finalJ].undo();
                            this.requestFocus();
                        });
                    }
                    else this.requestFocus();
                });
                this.getChildren().add(displayGrid[i][j]);
            }
        }
        Line d1 = new Line(150, 100, 150, 550);
        Line d2 = new Line(300, 100, 300, 550);
        Line n1 = new Line(0, 250, 450, 250);
        Line n2 = new Line(0, 400, 450, 400);
        this.getChildren().add(d1);
        this.getChildren().add(d2);
        this.getChildren().add(n1);
        this.getChildren().add(n2);
        solve(0, 0);
        gen();
        cnt = 0;
        solve(0, 0);
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                if(grid[i][j] > 0){
                    displayGrid[i][j].setText(Integer.toString(grid[i][j]));
                    displayGrid[i][j].setEditable(false);
                }
            }
        }
        this.requestFocus();
    }

    private boolean check(int n, int x, int y){
        for(int i = 0; i < 9; ++i){
            if(sol[x][i] == n || sol[i][y] == n) return false;
        }
        int stx = x / 3 * 3, sty = y / 3 * 3;
        for(int i = stx; i < stx + 3; ++i){
            for(int j = sty; j < sty + 3; ++j){
                if(sol[i][j] == n) return false;
            }
        }
        return true;
    }
    public void solve(int x, int y){
        if(cnt > 1) return;
        if(x == 9){
            ++cnt;
            if(solvable){
                for(int i = 0; i < 9; ++i){
                    System.arraycopy(sol[i], 0, grid[i], 0, 9);
                }
                for(int i = 0; i < 9; ++i){
                    System.arraycopy(sol[i], 0, solu[i], 0, 9);
                }
            }
            solvable = false;
            return;
        }
        if(y == 9){
            solve(x + 1, 0);
            return;
        }
        if(sol[x][y] != 0){
            solve(x, y + 1);
            return;
        }
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        while(set.size() < 9) set.add(rand.nextInt(1, 10));
        for(var k : set){
            if(check(k, x, y)){
                sol[x][y] = k;
                solve(x, y + 1);
                sol[x][y] = 0;
            }
        }
    }
    int remains = 81;
    public void gen(){
        for(int i = 0; i < 9; ++i){
                System.arraycopy(grid[i], 0, sol[i], 0, 9);
            }
        int x = rand.nextInt(0, 9), y = rand.nextInt(0, 9);
        while(remains > startAmount){
            while(grid[x][y] == 0){
                x = rand.nextInt(0, 9);
                y = rand.nextInt(0, 9);
            }
            cnt = 0;
            int tmp = grid[x][y];
            grid[x][y] = 0;
            for(int i = 0; i < 9; ++i){
                System.arraycopy(grid[i], 0, sol[i], 0, 9);
            }
            solve(0, 0);
            if(cnt > 1){
                grid[x][y] = tmp;
                x = rand.nextInt(0, 9);
                y = rand.nextInt(0, 9);
            }
            else --remains;
//            --remains;
        }
    }
}
