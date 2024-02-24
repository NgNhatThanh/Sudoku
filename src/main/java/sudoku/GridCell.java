package sudoku;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class GridCell extends TextField {
    static public final String normalCellStyle = "-fx-border-color: black; -fx-border-width: 0.2;-fx-pref-width: 50;-fx-pref-height: 50;";
    static public final String choosenCellStyle = "-fx-background-color: #e2ebf3; -fx-border-color: black; -fx-border-width: 0.2;";
    static public final String incorrectCellStyle = "-fx-background-color: #e2ebf3; -fx-border-color: black; -fx-border-width: 0.2; -fx-text-fill: red;";
    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {this.correct = correct;}

    private boolean correct = true;

    public GridCell(){
        setStyle("-fx-border-color: black; -fx-border-width: 0.2;");
        setFont(new Font("Calibri", 30));
        setAlignment(Pos.CENTER);
        setPrefSize(50, 50);
    }
}
