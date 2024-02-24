package sudoku;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class MenuScene extends VBox {
    Button playButton = new Button("PLAY");
    Button exitButton = new Button("EXIT");
    Label nameLabel = new Label("Sudoku");
    public MenuScene(){
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/menuscene.css")).toExternalForm());
        setPrefSize(450, 550);
        setAlignment(Pos.CENTER);
        setSpacing(40);
        getChildren().add(nameLabel);
        getChildren().add(playButton);
        getChildren().add(exitButton);
        playButton.setOnAction(actionEvent -> InitApp.scene.setRoot(InitApp.pickLevelScene));
        exitButton.setOnAction(actionEvent -> System.exit(0));
    }
}
