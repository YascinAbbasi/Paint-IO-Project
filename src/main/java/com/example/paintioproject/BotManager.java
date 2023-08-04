package com.example.paintioproject;

import javafx.scene.paint.Color;

import java.lang.invoke.VolatileCallSite;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class BotManager extends GameThings implements Runnable {
    private volatile BotPlayer Bot;
    private volatile Color color;

    private boolean ID_in_use = false;
    private boolean Color_in_use = false;
    private boolean is_empty = false;
    private boolean AmiDead =false;
   // private static boolean Restart = false;

    private  node PlayerNode;
    private  node TempNode;
    private  ArrayList<node> tempnodes = new ArrayList<>();
    private ArrayList  <node> UnableToKill = new ArrayList<>();
    public static ArrayList<String> PlayerID = new ArrayList<>();
    public static ArrayList<Color> AlreadyTakenColors = new ArrayList<>();
    public static ArrayList<String> AlreadyTakenIDs = new ArrayList<>();
    private volatile Color[] Colors = {Color.RED, Color.BLUE, Color.GREEN, Color.PURPLE};

    private String Difficulty = "EASY";

    private volatile int row_move;
    private volatile int column_move;
    private volatile int steps = 0;
    private  static int x = 5;
    private  static int y = 7;
    private volatile int BotScore = 0;
    private int Speed;


    public BotManager(BotPlayer Bot) {
        this.Bot = Bot;
    }

    public synchronized void SetBotID() {
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
    public void setDifficulty (String Difficulty){
        this.Difficulty = Difficulty;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public synchronized void SetBotColor() { //RANG
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

    public synchronized void SetDefaultBotArea() {
        Random rand = new Random();
        while (true) {
            tempnodes.clear();
            int RandNum = rand.nextInt(nodes.size());
            if (!nodes.get(RandNum).GetIs_passed() && !nodes.get(RandNum).GetIs_colored()) {  //?????????????
                for (int i = nodes.get(RandNum).GetRow(); i <= nodes.get(RandNum).GetRow() + 1; i++) {
                    for (int j = nodes.get(RandNum).GetColumn(); j <= nodes.get(RandNum).GetColumn() + 1; j++) {
                        for (int n = 0; n < nodes.size(); n++) {
                            if (nodes.get(n).GetRow() == i && nodes.get(n).GetColumn() == j ) { //?????????????????????
                                tempnodes.add(nodes.get(n));
                                break;/////////////////////////////
                            }
                        }
                    }
                }if(tempnodes.size() <4){
                    tempnodes.clear();
                    continue;
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
             /*for (int i = 0; i < nodes.size(); i++) {
                    for (int j = 0; j < tempnodes.size(); j++) {
                        if (nodes.get(i) == tempnodes.get(j)) {
                            nodes.get(i).setColor(Bot.GetAreaColor());
                            nodes.get(i).SetIs_colored(true);
                            nodes.get(i).SetPrevious();
                            Owner.put(nodes.get(i), Bot.GetBotID());
                        }
                    }
                }*/
                  for(int j = 0; j < tempnodes.size();j++) {
                      tempnodes.get(j).setColor(Bot.GetAreaColor());
                      tempnodes.get(j).SetIs_colored(true);
                      tempnodes.get(j).SetPrevious();
                      Owner.put(tempnodes.get(j), Bot.GetBotID());
                  }
                 SetPlayer();
                break;
                }
                          //  SetPlayerCordinates_R(PlayerNode.GetRow());
                          // SetPlayerCordinates_C();
            // break;
            //}

        }
    }

    public void AddPlayerID(int playernum) {
        for (int i = 2; i <= playernum + 1; i++) {
            PlayerID.add("PLAYER" + i);
        }
        System.out.println(PlayerID);
    }
    public synchronized void SetPlayerCordinates_R(int row_move){
        this.row_move = row_move;
    }
    public synchronized void SetPlayerCordinates_C (int column_move){
        this.column_move = column_move;
    }

    public synchronized void EasyModeMovement() throws InterruptedException {
        Random random = new Random();
        int rand = random.nextInt(4);
        column_move = PlayerNode.GetColumn();
        row_move = PlayerNode.GetRow();
         x = 5;
         y = 7;
       // RepairColor();
        while (true) {
            switch (rand) {
                case (0):  //UP

                    MoveUP(row_move,column_move,x,Difficulty);
                    MoveRIGHT(row_move,column_move,y,Difficulty);
                    MoveDOWN(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 0");
                    rand = 2;
                    break;

                case (1):  //DOWN

                    MoveDOWN(row_move,column_move,x,Difficulty);
                    MoveLEFT(row_move,column_move,y,Difficulty);
                    MoveUP(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 1");

                    rand = 3;
                    break;

                case (2):  //LEFT

                    MoveLEFT(row_move,column_move,x,Difficulty);
                    MoveUP(row_move,column_move,y,Difficulty);
                    MoveRIGHT(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 2");
                    rand = 1;
                    break;

                case (3):  //RIGHT

                    MoveRIGHT(row_move,column_move,x,Difficulty);
                    MoveDOWN(row_move,column_move,y,Difficulty);
                    MoveLEFT(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 3");
                    rand = 0;
                    break;

            }

            x++;
            y++;
        }
    }
    public synchronized void NormalModeMovement() throws InterruptedException{

        Random random = new Random();
        int rand = random.nextInt(4);
        column_move = PlayerNode.GetColumn();
        row_move = PlayerNode.GetRow();
        int x = 3;
        int y = 5;
       // RepairColor();
        while(true){
            switch (rand) {
                case (0):  //UP
                    MoveUP(row_move,column_move,x,Difficulty);
                    MoveRIGHT(row_move,column_move,y,Difficulty);
                    MoveDOWN(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 0");
                    rand = 2;
                    break;

                case (1):  //DOWN
                    MoveDOWN(row_move,column_move,x,Difficulty);
                    MoveLEFT(row_move,column_move,y,Difficulty);
                    MoveUP(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 1");
                    rand = 3;
                    break;

                case (2):  //LEFT
                    MoveLEFT(row_move,column_move,x,Difficulty);
                    MoveUP(row_move,column_move,y,Difficulty);
                    MoveRIGHT(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 2");
                    rand = 1;
                    break;

                case (3):  //RIGHT
                    MoveRIGHT(row_move,column_move,x,Difficulty);
                    MoveDOWN(row_move,column_move,y,Difficulty);
                    MoveLEFT(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 3");
                    rand = 0;
                    break;


            }

            x++;
            y++;
        }
    }
    public synchronized void HardMoveMovement() throws InterruptedException {

        Random random = new Random();
        int rand = random.nextInt(4);
        column_move = PlayerNode.GetColumn();
        row_move = PlayerNode.GetRow();
        int x = 3;
        int y = 5;
      //  RepairColor();
        while(true){
            switch (rand) {
                case (0):  //UP
                    MoveUP(row_move,column_move,x,Difficulty);
                    MoveRIGHT(row_move,column_move,y,Difficulty);
                    MoveDOWN(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 0");
                    rand = 2;
                    break;

                case (1):  //DOWN
                    MoveDOWN(row_move,column_move,x,Difficulty);
                    MoveLEFT(row_move,column_move,y,Difficulty);
                    MoveUP(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 1");
                    rand = 3;
                    break;

                case (2):  //LEFT
                    MoveLEFT(row_move,column_move,x,Difficulty);
                    MoveUP(row_move,column_move,y,Difficulty);
                    MoveRIGHT(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 2");
                    rand = 1;
                    break;

                case (3):  //RIGHT
                    MoveRIGHT(row_move,column_move,x,Difficulty);
                    MoveDOWN(row_move,column_move,y,Difficulty);
                    MoveLEFT(row_move,column_move,y,Difficulty);
                    System.out.println("EXITING CASE 3");
                    rand = 0;
                    break;


            }

            x++;
            y++;
        }
    }

    public synchronized void SetPlayer() {
      /* tempnodes.get(0).setColor(Bot.GetBOTColor());
       tempnodes.get(0).SetIs_player(true);
       PlayerNode = tempnodes.get(0);
        SetPlayerCordinates_C(PlayerNode.GetColumn());
        SetPlayerCordinates_R(PlayerNode.GetRow());
        tempnodes.clear();*/


        for (int j = 0; j < nodes.size(); j++) {
            if (Objects.equals(Owner.get(nodes.get(j)), Bot.GetBotID())) {  /////////////////
                nodes.get(j).setColor(Bot.GetBOTColor());
                nodes.get(j).SetIs_player(true);
                PlayerNode = nodes.get(j);
                SetPlayerCordinates_C(PlayerNode.GetColumn());
                SetPlayerCordinates_R(PlayerNode.GetRow());
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

    public synchronized void FindUnColoredRow(int row_move,int column_move,boolean UP) throws InterruptedException { //RE CHECK ??
        while(PlayerNode.GetIs_colored() && Objects.equals(Owner.get(PlayerNode), Bot.GetBotID())){
            if(UP){
                row_move --;
            }else{
                row_move ++;
            }
            TempNode = FindRowNode(row_move, column_move);
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
            Thread.sleep(Speed);
            System.out.println("UNCOLORED CALLED");
        }

       SetPlayerCordinates_R(PlayerNode.GetRow()); //row
      //  System.out.println("R GETTING OUT!");

    }
    public synchronized void FindUnColoredColumn(int row_move,int column_move,boolean LEFT) throws InterruptedException { //RE CHECK ??
        while(PlayerNode.GetIs_colored() && Objects.equals(Owner.get(PlayerNode), Bot.GetBotID())){
            if(LEFT){
                 column_move--;
            }else{
                column_move++;
            }
            TempNode = FindColumnNode(row_move, column_move);
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
            Thread.sleep(Speed);
            System.out.println("UNCOLORED CALLED");
        }

        SetPlayerCordinates_C(PlayerNode.GetColumn()); //COL
    }


    @Override
    public  void run() {
        if(Difficulty == "EASY") {
            while(true) {
                try {
                    //RepairColor();
                    EasyModeMovement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("RESTARTTTTTTTTTTTT");
            }
        }
        else if(Difficulty == "NORMAL"){
            while (true) {
                try {
                    // RepairColor();
                    NormalModeMovement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("RESTARTTTTTTTTTTTT");
            }
        }
        else if(Difficulty == "HARD"){
            while (true) {
                try {
                    HardMoveMovement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("RESTARTTTTTTTTTTTT");
            }

        }
    }
    public synchronized void MoveUP(int row, int column, int step,String difficulty) throws InterruptedException {
        int Counter = 1;
        while(Counter <= step){
            TempNode = FindRowNode(row_move,column_move);
           // if(Restart) {
                if (TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())) {
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    FindUnColoredRow(row_move, column_move, true);
                }
           // }
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
          //  if(Restart){
                //break;
           // }
            row_move--;
            Counter++;
            steps++;
            Thread.sleep(Speed);
            System.out.println("MOVE UP");

            if(difficulty == "EASY"){
                if(steps >= 50){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }
             else if(difficulty == "NORMAL") {
                if(steps % 8 == 0){
                    FindEnemy(row_move,column_move,5);
                }
                if (steps >= 30) {
                     LocateMyArea(Bot.GetBotID());
                      color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                     ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }
            else if(difficulty == "HARD"){
                if(steps % 5 == 0){
                    FindEnemy(row_move,column_move,10);
                }
                if(steps >= 20){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    SetSteps(0);
                }

            }
        }
        SetPlayerCordinates_R(row_move); //ROWM
       // SetPlayerCordinates_C(PlayerNode.GetColumn());
        SetSteps(steps);


    }
    public synchronized void MoveDOWN(int row, int column,int step,String difficulty)throws  InterruptedException{
        int Counter = 1;
        while(Counter <= step){
            TempNode = FindRowNode(row_move,column_move);
          //  if(!Restart) {
                if (TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())) { // ????????????
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    // FindEnemy(PlayerNode.GetRow(),PlayerNode.GetColumn(),5);
                    FindUnColoredRow(row_move, column_move, false);
                    //TempNode = FindRowNode(row_move,column);
                }
          //  }
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
          ///  if(Restart){
           //     break;
          //  }
            row_move++;
            Counter++;
            steps++;
            Thread.sleep(Speed);
            System.out.println("MOVE DOWN");

            if(difficulty == "EASY"){
                if(steps >= 50){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }
             else if(difficulty == "NORMAL") {
                if(steps % 8 == 0){
                    FindEnemy(row_move,column_move,5);
                }
                if (steps >= 30) {
                     LocateMyArea(Bot.GetBotID());
                     color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                     ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }
            else if(difficulty == "HARD"){
                if(steps % 5 == 0){
                    FindEnemy(row_move,column_move,10);
                }
                if(steps >= 20){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    SetSteps(0);
                }

            }

        }
       SetPlayerCordinates_R(row_move); //ROWM
       // SetPlayerCordinates_C(PlayerNode.GetColumn());
        SetSteps(steps);
    }
    public synchronized void MoveLEFT(int row, int column,int step,String difficulty) throws InterruptedException{
        int Counter = 1;
        while(Counter <= step) {
            //if (!Restart) {
                TempNode = FindColumnNode(row_move, column_move);
            if (TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())) { // ????????????
                color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                // FindEnemy(PlayerNode.GetRow(),PlayerNode.GetColumn(),5);
                FindUnColoredColumn(row_move, column_move, true);
                //TempNode = FindColumnNode(row,column_move);
           // }
        }
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
           // if(Restart){
           //     break;
           // }
            column_move--;
            Counter++;
            steps++;
            Thread.sleep(Speed);
            System.out.println("MOVE LEFT");

            if(difficulty == "EASY"){
                if(steps >= 50){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }
             else if(difficulty == "NORMAL") {
                if(steps % 8 == 0){
                    FindEnemy(row_move,column_move,5);
                }
                if (steps >= 30) {
                      LocateMyArea(Bot.GetBotID());
                      color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                     ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }
            else if(difficulty == "HARD"){
                if(steps % 5 == 0){
                    FindEnemy(row_move,column_move,10);
                }
                if(steps >= 20){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    SetSteps(0);
                }

            }
        }
      // SetPlayerCordinates_R(PlayerNode.GetRow()); //ROWM
        SetPlayerCordinates_C(column_move); //COLM
        SetSteps(steps);
    }
    public synchronized void MoveRIGHT(int row, int column,int step,String difficulty) throws InterruptedException{
        int Counter = 1;
        while(Counter <= step){
            TempNode = FindColumnNode(row_move,column_move);
           // if(!Restart) {
                if (TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())) { //?????????????????
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    //  FindEnemy(PlayerNode.GetRow(),PlayerNode.GetColumn(),5);
                    FindUnColoredColumn(row_move, column_move, false);
                    //  TempNode = FindColumnNode(row,column_move);
                }
          //  }
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);
        //   if(Restart){
          //      break;
          //  }
            column_move++;
            Counter++;
            steps++;
            Thread.sleep(Speed);
            System.out.println("MOVE RIGHT");

            if(difficulty == "EASY"){
                if(steps >= 50){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }

            else if(difficulty =="NORMAL") {
                if(steps % 8 == 0){
                    FindEnemy(row_move,column_move,5);
                }
                if (steps >= 30) {
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    SetSteps(0);
                }
            }
            else if(difficulty == "HARD"){
                if(steps % 5 == 0){
                    FindEnemy(row_move,column_move,10);
                }
                if(steps >= 20){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    SetSteps(0);
                }

            }
        }
      //  SetPlayerCordinates_R(PlayerNode.GetRow()); //ROWM
        SetPlayerCordinates_C(column_move); //COLM
        SetSteps(steps);
    }

    public synchronized node SwitchPlayer(node player, node temp) throws InterruptedException {
        AmiDead = AmIDead(Bot.GetBotID());
        if(AmiDead){
            Reset(temp, player);
            ReGenerate(false);
            return PlayerNode;
        }else {
            if (temp.GetIs_colored() && Owner.get(temp) != Bot.GetBotID()) {
              //  temp.SetPrevious(); //&&&&&&&&&&&&&
            }
            temp.SetIs_player(true);
            player.SetIs_player(false);
            temp.setColor(Bot.GetBOTColor());
            if (temp.GetIs_colored() && Owner.get(temp) == Bot.GetBotID()) {
                player.setColor(Bot.GetAreaColor());
            } else {
                player.setColor(Bot.GetTraceColor());
            }
            //  ChecktoKill(temp,Bot.GetBotID());
            temp.setOwnerID(Bot.GetBotID());
                if(!temp.GetIs_colored() ||(temp.GetIs_colored() && Owner.get(temp) != Bot.GetBotID() )){
                    player.SetIs_passed(true);
                    ResetKillIt();
           }else {
                    player.SetIs_passed(true);
                    player.SetKillIt(false);
            }
            //AmiDead = AmIDead(Bot.GetBotID()); //@@@@@@@@@@
            temp.setOwnerID(Bot.GetBotID());
            player.setOwnerID(Bot.GetBotID());
            player = temp;
            //  player.setOwnerID(Bot.GetBotID());
            SetBotScore(CheckScore(Bot.GetBotID()));
            System.out.println( "BOT SCORE : " + BotScore);
            return player;
        }
    }
   /* public synchronized node SwitchPlayer(node player,node temp) throws InterruptedException {
        AmiDead = AmIDead(Bot.GetBotID());
        if(AmiDead){
            Reset(temp,player);
            ReGenerate(false);
            player = PlayerNode;
            return player;
        }else{
            player.SetIs_player(false);
            if (temp.GetIs_colored() && Owner.get(temp).equals(Bot.GetBotID())) {
                player.setColor(Bot.GetAreaColor());
            } else {
                player.setColor(Bot.GetTraceColor());
            }
            player.setOwnerID(Bot.GetBotID()); //////
            player = temp;
            player.SetIs_player(true);
            player.setColor(Bot.GetBOTColor());
            //ChecktoKill(player,Bot.GetBotID());
            player.setOwnerID(Bot.GetBotID());
            SetBotScore(CheckScore(Bot.GetBotID()));
            System.out.println( "BOT SCORE : " + BotScore);
            return player;
        }
    }*/
    public synchronized void LocateMyArea(String ID) throws InterruptedException {  //RE CHECK ??
        boolean AlreadyColored = false;
        node TempNode;
        int MinRow = 1000000;
        int MinColumn = 1000000;
        for (int j = 0; j < nodes.size(); j++) {
            if (Objects.equals(Owner.get(nodes.get(j)), ID)) {
                if (Math.abs(PlayerNode.GetRow() - nodes.get(j).GetRow()) < MinRow) {
                    MinRow = nodes.get(j).GetRow();
                }
                if (Math.abs(PlayerNode.GetColumn() - nodes.get(j).GetColumn()) < MinColumn) {
                    MinColumn = nodes.get(j).GetColumn();
                }
            }
        }
        System.out.println( " MIN LOCATE ROW : " + MinRow);
        System.out.println("MIN LOCATE COL "+MinColumn);
        if (MinRow < PlayerNode.GetRow()) {
            for(int i = PlayerNode.GetRow() - 1; i >= MinRow;i--){
                TempNode = FindRowNode(i,PlayerNode.GetColumn());
                if(TempNode.GetIs_colored() && Owner.get(TempNode) == ID && !AlreadyColored){
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    AlreadyColored = true;
                }else if((TempNode.GetIs_colored() && Owner.get(TempNode) != ID) || !TempNode.GetIs_colored()){
                    AlreadyColored = false;

                }
                PlayerNode = SwitchPlayer(PlayerNode,TempNode);
              //  if(Restart){
               //     break;
              //  }
                 Thread.sleep(Speed);
              //  SetPlayerCordinates_R(PlayerNode.GetRow());
            }
            AlreadyColored = false;
        }
        else if(MinRow >= PlayerNode.GetRow()){
            for(int i = PlayerNode.GetRow() + 1;i <= MinRow;i++){
                TempNode = FindRowNode(i,PlayerNode.GetColumn());
                if(TempNode.GetIs_colored() && Owner.get(TempNode) == ID && !AlreadyColored){
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    AlreadyColored = true;
                }else if((TempNode.GetIs_colored() && Owner.get(TempNode) != ID) || !TempNode.GetIs_colored()){
                    AlreadyColored = false;

                }
                PlayerNode =  SwitchPlayer(PlayerNode,TempNode);
               // if(Restart){
               //     break;
              //  }
                Thread.sleep(Speed);
               // SetPlayerCordinates_R(PlayerNode.GetRow());
            }
            AlreadyColored = false;
        }
        if(MinColumn < PlayerNode.GetColumn()){
            for(int i = PlayerNode.GetColumn() - 1; i >= MinColumn; i--){
                TempNode = FindColumnNode(PlayerNode.GetRow(),i);
                if(TempNode.GetIs_colored() && Owner.get(TempNode) == ID && !AlreadyColored){
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    AlreadyColored = true;
                }else if((TempNode.GetIs_colored() && Owner.get(TempNode) != ID) || !TempNode.GetIs_colored()){
                    AlreadyColored = false;

                }
                PlayerNode =  SwitchPlayer(PlayerNode,TempNode);
              //  if(Restart){
               //     break;
             //  }
                Thread.sleep(Speed);
               // SetPlayerCordinates_C(PlayerNode.GetColumn());
            }
            AlreadyColored = false;
        }
        else if(MinColumn >= PlayerNode.GetColumn()){
            for(int i = PlayerNode.GetColumn() + 1; i <= MinColumn;i++){
                TempNode = FindColumnNode(PlayerNode.GetRow(),i);
                if(TempNode.GetIs_colored() && Owner.get(TempNode) == ID && !AlreadyColored){
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    AlreadyColored = true;
                }else if((TempNode.GetIs_colored() && Owner.get(TempNode) != ID) || !TempNode.GetIs_colored()){
                    AlreadyColored = false;
                }
                PlayerNode =  SwitchPlayer(PlayerNode,TempNode);
               // if(Restart){
               //     break;
              //  }
                Thread.sleep(Speed);
              //  SetPlayerCordinates_C(PlayerNode.GetColumn());
            }
            AlreadyColored = false;
        }
        SetPlayerCordinates_R(PlayerNode.GetRow());
        SetPlayerCordinates_C(PlayerNode.GetColumn());
    }

   /* public void RepairColor(){
        for(int i = PlayerNode.GetRow(); i <= PlayerNode.GetRow() + 1;i++){
            for(int j = PlayerNode.GetColumn(); j <= PlayerNode.GetColumn() + 1;j++){
                for(int n = 0 ; n < nodes.size();n++){
                    if(nodes.get(n).GetRow() == i && nodes.get(n).GetColumn() == j && !nodes.get(n).Getis_player()){
                        nodes.get(n).setColor(Bot.GetAreaColor());
                    }
                }
            }
        }
    }*/
    public synchronized void FindEnemy(int row , int column , int Sensitivity) throws InterruptedException {
        node EnemyTrace = null;
        node TempNode;
        String Direction = "";
        int KillSpeed;

        for(int i = row;i >= row - Sensitivity;i--){  //UP CHECK
            TempNode = FindTemp(i,column);
            if(TempNode != null){
                if(TempNode.GetIs_passed() && TempNode.GetColor() != Bot.GetTraceColor()){
                    EnemyTrace = TempNode;
                    Direction = "UP";
                    break;
                }
            }else{
                break;
            }
        }
        for(int i = row; i<= row + Sensitivity;i--){   //DOWN CHECK
            TempNode = FindTemp(i,column);
            if(TempNode != null){
                if(TempNode.GetIs_passed() && TempNode.GetColor() != Bot.GetTraceColor()){
                    EnemyTrace = TempNode;
                    Direction = "DOWN";
                    break;
                }
            }else{
                break;
            }
        }
        for(int i = column; i >= column - Sensitivity;i--){  //LEFT CHECK
            TempNode = FindTemp(row,i);
            if(TempNode != null){
                if(TempNode.GetIs_passed() && TempNode.GetColor() != Bot.GetTraceColor()){
                    EnemyTrace = TempNode;
                    Direction = "LEFT";
                    break;
                }
            }else{
                break;
            }
        }
        for(int i = column; i <= column + Sensitivity;i++){   //RIGHT CHECK
            TempNode = FindTemp(row,i);
            if(TempNode != null){
                if(TempNode.GetIs_passed() && TempNode.GetColor() != Bot.GetTraceColor()){
                    EnemyTrace = TempNode;
                    Direction = "RIGHT";
                    break;
                }
            }else{
                break;
            }
        }
        if(EnemyTrace != null){
            System.out.println("FIND ENEMY CALLED!");
            if(Difficulty == "HARD"){
              KillSpeed = Speed / 2;
            }else{
                KillSpeed = Speed;
            }
            switch (Direction){

                case("UP"):
                    for(int i = row; i >= EnemyTrace.GetRow();i--){
                        TempNode = FindTemp(i,column);
                        PlayerNode = SwitchPlayer(PlayerNode,TempNode);
                       // if(Restart){
                        //    break;
                       // }
                        Thread.sleep(KillSpeed);
                        }
                    break;

                case("DOWN"):
                    for(int i = row; i <= EnemyTrace.GetRow();i++){
                        TempNode = FindTemp(i,column);
                       PlayerNode =  SwitchPlayer(PlayerNode,TempNode);
                       // if(Restart){
                        //    break;
                       // }
                        Thread.sleep(KillSpeed);
                    }
                    break;

                case("LEFT"):
                    for(int i = column; i >= EnemyTrace.GetColumn();i--){
                        TempNode = FindTemp(row,i);
                        PlayerNode = SwitchPlayer(PlayerNode,TempNode);
                      //  if(Restart){
                       //     break;
                     //   }
                        Thread.sleep(KillSpeed);
                    }
                    break;

                case("RIGHT"):
                    for(int i = column; i <= EnemyTrace.GetColumn();i++){
                        TempNode = FindTemp(row,i);
                        PlayerNode = SwitchPlayer(PlayerNode,TempNode);
                      //  if(Restart){
                       //     break;
                      //  }
                        Thread.sleep(KillSpeed);

                    }
                    break;

                default:
                    break;
            }
            SetPlayerCordinates_R(PlayerNode.GetRow());
            SetPlayerCordinates_C(PlayerNode.GetColumn());
        }else {
            System.out.println("ENEMY NOT FOUND!!");
        }
    }
    public synchronized void SetSteps(int steps){
    this.steps =  steps;
    }

    public synchronized void ReGenerate(boolean firsttime) throws InterruptedException {
        if(!firsttime) {
            Thread.sleep(3000);
        }
       // SetBotColor();
       // SetBotID();
       // PlayerNode = null;
        SetDefaultBotArea();
        //SetPlayer();
        SetSteps(0);

        SetPlayerCordinates_C(PlayerNode.GetColumn());
        SetPlayerCordinates_R(PlayerNode.GetRow());
       // SetRestart(false);

       //row_move = PlayerNode.GetRow();
      //column_move = PlayerNode.GetColumn();

    }
    public synchronized void Reset(node temp,node player){
        if(temp.GetIs_colored() && Owner.get(temp) != Bot.GetBotID()){ ///////////
            player.SetIs_player(false);
            temp.ResetToPrevious();
            player.ResetToPrevious();
        }else if(temp.GetIs_colored() && Owner.get(temp) == Bot.GetBotID()){
            player.SetIs_player(false);
            temp.ResetoDefault();
            player.ResetoDefault();
        }else if(!temp.GetIs_colored()){
            player.SetIs_player(false);
            temp.ResetoDefault();
            player.ResetoDefault();
        }
       /* for(int j = 0; j < nodes.size();j++){
            if(nodes.get(j).Getis_player() || nodes.get(j).GetColor() == Bot.GetBOTColor()){
                nodes.get(j).ResetoDefault();
            }
        }*/
        if(Difficulty == "EASY"){
            SetX(5);
            SetY(7);
            //x = 5;
           // y = 7;
        }else{
            SetX(3);
            SetY(5);
            //  x = 3;
            //y = 5;
        }
    }
   /* public void SetRestart( boolean Restart){
        this.Restart = Restart;
    }*/
    public synchronized void SetBotScore(int BotScore){
        this.BotScore = BotScore;
    }

    public synchronized int GetBotScore() {
        return BotScore;
    }
    public synchronized void SetX(int x){
        this.x = x;
    }
    public synchronized void SetY(int y){
        this.y = y;
    }
    public synchronized void ResetKillIt(){
        for(int j = 0; j < nodes.size(); j++){
            if(!nodes.get(j).GetKillIt()){
                nodes.get(j).SetKillIt(true);
                //nodes.get(j).SetIs_passed(false);
            }
        }
    }
}
