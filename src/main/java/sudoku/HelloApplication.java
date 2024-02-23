package sudoku;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Scanner;

public class HelloApplication extends Application {
    static public GameGrid grid = new GameGrid();
    static public MenuScene menu = new MenuScene();
    static public PickLevelScene pickLevelScene = new PickLevelScene();
    static public Scene scene;
    @Override
    public void start(Stage stage){
        scene = new Scene(menu);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}