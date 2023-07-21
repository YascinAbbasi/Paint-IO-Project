package com.example.paintioproject;

import javafx.scene.paint.Color;

import java.util.ArrayList;
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
                            Owner.put(nodes.get(i), Bot.GetBotID());
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

    public void EasyModeMovement() throws InterruptedException {
        Random random = new Random();
        int rand = random.nextInt(4);
        int x = 3;
        int y = 4;
        int R = row_move;
        int C = column_move;
        row_move = PlayerNode.GetRow();
        column_move = PlayerNode.GetColumn();
        while (true) {
            x++;
            y++;
            C = column_move;
            R = row_move;
            switch (rand) {
                case (0): // UP

                    FindRowNode(row_move,column_move);
                    if(TempNode.GetIs_colored()) {
                       FindUnColoredRow(row_move,column_move,true);
                        R = row_move;
                    }
                    for(int i = R ; i >= R - x; i--){ // UP
                        FindRowNode(i,column_move);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        row_move--;
                        Thread.sleep(Speed);

                    }
                    R = row_move;
                    for(int i = C; i <= C + y;i++){  //RIGHT
                        FindColumnNode(row_move,i);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        column_move++;
                        Thread.sleep(Speed);
                    }
                    C = column_move;
                    for(int i = R;  i  <= R + y;i++){ //DOWN
                        FindRowNode(i,column_move);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        row_move++;
                        Thread.sleep(Speed);
                    }
                    R = row_move;
                    rand = 3;
                    break;

                case (1): //RIGHT
                    FindColumnNode(row_move,column_move);
                    if(TempNode.GetIs_colored()) {
                        FindUnColoredColumn(row_move,column_move,false);
                        C = column_move;
                    }
                    for(int i = C; i <= C + x;i++){  //RIGHT
                        FindColumnNode(row_move,i);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        column_move++;
                        Thread.sleep(Speed);
                    }
                    C = column_move;
                    for(int i = R;  i  <= R + y;i++){ //DOWN
                        FindRowNode(i,column_move);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        row_move++;
                        Thread.sleep(Speed);
                    }
                    R = row_move;
                    for(int i = C; i >= C - y;i--){ // LEFT
                        FindColumnNode(row_move,i);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        column_move--;
                        Thread.sleep(Speed);
                    }
                    C = column_move;
                    rand = 0;
                    break;

                case (2): // DOWN
                    FindRowNode(row_move,column_move);
                    if(TempNode.GetIs_colored()) {
                        FindUnColoredRow(row_move,column_move,false);
                        R = row_move;
                    }
                    for(int i = R;  i  <= R + x;i++){ //DOWN
                        FindRowNode(i,column_move);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        row_move++;
                        Thread.sleep(Speed);
                    }
                    R = row_move;
                    for(int i = C; i >= C - y;i--){ // LEFT
                        FindColumnNode(row_move,i);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        column_move--;
                        Thread.sleep(Speed);
                    }
                    C = column_move;
                    for(int i = R ; i >= R - y; i--){ // UP
                        FindRowNode(i,column_move);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        row_move--;
                        Thread.sleep(Speed);
                    }
                    R = row_move;
                    rand = 1;
                    break;

                case (3)://LEFT

                    FindColumnNode(row_move,column_move);
                    if(TempNode.GetIs_colored()) {
                        FindUnColoredColumn(row_move,column_move,true);
                        C = column_move;
                    }
                    for(int i = C; i >= C - x;i--){ // LEFT
                        FindColumnNode(row_move,i);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        column_move--;
                        Thread.sleep(Speed);
                    }
                    C = column_move;
                    for(int i = R ; i >= R - y; i--){ // UP
                        FindRowNode(i,column_move);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        row_move--;
                        Thread.sleep(Speed);
                    }
                    R = row_move;
                    for(int i = C; i <= C + y;i++){  //RIGHT
                        FindColumnNode(row_move,i);
                        TempNode.SetIs_player(true);
                        TempNode.setColor(Bot.GetBOTColor());
                        PlayerNode.SetIs_player(false);
                        PlayerNode.SetIs_passed(true);
                        PlayerNode.setColor(Bot.GetTraceColor());
                        PlayerNode = TempNode;
                        column_move++;
                        Thread.sleep(Speed);
                    }
                    C = column_move;

                    rand = 2;
                    break;

                default:
                    break;

            }

        }
    }

    public void SetPlayer() {
        for (int j = 0; j < nodes.size(); j++) {
            if (Owner.get(nodes.get(j)) == Bot.GetBotID()) {
                nodes.get(j).setColor(Bot.GetBOTColor());
                nodes.get(j).SetIs_player(true);
                PlayerNode = nodes.get(j);
                break;
            }
        }
    }
    public synchronized void FindRowNode(int row, int  column){
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
        System.out.println("GETTING OUT OF FIND");
    }

    public synchronized void FindColumnNode(int row, int  column){
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
        System.out.println("GETTING OUT OF FIND");
    }
    public void Switch(int A, int B){
        int temp = A;
         A = B;
         B = temp;

    }
    public void FindUnColoredRow(int row,int column,boolean UP) throws InterruptedException {
        while (TempNode.GetIs_colored()) {
            if(UP){
                row --;
            }else{
                row ++;
            }
            FindRowNode(row, column);
            TempNode.SetIs_player(true);
            TempNode.setColor(Bot.GetBOTColor());
            PlayerNode.SetIs_player(false);
            PlayerNode.setColor(Bot.GetAreaColor());
            //PlayerNode = null;
            PlayerNode = TempNode;
           // FindRowNode(row_move, column_move);
         //   System.out.println("R IM HERE");
            Thread.sleep(Speed);
         //   System.out.println(TempNode.GetIs_colored());
         //   System.out.println(TempNode.GetRow());

        }
      //  System.out.println("R GETTING OUT!");
    }
    public void FindUnColoredColumn(int row,int column,boolean LEFT) throws InterruptedException {
        while (TempNode.GetIs_colored()) {
            if(LEFT){
                 column--;
            }else{
                column++;
            }
            FindColumnNode(row, column);
            TempNode.SetIs_player(true);
            TempNode.setColor(Bot.GetBOTColor());
            PlayerNode.SetIs_player(false);
            PlayerNode.setColor(Bot.GetAreaColor());
            //PlayerNode = null;
            PlayerNode = TempNode;
           // FindRowNode(row_move, column_move);
          //  System.out.println("C IM HERE");
            Thread.sleep(Speed);
           // System.out.println(TempNode.GetIs_colored());
          //  System.out.println(TempNode.GetColumn());
        }
      //  System.out.println("C GETTING OUT!");
    }


    @Override
    public void run() {
        try {
            EasyModeMovement();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
