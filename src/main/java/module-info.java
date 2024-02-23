module Sudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens sudoku to javafx.fxml;
    exports sudoku;
}