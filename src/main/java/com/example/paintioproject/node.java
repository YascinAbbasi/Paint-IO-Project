package com.example.paintioproject;

import javafx.geometry.Insets;
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
    private int LocationNum;
   private Color color;
    public boolean is_colored = true;
    public boolean is_passed  =false;
    private boolean is_player = false;
    private boolean is_MainPlayer = false;
    public node( Color color){
        setPrefSize(25,25);
        this.color = color;
    }
    @Override
    protected void layoutChildren() {

        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    }
    public void setColor(Color color) {
        this.color = color;
        requestLayout();
    }




}
