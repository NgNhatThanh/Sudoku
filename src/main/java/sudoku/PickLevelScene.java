package sudoku;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class PickLevelScene extends VBox{
    Button easyLevel = new Button("EASY");
    Button hardLevel = new Button("HARD");
    Button masterLevel = new Button("MASTER");
    Label nameLabel = new Label("Choose level");
    public PickLevelScene(){
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/menuscene.css")).toExternalForm());
        setAlignment(Pos.CENTER);
        setSpacing(40);
        getChildren().add(nameLabel);
        getChildren().add(easyLevel);
        getChildren().add(hardLevel);
        getChildren().add(masterLevel);
        easyLevel.setOnAction(buttonHanlder);
        hardLevel.setOnAction(buttonHanlder);
        masterLevel.setOnAction(buttonHanlder);
    }

    EventHandler<ActionEvent> buttonHanlder = event -> {
        if(event.getSource() == easyLevel) InitApp.inGameScene.setLevel(GameGrid.easyLevel);
        else if(event.getSource() == hardLevel) InitApp.inGameScene.setLevel(GameGrid.hardLevel);
        else InitApp.inGameScene.setLevel(GameGrid.masterLevel);
        InitApp.inGameScene.startNew();
        InitApp.scene.setRoot(InitApp.inGameScene);
    };
}
