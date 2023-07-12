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
    public int row;
    public int column;
    public int LocationNum;
   private Color color;
    public boolean is_default;

    public boolean is_colored = false;
    public boolean is_passed  =false;
    private boolean is_player = false;
    private boolean is_MainPlayer = false;
    public node( Color color,int row,int column,boolean is_default){
        setPrefSize(x,y);
        this.color = color;
        this.row = row;
        this.column = column;
        this.is_default = is_default;
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
