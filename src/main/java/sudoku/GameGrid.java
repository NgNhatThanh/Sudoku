package sudoku;

import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.util.LinkedHashSet;
import java.util.Random;

public class GameGrid extends AnchorPane {
    static public final int easyLevel = 38;
    static public final int hardLevel = 30;
    static public final int masterLevel = 27;
    private static int startAmount = easyLevel;
    public void setLevel(int level){
        startAmount = level;
    }
    private final GridCell[][] displayGrid = new GridCell[9][9];
    private final int[][] grid = new int[9][9];
    private final int[][] sample = new int[9][9];
    private final int[][] sol = new int[9][9];
    private int solCount = 0;
    private boolean firstSol = true;
    private boolean isPlaying = true;
    private final Random rand = new Random();

    private boolean isWon(){
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                if(grid[i][j] != sol[i][j]) return false;
            }
        }
        return true;
    }

    void backToMenu(){
        InitApp.scene.setRoot(InitApp.menuScene);
    }

    GameGrid(){
        this.setPrefSize(450, 450);
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                displayGrid[i][j] = new GridCell();
                displayGrid[i][j].setLayoutX(i * 50);
                displayGrid[i][j].setLayoutY(j * 50);
                displayGrid[i][j].setStyle(GridCell.normalCellStyle);
                int finalI = i;
                int finalJ = j;
                displayGrid[finalI][finalJ].setOnKeyPressed(keyEvent -> {
                    if(isPlaying && displayGrid[finalI][finalJ].isCanEdit()){
                        if(keyEvent.getCode().isDigitKey() && keyEvent.getCode() != KeyCode.DIGIT0 && keyEvent.getCode() != KeyCode.NUMPAD0){
                            displayGrid[finalI][finalJ].setText(keyEvent.getText());
                            grid[finalI][finalJ] = Integer.parseInt(keyEvent.getText());
                            displayGrid[finalI][finalJ].setCorrect(true);
                            if(grid[finalI][finalJ] != sol[finalI][finalJ]){
                                displayGrid[finalI][finalJ].setStyle(GridCell.incorrectCellStyle);
                                displayGrid[finalI][finalJ].setCorrect(false);
                            }
                            else{
                                displayGrid[finalI][finalJ].setStyle(GridCell.choosenCellStyle);
                                if(isWon()){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    InitApp.inGameScene.timeLabel.pauseTime();
                                    alert.setContentText("You won!");
                                    alert.showAndWait();
                                    backToMenu();
                                }
                            }
                        }
                        else  if(keyEvent.getCode() == KeyCode.BACK_SPACE){
                            displayGrid[finalI][finalJ].setCorrect(true);
                            displayGrid[finalI][finalJ].setText("");
                            grid[finalI][finalJ] = 0;
                        }
                    }
                });

                displayGrid[i][j].setOnMouseClicked(mouseEvent -> {
                    displayGrid[finalI][finalJ].requestFocus();
                    int stx = (finalI / 3) * 3;
                    int sty = (finalJ / 3) * 3;
                    for(int m = 0; m < 9; ++m){
                        for(int n = 0; n < 9; ++n){
                            if(!displayGrid[m][n].isCorrect()) displayGrid[m][n].setStyle(GridCell.incorrectCellStyle);
                            else{
                                if(m == finalI || n == finalJ || (m >= stx && m < stx + 3 && n >= sty && n < sty + 3)) displayGrid[m][n].setStyle(GridCell.choosenCellStyle);
                                else displayGrid[m][n].setStyle(GridCell.normalCellStyle);
                            }
                        }
                    }
                });
                this.getChildren().add(displayGrid[i][j]);
            }
        }
        solve(0, 0);
        gen();
        solCount = 0;
        solve(0, 0);
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                if(grid[i][j] > 0){
                    displayGrid[i][j].setText(Integer.toString(grid[i][j]));
                    displayGrid[i][j].setCanEdit(false);
                }
            }
        }
        Line d1 = new Line(150, 0, 150, 450);
        Line d2 = new Line(300, 0, 300, 450);
        Line n1 = new Line(0, 150, 450, 150);
        Line n2 = new Line(0, 300, 450, 300);
        this.getChildren().add(d1);
        this.getChildren().add(d2);
        this.getChildren().add(n1);
        this.getChildren().add(n2);
    }

    private boolean check(int n, int x, int y){
        for(int i = 0; i < 9; ++i){
            if(sample[x][i] == n || sample[i][y] == n) return false;
        }
        int stx = x / 3 * 3, sty = y / 3 * 3;
        for(int i = stx; i < stx + 3; ++i){
            for(int j = sty; j < sty + 3; ++j){
                if(sample[i][j] == n) return false;
            }
        }
        return true;
    }
    public void solve(int x, int y){
        if(solCount > 1) return;
        if(x == 9){
            ++solCount;
            if(firstSol){
                for(int i = 0; i < 9; ++i){
                    System.arraycopy(sample[i], 0, grid[i], 0, 9);
                }
                for(int i = 0; i < 9; ++i){
                    System.arraycopy(sample[i], 0, sol[i], 0, 9);
                }
            }
            firstSol = false;
            return;
        }
        if(y == 9){
            solve(x + 1, 0);
            return;
        }
        if(sample[x][y] != 0){
            solve(x, y + 1);
            return;
        }
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        while(set.size() < 9) set.add(rand.nextInt(1, 10));
        for(var k : set){
            if(check(k, x, y)){
                sample[x][y] = k;
                solve(x, y + 1);
                sample[x][y] = 0;
            }
        }
    }
    public void gen(){
        int remains = 81;
        for(int i = 0; i < 9; ++i){
            System.arraycopy(grid[i], 0, sample[i], 0, 9);
        }
        int x = rand.nextInt(0, 9), y = rand.nextInt(0, 9);
        while(remains > startAmount){
            while(grid[x][y] == 0){
                x = rand.nextInt(0, 9);
                y = rand.nextInt(0, 9);
            }
            solCount = 0;
            int tmp = grid[x][y];
            grid[x][y] = 0;
            for(int i = 0; i < 9; ++i){
                System.arraycopy(grid[i], 0, sample[i], 0, 9);
            }
            solve(0, 0);
            if(solCount > 1){
                grid[x][y] = tmp;
                x = rand.nextInt(0, 9);
                y = rand.nextInt(0, 9);
            }
            else --remains;
        }
    }

    public void hideGrid(){
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                displayGrid[i][j].setCanEdit(false);
                displayGrid[i][j].setText("");
                displayGrid[i][j].setStyle(GridCell.normalCellStyle);
            }
        }
        isPlaying = false;
    }

    public void unHideGrid(){
        isPlaying = true;
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                displayGrid[i][j].setCanEdit(true);
                if(grid[i][j] > 0) displayGrid[i][j].setText(Integer.toString(grid[i][j]));
            }
        }
    }
}
