package com.example.paintioproject;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Objects;
import java.util.Random;

public class BotManager extends GameThings implements Runnable {
    private BotPlayer Bot;
    private Color color;
   // private Color BOTColor;
  //  private Color AreaColor;
 //   private Color TraceColor;
    private boolean ID_in_use = false;
    private boolean Color_in_use = false;
    private boolean is_empty = false;

    private node PlayerNode;
    private node TempNode;
    private ArrayList<node> tempnodes = new ArrayList<>();
    public static ArrayList<String> PlayerID = new ArrayList<>();
    public static ArrayList<Color> AlreadyTakenColors = new ArrayList<>();
    public static ArrayList<String> AlreadyTakenIDs = new ArrayList<>();
    private Color[] Colors = {Color.RED, Color.BLUE, Color.GREEN, Color.PURPLE};

    private int row_move = 0;
    private int column_move = 0;
    private int Speed;


    public BotManager(BotPlayer Bot) {
        this.Bot = Bot;
    }

    public void SetBotID() {
        for (int i = 0; i < PlayerID.size(); i++) {
            for (int j = 0; j < AlreadyTakenIDs.size(); j++) {
                if (PlayerID.get(i) == AlreadyTakenIDs.get(j)) {
                    ID_in_use = true;
                    break;
                } else {
                    ID_in_use = false;
                }
            }
            if (!ID_in_use) {
                Bot.SetBotID(PlayerID.get(i));
                System.out.println(Bot.GetBotID());
                AlreadyTakenIDs.add(PlayerID.get(i));
                break;
            }
        }
    }

    public void setSpeed(int Speed){
        this.Speed = Speed;
    }

    public int getSpeed() {
        return Speed;
    }

    public void SetBotColor() { //RANG
        for (int i = 0; i < Colors.length; i++) {
            for (int j = 0; j < AlreadyTakenColors.size(); j++) {
                if (Colors[i] == AlreadyTakenColors.get(j)) {
                    Color_in_use = true;
                    break;
                } else {
                    Color_in_use = false;
                }
            }
            if (!Color_in_use) {
                color = Colors[i];
                AlreadyTakenColors.add(color);
                break;
            }
        }
        if (color == Color.RED) {

            Bot.SetBotColor(Color.DARKRED);
            Bot.SetAreaColor(color);
            Bot.SetTraceColor(Color.CORAL);
        } else if (color == Color.BLUE) {

            Bot.SetBotColor(Color.DARKBLUE);
            Bot.SetAreaColor(color);
            Bot.SetTraceColor(Color.LIGHTBLUE);

        } else if (color == Color.GREEN) {
            Bot.SetBotColor(Color.DARKGREEN);
            Bot.SetAreaColor(color);
            Bot.SetTraceColor(Color.LIGHTGREEN);

        } else if (color == Color.PURPLE) {
            Bot.SetBotColor(Color.PURPLE);
            Bot.SetAreaColor(Color.MEDIUMPURPLE);
            Bot.SetTraceColor(Color.LAVENDER);


        }

    }
    public String GETbotID(){
        return Bot.GetBotID();
    }

    public void SetDefaultBotArea() {
        tempnodes.clear();
        Random rand = new Random();
        while (true) {
            int RandNum = rand.nextInt(nodes.size() - 100);
            if (!nodes.get(RandNum).GetIs_passed()) {
                for (int i = nodes.get(RandNum).GetRow(); i <= nodes.get(RandNum).GetRow() + 1; i++) {
                    for (int j = nodes.get(RandNum).GetColumn(); j <= nodes.get(RandNum).GetColumn() + 1; j++) {
                        for (int n = 0; n < nodes.size(); n++) {
                            if (nodes.get(n).GetRow() == i && nodes.get(n).GetColumn() == j) {
                                tempnodes.add(nodes.get(n));
                            }
                        }
                    }
                }
                for (int j = 0; j < tempnodes.size(); j++) {
                    if (!tempnodes.get(j).GetIs_passed()) {
                        is_empty = true;
                    } else {
                        is_empty = false;
                        break;
                    }
                }
            }
            if (is_empty) {
                for (int i = 0; i < nodes.size(); i++) {
                    for (int j = 0; j < tempnodes.size(); j++) {
                        if (nodes.get(i) == tempnodes.get(j)) {
                            nodes.get(i).setColor(Bot.GetAreaColor());
                            nodes.get(i).SetIs_colored(true);
                            Owner.put(nodes.get(i), Bot.GetBotID());  //?????????
                        }
                    }
                }
                SetPlayer();
                break;
            }

        }
    }

