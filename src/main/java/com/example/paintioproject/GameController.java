package com.example.paintioproject;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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
                }else{
                    columnIndex = 24;
                }
            }
            case RIGHT -> {
                if (columnIndex < 24) {
                    columnIndex++;
                }else{
                    columnIndex = 0;
                }
            }
            case UP -> {
                if(rowIndex > 0){
                    rowIndex--;
                }else{
                    rowIndex = 24;
                }
            }
            case DOWN -> {
                if(rowIndex< 24){
                    rowIndex++;
                }else{
                    rowIndex = 0;
                }
            }
            default -> {
            }
        }
        GridPane.setConstraints(label, columnIndex, rowIndex);

    }
    public void nodehandel(){
        node nd = new node();
        node nd2 = new node();
        gp.add(nd,2,2);
        gp.add(nd2,3,3);
        System.out.println(nd.is_colored);
        System.out.println(nd.is_passed);
    }


}
