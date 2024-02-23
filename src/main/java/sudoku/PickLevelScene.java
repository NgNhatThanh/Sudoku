package sudoku;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PickLevelScene extends VBox{
    Button easyLevel = new Button("EASY");
    Button hardLevel = new Button("HARD");
    Button masterLevel = new Button("MASTER");
    Label nameLabel = new Label("Choose level");
    public PickLevelScene(){
        getStylesheets().add(getClass().getResource("/menuscene.css").toExternalForm());
        setAlignment(Pos.CENTER);
        setSpacing(40);
        getChildren().add(nameLabel);
        getChildren().add(easyLevel);
        getChildren().add(hardLevel);
        getChildren().add(masterLevel);
        easyLevel.setOnAction(buttonHanlder);
        hardLevel.setOnAction(buttonHanlder);
        masterLevel.setOnAction(buttonHanlder);
//        easyLevel.setOnAction(actionEvent -> {
//
//        });
    }

    EventHandler<ActionEvent> buttonHanlder = event -> {
        if(event.getSource() == easyLevel) HelloApplication.grid.setLevel(GameGrid.easyLevel);
        else if(event.getSource() == hardLevel) HelloApplication.grid.setLevel(GameGrid.hardLevel);
        else HelloApplication.grid.setLevel(GameGrid.masterLevel);
        HelloApplication.grid = new GameGrid();
        HelloApplication.scene.setRoot(HelloApplication.grid);
        HelloApplication.grid.timeLabel.startTime();
    };
}
