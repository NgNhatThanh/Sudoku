package sudoku;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuScene extends VBox {
    Button playButton = new Button("PLAY");
    Button leaderBoard = new Button("LEADERBOARD");
    Button exitButton = new Button("EXIT");
    Label nameLabel = new Label("Sudoku");
    public MenuScene(){
        getStylesheets().add(getClass().getResource("/menuscene.css").toExternalForm());
        setPrefSize(450, 550);
        setAlignment(Pos.CENTER);
        setSpacing(40);
        getChildren().add(nameLabel);
        getChildren().add(playButton);
//        getChildren().add(leaderBoard);
        getChildren().add(exitButton);
        playButton.setOnAction(actionEvent -> {
            HelloApplication.scene.setRoot(HelloApplication.pickLevelScene);
        });
        exitButton.setOnAction(actionEvent -> {
            System.exit(0);
        });
    }
}
