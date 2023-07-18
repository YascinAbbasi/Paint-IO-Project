package com.example.paintioproject;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BotPlayer extends NodeManager {

    private Color color;
    private Color BOTColor;
    private Color AreaColor;
    private Color TraceColor;
    private boolean ID_in_use = false;
    private boolean Color_in_use = false;

    int row_move = 0;
    int column_move = 0;

    private String BotID;
   private Color[] Colors = {Color.RED,Color.BLUE,Color.GREEN,Color.PURPLE};

  public BotPlayer(){
      SetBotID();
      SetBotColor();

  }

    public void SetBotID(){
        for(int i = 0; i < PlayerID.size();i++){
            for(int j = 0; j < AlreadyTakenIDs.size();j++){
                if(PlayerID.get(i) == AlreadyTakenIDs.get(j)){
                    ID_in_use = true;
                    break;
                }
                else {
                    ID_in_use = false;
                }
            }
            if(!ID_in_use){
                BotID = PlayerID.get(i);
                AlreadyTakenIDs.add(BotID);
                break;
            }
        }
    }
    public void SetBotColor(){
        for(int i = 0; i < Colors.length ;i++){
            for(int j = 0;  j < AlreadyTakenColors.size();j++){
                if(Colors[i] == AlreadyTakenColors.get(j)){
                    Color_in_use = true;
                    break;
                }
                else {
                    Color_in_use = false;
                }
            }
            if(!Color_in_use){
                color = Colors[i];
                AlreadyTakenColors.add(color);
                break;
            }
            }
        if(color == Color.RED){

            BOTColor = Color.DARKRED;
            AreaColor = color;
            TraceColor = Color.CORAL;
        }
        else if(color == Color.BLUE){

            BOTColor = Color.DARKBLUE;
            AreaColor = color;
            TraceColor = Color.LIGHTBLUE;
        }
        else if(color == Color.GREEN){

            BOTColor = Color.DARKGREEN;
            AreaColor = color;
            TraceColor = Color.LIGHTGREEN;
        }
        else if(color == Color.PURPLE){

            BOTColor = Color.PURPLE;
            AreaColor = Color.MEDIUMPURPLE;
            TraceColor = Color.LAVENDER;
        }

    }
    public String GetBotID(){
        return BotID;
    }
   public void SetDefalutBotArea(){

  }
}