    public void AddPlayerID(int playernum) {
        for (int i = 2; i <= playernum + 1; i++) {
            PlayerID.add("PLAYER" + i);
        }
        System.out.println(PlayerID);
    }
    public void SetPlayerCordinates_R(int row_move){
        this.row_move = row_move;
    }
    public void SetPlayerCordinates_C (int column_move){
        this.column_move = column_move;
    }

    public void EasyModeMovement() throws InterruptedException {
        Random random = new Random();
        int rand = random.nextInt(4);
        column_move = PlayerNode.GetColumn();
        row_move = PlayerNode.GetRow();
        while (true) {
            switch (rand) {
                case (0):  //UP
                    //  column_move = PlayerNode.GetColumn();
                 //  row_move = PlayerNode.GetRow();
                  //  FindRowNode(row_move,column_move);
                  //  if(TempNode.GetIs_colored()) {
                 //       FindUnColoredRow(row_move, column_move, true);
                 //   }
                    MoveUP(row_move,column_move,8);
                    MoveRIGHT(row_move,column_move,8);
                    MoveDOWN(row_move,column_move,8);
                    System.out.println("EXITING CASE 0");
                    rand = 2;
                    break;

                case (1):  //DOWN
                  // column_move = PlayerNode.GetColumn();
                   // row_move = PlayerNode.GetRow();
                   // FindRowNode(row_move,column_move);
                  //  if(TempNode.GetIs_colored()) {
                   //     FindUnColoredRow(row_move, column_move, false);
                  //  }
                    MoveDOWN(row_move,column_move,8);
                    MoveLEFT(row_move,column_move,8);
                    MoveUP(row_move,column_move,8);
                    System.out.println("EXITING CASE 1");

                    rand = 3;
                    break;

                case (2):  //LEFT
                   // column_move = PlayerNode.GetColumn();
                  //  row_move = PlayerNode.GetRow();
                  // FindColumnNode(row_move,column_move);
                  //  if(TempNode.GetIs_colored()) {
                    //    FindUnColoredColumn(row_move,column_move,true);
                  //  }
                    MoveLEFT(row_move,column_move,8);
                    MoveUP(row_move,column_move,8);
                    MoveRIGHT(row_move,column_move,8);
                    System.out.println("EXITING CASE 2");
                    rand = 1;
                    break;

                case (3):  //RIGHT
                  //  column_move = PlayerNode.GetColumn();
                   // row_move = PlayerNode.GetRow();
                 //   FindColumnNode(row_move,column_move);
                 //   if(TempNode.GetIs_colored()) {
                 //       FindUnColoredColumn(row_move,column_move,false);
                  //  }
                    MoveRIGHT(row_move,column_move,8);
                    MoveDOWN(row_move,column_move,8);
                    MoveLEFT(row_move,column_move,8);
                    System.out.println("EXITING CASE 3");
                    rand = 0;
                    break;

            }
        }
    }

    public void SetPlayer() {
        for (int j = 0; j < nodes.size(); j++) {
            if (Owner.get(nodes.get(j)) == Bot.GetBotID()) { // ???????????????
                nodes.get(j).setColor(Bot.GetBOTColor());
                nodes.get(j).SetIs_player(true);
                PlayerNode = nodes.get(j);
                break;
            }
        }
    }
    public synchronized node FindRowNode(int row, int  column){
        TempNode = null;
        while(true) {
            for (int j = 0; j < nodes.size(); j++) {
                if (nodes.get(j).GetRow() == row && nodes.get(j).GetColumn() == column) {
                    TempNode = nodes.get(j);
                    break;
                }
            }
            if (TempNode == null) {
                FindRowNodes(row, column, true);
            }
            else{
                break;
            }
        }
        System.out.println("R FIND");
        return TempNode;
       // System.out.println("GETTING OUT OF FIND");
    }

    public synchronized node FindColumnNode(int row, int  column){
        TempNode = null;
        while(true) {
            for (int j = 0; j < nodes.size(); j++) {
                if (nodes.get(j).GetRow() == row && nodes.get(j).GetColumn() == column) {
                    TempNode = nodes.get(j);
                    break;
                }
            }
            if (TempNode == null) {
                FindColumnNodes(row, column, true);
            }
            else{
                break;
            }
        }
        System.out.println("C FIND");
        return TempNode;
    }

