package sudoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InitApp extends Application {
    static public InGameScene inGameScene = new InGameScene();
    static public MenuScene menuScene = new MenuScene();
    static public PickLevelScene pickLevelScene = new PickLevelScene();
    static public Scene scene;

    @Override
    public void start(Stage stage){
        scene = new Scene(menuScene);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}