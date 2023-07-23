package com.example.paintioproject;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class node extends Region {
   private int x = 25;
   private   int y = 25;
    private int row;
    private int column;
   private Color color;
   private Color DefaultColor;
   private Color  TraceColor;

    public boolean is_default;

    private boolean is_colored = false;
    private boolean is_passed  =false;
    private boolean is_player = false;
    private boolean is_MainPlayer = false;

    public void SetRow(int row){
        this.row  = row;
    }
    public void SetColumn(int column){
        this.column = column;
    }
    public int GetRow(){
        return row;
    }
    public int GetColumn(){
        return column;
    }


    public void SetIs_colored(boolean is_colored) {
        this.is_colored = is_colored;
    }

    public void SetIs_passed(boolean is_passed) {
        this.is_passed = is_passed;
    }

    public void SetIs_player(boolean is_player) {
        this.is_player = is_player;
    }

    public boolean Getis_player() {
        return is_player;
    }

    public boolean GetIs_passed(){
        return is_passed;
    }
    public boolean GetIs_colored(){
        return is_colored;
    }


    public node(Color color, int row, int column, boolean is_default){
        setPrefSize(x,y);
        this.color = color;
        DefaultColor = color;
        this.row = row;
        this.column = column;
        this.is_default = is_default;
        is_colored = false;
        is_passed = false;
    }
    @Override
    protected void layoutChildren() {

        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    }
    public void setColor(Color color) {
        this.color = color;
        requestLayout();
    }
    public void ResettoDefaultColor(){
        color = DefaultColor;
        requestLayout();
    }




}
