package sudoku;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class InGameScene extends AnchorPane {
    private boolean isPlaying = true;
    TimeLabel timeLabel = new TimeLabel();
    private static GameGrid gameGrid = new GameGrid();

    void backToMenu(){
        InitApp.scene.setRoot(InitApp.menuScene);
    }

    public InGameScene(){
        Button backButton = new Button("Back");
        Button pauseButton = new Button("Pause");
        getChildren().add(backButton);
        getChildren().add(pauseButton);
        getChildren().add(timeLabel);
        timeLabel.setLayoutX(150);
        pauseButton.setLayoutX(300);
        pauseButton.setOnAction(event ->{
            if(isPlaying){
                InitApp.inGameScene.timeLabel.pauseTime();
                gameGrid.hideGrid();
                pauseButton.setText("Continue");
                isPlaying = false;
            }
            else{
                isPlaying = true;
                InitApp.inGameScene.timeLabel.startTime();
                pauseButton.setText("Pause");
                gameGrid.unHideGrid();
            }
        });
        backButton.setOnAction(actionEvent ->  backToMenu());
        String css = Objects.requireNonNull(getClass().getResource("/ingamescene.css")).toExternalForm();
        getStylesheets().add(css);
        pauseButton.setPrefHeight(50);
        getChildren().add(gameGrid);
    }

    public void startNew(){
        getChildren().remove(gameGrid);
        gameGrid = new GameGrid();
        getChildren().add(gameGrid);
        gameGrid.setLayoutY(100);
        gameGrid.setLayoutX(0);
        timeLabel.reset();
        timeLabel.startTime();
    }

    public void setLevel(int level){
        gameGrid.setLevel(level);
    }
}
