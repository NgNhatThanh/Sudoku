package sudoku;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class GridCell extends TextField {

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {this.correct = correct;}

    private boolean correct = true;

    public GridCell(){
        setStyle("-fx-border-color: black; -fx-border-width: 0.2px;");
        setFont(new Font("Calibri", 30));
        setAlignment(Pos.CENTER);
        setPrefSize(50, 50);
    }
}