    public void FindUnColoredRow(int row,int column,boolean UP) throws InterruptedException { //RE CHECK ??
        while(TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())){
            if(UP){
                row --;
            }else{
                row ++;
            }
            TempNode = FindRowNode(row, column);
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
            Thread.sleep(Speed);
            /*TempNode = FindRowNode(row, column);
            TempNode.SetIs_player(true);
            TempNode.setColor(Bot.GetBOTColor());
            Owner.put(TempNode,Bot.GetBotID());
            PlayerNode.SetIs_player(false);
            PlayerNode.setColor(Bot.GetAreaColor());*/
            //PlayerNode = null;
           // FindRowNode(row_move, column_move);
         //   System.out.println("R IM HERE");
         //   System.out.println(TempNode.GetIs_colored());
         //   System.out.println(TempNode.GetRow());
            System.out.println("UNCOLORED CALLED");
        }

        SetPlayerCordinates_R(row);
      //  System.out.println("R GETTING OUT!");

    }
    public void FindUnColoredColumn(int row,int column,boolean LEFT) throws InterruptedException { //RE CHECK ??
        while(TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())){
            if(LEFT){
                 column--;
            }else{
                column++;
            }
            TempNode = FindColumnNode(row, column);
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
            Thread.sleep(Speed);

            /*TempNode = FindColumnNode(row, column);
            TempNode.SetIs_player(true);
            TempNode.setColor(Bot.GetBOTColor());
            Owner.put(TempNode,Bot.GetBotID());
            PlayerNode.SetIs_player(false);
            PlayerNode.setColor(Bot.GetAreaColor());*/
            //PlayerNode = null;
           // FindRowNode(row_move, column_move);
          //  System.out.println("C IM HERE");
           // System.out.println(TempNode.GetIs_colored());
          //  System.out.println(TempNode.GetColumn());
            System.out.println("UNCOLORED CALLED");
        }

        SetPlayerCordinates_C(column);
    }


    @Override
    public void run() {
        try {
            EasyModeMovement();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void MoveUP(int row, int column,int step) throws InterruptedException {
        //for(int i = row  ; i >= row - step; i--){
        int Counter = 1;
            while(Counter <= step){
            TempNode = FindRowNode(row_move,column);
            if(TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())){ //?????????????
                color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
            //   FindUnColoredRow(row_move,column,true);
                TempNode = FindRowNode(row_move,column);
            }
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
            row_move--;
            Counter++;
            Thread.sleep(Speed);
            System.out.println("MOVE UP");
             /*TempNode.SetIs_player(true);
            TempNode.setColor(Bot.GetBOTColor());
            Owner.put(TempNode,Bot.GetBotID());
            if(TempNode.GetIs_colored()){
                color_the_path(Bot.GetAreaColor(),Bot.GetBotID());
            }
            PlayerNode.SetIs_player(false);
            PlayerNode.SetIs_passed(true);
            if(!PlayerNode.GetIs_colored() || !Objects.equals(Owner.get(PlayerNode), Bot.GetBotID())) {
                PlayerNode.setColor(Bot.GetTraceColor());
            }else{
                PlayerNode.setColor(Bot.GetAreaColor());
            }
            PlayerNode = TempNode;*/
            //row_move--;
           // Thread.sleep(Speed);
            //SetPlayerCordinates_R(row_move);
           // System.out.println("MOVE UP");
        }
        SetPlayerCordinates_R(row_move);
    }
    public void MoveDOWN(int row, int column,int step)throws  InterruptedException{
        //for(int i = row;  i  <= row + step;i++){
        int Counter = 1;
        while(Counter <= step){
            TempNode = FindRowNode(row_move,column);
            if(TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())){ // ????????????
                color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
             //   FindUnColoredRow(row,column,false);
                TempNode = FindRowNode(row_move,column);
            }
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
            row_move++;
            Counter++;
            Thread.sleep(Speed);
            System.out.println("MOVE DOWN");
           /* TempNode.SetIs_player(true);
            TempNode.setColor(Bot.GetBOTColor());
            Owner.put(TempNode,Bot.GetBotID());
            PlayerNode.SetIs_player(false);
            PlayerNode.SetIs_passed(true);
            if(!PlayerNode.GetIs_colored()) {
                PlayerNode.setColor(Bot.GetTraceColor());
            }else{
                PlayerNode.setColor(Bot.GetAreaColor());
            }
            PlayerNode = TempNode;*/
            //row_move++;
            //Thread.sleep(Speed);
            //SetPlayerCordinates_R(row_move);
          // System.out.println("MOVE DOWN");
        }
        SetPlayerCordinates_R(row_move);
    }
    public void MoveLEFT(int row, int column,int step) throws InterruptedException{
        //for(int i = column; i >= column - step;i--){
        int Counter = 1;
        while(Counter <= step){
            TempNode = FindColumnNode(row,column_move);
            if(TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())){ // ????????????
                color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
               // FindUnColoredColumn(row,column,true);
                TempNode = FindColumnNode(row,column_move);
            }
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
            column_move--;
            Counter++;
            Thread.sleep(Speed);
            System.out.println("MOVE LEFT");
            /*TempNode.SetIs_player(true);
            TempNode.setColor(Bot.GetBOTColor());
            Owner.put(TempNode,Bot.GetBotID());
            PlayerNode.SetIs_player(false);
            PlayerNode.SetIs_passed(true);
            if(!PlayerNode.GetIs_colored()) {
                PlayerNode.setColor(Bot.GetTraceColor());
            }else{
                PlayerNode.setColor(Bot.GetAreaColor());
            }
            PlayerNode = TempNode;*/
            //column_move--;
          //  Thread.sleep(Speed);
            //SetPlayerCordinates_C(column_move);
          //  System.out.println("MOVE LEFT");
        }
        SetPlayerCordinates_C(column_move);
    }
    public void MoveRIGHT(int row, int column,int step) throws InterruptedException{
        //for(int i = column; i <= column + step;i++){
        int Counter = 1;
        while(Counter <= step){
            TempNode = FindColumnNode(row,column_move);
            if(TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())){ //?????????????????
                color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
              //  FindUnColoredColumn(row,column,false);
                TempNode = FindColumnNode(row,column_move);
            }
              PlayerNode = SwitchPlayer(PlayerNode,TempNode);
            column_move++;
            Counter++;
            Thread.sleep(Speed);
            System.out.println("MOVE RIGHT");
           /* TempNode.SetIs_player(true);
            TempNode.setColor(Bot.GetBOTColor());
            Owner.put(TempNode,Bot.GetBotID());
            PlayerNode.SetIs_player(false);
            PlayerNode.SetIs_passed(true);*/
            /*if(!PlayerNode.GetIs_colored()) {
                PlayerNode.setColor(Bot.GetTraceColor());
            }else{
                PlayerNode.setColor(Bot.GetAreaColor());
            }*/
            //PlayerNode = TempNode;
           // column_move++;
          //  Thread.sleep(Speed);
            //SetPlayerCordinates_C(column_move);
           // System.out.println("MOVE RIGHT");
        }
        SetPlayerCordinates_C(column_move);
    }

    public node SwitchPlayer(node player, node temp){
       temp.SetIs_player(true);
       player.SetIs_player(false);
       temp.setColor(Bot.GetBOTColor());
       if(temp.GetIs_colored() && Owner.get(temp) == Bot.GetBotID()){
           player.setColor(Bot.GetAreaColor());
       } else {
           player.setColor(Bot.GetTraceColor());
       }
           player.SetIs_passed(true);

        player = temp;
        return player;
    }
    public void LocateMyArea(String ID) {  //RE CHECK ??
        node TempNode;
        int MinRow = 1000000;
        int MinColumn = 1000000;
        for (int j = 0; j < nodes.size(); j++) {
            if (Owner.get(nodes.get(j)) == ID) {
                if (Math.abs(PlayerNode.GetRow() - nodes.get(j).GetRow()) < MinRow) {
                    MinRow = nodes.get(j).GetRow();
                }
                if (Math.abs(PlayerNode.GetColumn() - nodes.get(j).GetColumn()) < MinColumn) {
                    MinColumn = nodes.get(j).GetColumn();
                }
            }
        }
        if (MinRow < PlayerNode.GetRow()) {
            for(int i = PlayerNode.GetRow(); i >= MinRow;i--){
             TempNode =  FindRowNode(i,PlayerNode.GetColumn());
              SwitchPlayer(PlayerNode,TempNode);
            }
        }else if(MinRow >= PlayerNode.GetRow()){
            for(int i = PlayerNode.GetRow();i <= MinRow;i++){
                TempNode =  FindRowNode(i,PlayerNode.GetColumn());
                SwitchPlayer(PlayerNode,TempNode);

            }
        }
        if(MinColumn < PlayerNode.GetColumn()){
            for(int i = PlayerNode.GetColumn(); i >= MinColumn; i--){
              TempNode = FindColumnNode(PlayerNode.GetRow(),i);
                SwitchPlayer(PlayerNode,TempNode);
            }

        }else if(MinColumn >= PlayerNode.GetColumn()){
            for(int i = PlayerNode.GetColumn(); i <= MinColumn;i++){
                TempNode = FindColumnNode(PlayerNode.GetRow(),i);
                SwitchPlayer(PlayerNode,TempNode);
            }
        }
    }

}
