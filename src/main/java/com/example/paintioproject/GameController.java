package com.example.paintioproject;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameController {
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private Rectangle RECT;
    @FXML
    private Label label;
    @FXML
    private GridPane gp;
    private int columnIndex = 12;
    private int rowIndex = 12;

    @FXML
    void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT -> {
                if (columnIndex > 0) {
                    columnIndex--;
                }
            }
            case RIGHT -> {
                if (columnIndex < 24) {
                    columnIndex++;
                }
            }
            case UP -> {
                if(rowIndex <24){
                    rowIndex--;
                }
            }
            case DOWN -> {
                if(rowIndex >0){
                    rowIndex++;
                }
            }
            default -> {
            }
        }
        GridPane.setConstraints(label, columnIndex, rowIndex);
    }


}
