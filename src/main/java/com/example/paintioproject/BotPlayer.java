package com.example.paintioproject;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class BotPlayer extends NodeManager {

    private Color color;
    private Color BOTColor;
    private Color AreaColor;
    private Color TraceColor;
    private boolean ID_in_use = false;
    private boolean Color_in_use = false;
    private boolean is_empty = false;
    private node PlayerNode;
    private ArrayList <node> tempnodes = new ArrayList<>();
     private int row_move = 0;
     private int column_move = 0;

    private String BotID;
   private Color[] Colors = {Color.RED,Color.BLUE,Color.GREEN,Color.PURPLE};

  public BotPlayer(){
      SetBotID();
      SetBotColor();
      SetDefaultBotArea();
      SetPlayer();

  }
  public void test() throws InterruptedException {
     row_move = PlayerNode.GetRow();
     column_move = PlayerNode.GetColumn();
     for(int i = row_move + 1; i <= row_move +5; i++)
     for(int j = 0; j < nodes.size();j++){
         if(nodes.get(j).GetRow() == i && nodes.get(j).GetColumn() == column_move){
             nodes.get(j).SetIs_player(true);
             nodes.get(j).setColor(BOTColor);
             PlayerNode.SetIs_player(false);
             PlayerNode.setColor(TraceColor);
             PlayerNode =  nodes.get(j);
             Thread.sleep(200);
         }
     }

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
   public void SetDefaultBotArea(){
       Random rand = new Random();
      while(true) {
          int RandNum = rand.nextInt(nodes.size() - 300);
          if(!nodes.get(RandNum).GetIs_passed()){
              for(int i =  nodes.get(RandNum).GetRow() ; i <= nodes.get(RandNum).GetRow() + 1; i++ ){
                  for(int j = nodes.get(RandNum).GetColumn(); j <= nodes.get(RandNum).GetColumn() + 1;j++){
                     for(int n = 0; n < nodes.size(); n++){
                          if(nodes.get(n).GetRow() == i  &&  nodes.get(n).GetColumn() == j){
                              tempnodes.add(nodes.get(n));
                          }
                     }
                  }
              }
              for(int j = 0 ; j < tempnodes.size();j++){
                  if(!tempnodes.get(j).GetIs_passed()){
                      is_empty = true;
                  }else{
                      is_empty = false;
                      break;
                  }
              }
          }
          if(is_empty){
            for(int i = 0; i < nodes.size();i++){
                for(int j = 0; j < tempnodes.size();j++){
                    if(nodes.get(i) == tempnodes.get(j)){
                        nodes.get(i).setColor(AreaColor);
                        nodes.get(i).SetIs_colored(true);
                        Owner.put(nodes.get(i),BotID);
                    }
                }
            }
            break;
          }

      }
  }

  public void SetPlayer(){
      for(int j = 0; j < nodes.size();j++){
          if(Owner.get(nodes.get(j)) == BotID){
              nodes.get(j).setColor(BOTColor);
              nodes.get(j).SetIs_player(true);
              PlayerNode = nodes.get(j);
              break;
          }
      }

    }
  public void EasyModeMovement(){
      Random random = new Random();
      int rand = random.nextInt(4);
      switch(rand){
          case(0): // UP


              break;

          case(1) : //RIGHT


              break;

          case(2): // DOWN


              break;

          case(3)://LEFT


              break;

          default:
              break;

      }

  }
}
