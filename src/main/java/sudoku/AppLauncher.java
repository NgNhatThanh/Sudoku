package sudoku;

import javafx.application.Application;

/*
You may wonder "why this class exist ?", or "why don't you just run the code with the InitApp
class ?" ...
I know this class looks weird at first sight, but it's not.
It exists because of one "feature" in Java/JavaFX Module System
You can find a good explanation here:
https://stackoverflow.com/questions/52569724/javafx-11-create-a-jar-file-with-gradle
 */

public class AppLauncher {
    static public void main(String[] args){
        Application.launch(InitApp.class, args);
    }
}
