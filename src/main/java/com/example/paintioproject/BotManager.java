package com.example.paintioproject;

import javafx.scene.paint.Color;

import java.lang.invoke.VolatileCallSite;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class BotManager extends GameThings implements Runnable { //this class handle the bot and its movement
    private volatile BotPlayer Bot;
    private volatile Color color;

    private boolean ID_in_use = false;
    private boolean Color_in_use = false;
    private boolean is_empty = false;
    private boolean AmiDead =false;

    private  node PlayerNode;
    private  node TempNode;
    private  ArrayList<node> tempnodes = new ArrayList<>();

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

    public synchronized void SetBotID() {      //setting a unique ID for each bot
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

    public synchronized void SetBotColor() {                   //this method gives each robot a unique list of colors to use in the game Algorithm
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

    public synchronized void SetDefaultBotArea() {  //This method sets a default area for the bots whenever they generate or regenerate.
        Random rand = new Random();
        while (true) {
            tempnodes.clear();
            int RandNum = rand.nextInt(nodes.size());
            if (!nodes.get(RandNum).GetIs_passed() && !nodes.get(RandNum).GetIs_colored()) {
                for (int i = nodes.get(RandNum).GetRow(); i <= nodes.get(RandNum).GetRow() + 1; i++) {
                    for (int j = nodes.get(RandNum).GetColumn(); j <= nodes.get(RandNum).GetColumn() + 1; j++) {
                        for (int n = 0; n < nodes.size(); n++) {
                            if (nodes.get(n).GetRow() == i && nodes.get(n).GetColumn() == j ) {
                                tempnodes.add(nodes.get(n));
                                break;
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
                  for(int j = 0; j < tempnodes.size();j++) {
                      tempnodes.get(j).setColor(Bot.GetAreaColor());
                      tempnodes.get(j).SetIs_colored(true);
                      tempnodes.get(j).SetIs_DefaultArea(true);
                      tempnodes.get(j).SetPrevious();
                      Owner.put(tempnodes.get(j), Bot.GetBotID());
                  }
                 SetPlayer();
                break;
                }

        }
    }

    public void AddPlayerID(int playernum) {   //This method generates unique IDs to be assigned later
        for (int i = 2; i <= playernum + 1; i++) {
            PlayerID.add("PLAYER" + i);
        }
        System.out.println(PlayerID);
    }
    public synchronized void SetPlayerCordinates_R(int row_move){
        this.row_move = row_move;
    }
                                                                                     //these 2 methods reset the playerNode position
    public synchronized void SetPlayerCordinates_C (int column_move){
        this.column_move = column_move;
    }

    public synchronized void EasyModeMovement() throws InterruptedException {   //This method represents the easy difficulty mode that bots use to move.
        Random random = new Random();
        int rand = random.nextInt(4);           // in here we use the  Random class to set a random starting direction
        column_move = PlayerNode.GetColumn();
        row_move = PlayerNode.GetRow();
         x = 5;
         y = 7;
        while (true) {
            switch (rand) {
                                                //Here, we use an infinite loop to move the bots continuously. Upon exiting each case,
                                                // we set a new direction to enable the bots to continue moving and expand their movement.

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
    public synchronized void NormalModeMovement() throws InterruptedException{   //This method represents the Normal difficulty mode that bots use to move.

        //Note that each of these movement methods utilizes auxiliary functions that make the robots smarter and give them unique movement capabilities


        Random random = new Random();
        int rand = random.nextInt(4);
        column_move = PlayerNode.GetColumn();  // Similarly, we utilize the Random class to set a random starting direction, as mentioned earlier
        row_move = PlayerNode.GetRow();
        int x = 3;
        int y = 5;
        while(true){
                                      //Same as above, here we use an infinite loop to move the bots continuously.
                                         // Upon exiting each case, we set a new direction to enable the bots to continue moving and expand their movement.
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
    public synchronized void HardMoveMovement() throws InterruptedException {   //This method represents the Hard difficulty mode that bots use to move.

        //Note that each of these movement methods utilizes auxiliary functions that make the robots smarter and give them unique movement capabilities

        Random random = new Random();
        int rand = random.nextInt(4);     // Similarly, we utilize the Random class to set a random starting direction, as mentioned earlier
        column_move = PlayerNode.GetColumn();
        row_move = PlayerNode.GetRow();
        int x = 3;
        int y = 5;
        while(true){
            //Same as above, here we use an infinite loop to move the bots continuously.
            // Upon exiting each case, we set a new direction to enable the bots to continue moving and expand their movement.
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

    public synchronized void SetPlayer() {     //in here we set the playerNode and its position


        for (int j = 0; j < nodes.size(); j++) {
            if (Objects.equals(Owner.get(nodes.get(j)), Bot.GetBotID()) && nodes.get(j).GetIs_DefaultArea()) {
                nodes.get(j).setColor(Bot.GetBOTColor());
                nodes.get(j).SetIs_player(true);
                nodes.get(j).SetIs_DefaultArea(false);
                PlayerNode = nodes.get(j);
                SetPlayerCordinates_C(PlayerNode.GetColumn());
                SetPlayerCordinates_R(PlayerNode.GetRow());
                break;
            }
        }
    }
    public synchronized node FindRowNode(int row, int  column){
        //Here, we find the UP and DOWN nodes for our PlayerNode because the player is about to move UP or DOWN
        TempNode = null;
        while(true) {
            for (int j = 0; j < nodes.size(); j++) {
                if (nodes.get(j).GetRow() == row && nodes.get(j).GetColumn() == column) {
                    TempNode = nodes.get(j);
                    break;
                }
            }
            if (TempNode == null) {                  //If the UP or DOWN node does not exist, we call the Main Find method to generate it for us
                FindRowNodes(row, column, true);
            }
            else{
                break;
            }
        }
        return TempNode;
    }

    public synchronized node FindColumnNode(int row, int  column){
        //Here, we find the LEFT and RIGHT nodes for our PlayerNode because the player is about to move LEFT or RIGHT
        TempNode = null;
        while(true) {
            for (int j = 0; j < nodes.size(); j++) {
                if (nodes.get(j).GetRow() == row && nodes.get(j).GetColumn() == column) {
                    TempNode = nodes.get(j);
                    break;
                }
            }
            if (TempNode == null) {                    //If the LEFT or RIGHT node does not exist, we call the Main Find method to generate it for us
                FindColumnNodes(row, column, true);
            }
            else{
                break;
            }
        }

        return TempNode;
    }

    public synchronized void FindUnColoredRow(int row_move,int column_move,boolean UP) throws InterruptedException {
        //IN here we try to find a node that is not owned by our  Bot so we can continue the move algorithm
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

       SetPlayerCordinates_R(PlayerNode.GetRow());
    }
    public synchronized void FindUnColoredColumn(int row_move,int column_move,boolean LEFT) throws InterruptedException {
        //IN here we try to find a node that is not owned by our  Bot so we can continue the move algorithm
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

        SetPlayerCordinates_C(PlayerNode.GetColumn());
    }


    @Override
    public  void run() {                            //This is the implemented run method that each thread uses to move each bot based on the selected difficulty.

        if(Difficulty == "EASY") {
            while(true) {
                try {
                    EasyModeMovement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else if(Difficulty == "NORMAL"){
            while (true) {
                try {
                    NormalModeMovement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else if(Difficulty == "HARD"){
            while (true) {
                try {
                    HardMoveMovement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

            //These 4 methods below handle the bot movement. They use the Playernode position, difficulty level, and an integer called step to move the nodes.
            // All of the auxiliary functions are called inside these methods to make each bot smarter and more unique
    public synchronized void MoveUP(int row, int column, int step,String difficulty) throws InterruptedException {
        int Counter = 1;
        while(Counter <= step){
            TempNode = FindRowNode(row_move,column_move);
                if (TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())) { // Here, we handle the coloring method and use FindUncolored
                                                                                                   // to ensure that the bot won't move inside its area indefinitely
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    FindUnColoredRow(row_move, column_move, true);
                }

            PlayerNode = SwitchPlayer(PlayerNode,TempNode);  //We use a method called switchPlayer to swap the playernode with its new position

            row_move--;
            Counter++;
            steps++;
            Thread.sleep(Speed);
            System.out.println("MOVE UP");

            if(difficulty == "EASY"){     //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.
                if(steps >= 50){
                                        //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                               // In easy mode, we use a method called locateMyArea to ensure that the bot doesn't forget its area.
                                               // This method helps to make the movements more unique too !.
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }
             else if(difficulty == "NORMAL") {     //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.
                                              //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                                     //In Normal and Hard mode difficulties, we employ an additional auxiliary method called "findEnemy".
                                                   // This method scans the four sides of the Playernode (UP, DOWN, LEFT, and RIGHT)
                                                     //to detect any traces of another player.
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
                                            //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.
                                            //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                            //In Normal and Hard mode difficulties, we employ an additional auxiliary method called "findEnemy".
                                            // This method scans the four sides of the Playernode (UP, DOWN, LEFT, and RIGHT)
                                            //to detect any traces of another player. Additionally, in Hard mode, the "findEnemy" method incorporates
                                             // additional features along with a larger scan area.
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
        SetPlayerCordinates_R(row_move);
        SetSteps(steps);


    }
    public synchronized void MoveDOWN(int row, int column,int step,String difficulty)throws  InterruptedException{
        int Counter = 1;
        while(Counter <= step){
            TempNode = FindRowNode(row_move,column_move);

                if (TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())) {
                                                                                    // Here, we handle the coloring method and use FindUncolored
                                                                                    // to ensure that the bot won't move inside its area indefinitely
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    FindUnColoredRow(row_move, column_move, false);

                }

            PlayerNode = SwitchPlayer(PlayerNode,TempNode); //We use a method called switchPlayer to swap the playernode with its new position
            row_move++;
            Counter++;
            steps++;
            Thread.sleep(Speed);
            System.out.println("MOVE DOWN");

            if(difficulty == "EASY"){
                                         //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.

                                            //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                            // In easy mode, we use a method called locateMyArea to ensure that the bot doesn't forget its area.
                                            // This method helps to make the movements more unique too !.
                if(steps >= 50){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }
             else if(difficulty == "NORMAL") {

                                        //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.
                                        //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                        //In Normal and Hard mode difficulties, we employ an additional auxiliary method called "findEnemy".
                                        // This method scans the four sides of the Playernode (UP, DOWN, LEFT, and RIGHT)
                                        //to detect any traces of another player.
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
                                    //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.
                                    //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                    //In Normal and Hard mode difficulties, we employ an additional auxiliary method called "findEnemy".
                                    // This method scans the four sides of the Playernode (UP, DOWN, LEFT, and RIGHT)
                                    //to detect any traces of another player. Additionally, in Hard mode, the "findEnemy" method incorporates
                                    // additional features along with a larger scan area.
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
       SetPlayerCordinates_R(row_move);
        SetSteps(steps);
    }
    public synchronized void MoveLEFT(int row, int column,int step,String difficulty) throws InterruptedException{
        int Counter = 1;
        while(Counter <= step) {

                TempNode = FindColumnNode(row_move, column_move);
            if (TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())) {
                                                                            // Here, we handle the coloring method and use FindUncolored
                                                                            // to ensure that the bot won't move inside its area indefinitely
                color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                FindUnColoredColumn(row_move, column_move, true);


        }
            PlayerNode = SwitchPlayer(PlayerNode,TempNode);   //We use a method called switchPlayer to swap the playernode with its new position

            column_move--;
            Counter++;
            steps++;
            Thread.sleep(Speed);
            System.out.println("MOVE LEFT");

            if(difficulty == "EASY"){
                                        //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.

                                        //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                        // In easy mode, we use a method called locateMyArea to ensure that the bot doesn't forget its area.
                                        // This method helps to make the movements more unique too !.
                if(steps >= 50){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }
             else if(difficulty == "NORMAL") {
                                    //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.
                                    //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                    //In Normal and Hard mode difficulties, we employ an additional auxiliary method called "findEnemy".
                                    // This method scans the four sides of the Playernode (UP, DOWN, LEFT, and RIGHT)
                                    //to detect any traces of another player.
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

        SetPlayerCordinates_C(column_move);
        SetSteps(steps);
    }
    public synchronized void MoveRIGHT(int row, int column,int step,String difficulty) throws InterruptedException{
        int Counter = 1;
        while(Counter <= step){
            TempNode = FindColumnNode(row_move,column_move);

                if (TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), Bot.GetBotID())) {
                                                                        // Here, we handle the coloring method and use FindUncolored
                                                                        // to ensure that the bot won't move inside its area indefinitely
                    color_the_path(Bot.GetAreaColor(), Bot.GetBotID(), Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(), Bot.GetBotID());
                    FindUnColoredColumn(row_move, column_move, false);

                }

            PlayerNode = SwitchPlayer(PlayerNode,TempNode);    //We use a method called switchPlayer to swap the playernode with its new position
            column_move++;
            Counter++;
            steps++;
            Thread.sleep(Speed);
            System.out.println("MOVE RIGHT");

            if(difficulty == "EASY"){
                                        //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.

                                        //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                        // In easy mode, we use a method called locateMyArea to ensure that the bot doesn't forget its area.
                                        // This method helps to make the movements more unique too !.
                if(steps >= 50){
                    LocateMyArea(Bot.GetBotID());
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    SetSteps(0);
                }
            }

            else if(difficulty =="NORMAL") {
                                        //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.
                                        //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                        //In Normal and Hard mode difficulties, we employ an additional auxiliary method called "findEnemy".
                                        // This method scans the four sides of the Playernode (UP, DOWN, LEFT, and RIGHT)
                                        //to detect any traces of another player.
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
                                    //Based on the difficulty, we utilize various auxiliary methods to enhance the robot's behavior and make it smarter.
                                    //We also utilize steps here to invoke the auxiliary functions at a shorter interval, depending on the difficulty level.

                                    //In Normal and Hard mode difficulties, we employ an additional auxiliary method called "findEnemy".
                                    // This method scans the four sides of the Playernode (UP, DOWN, LEFT, and RIGHT)
                                    //to detect any traces of another player. Additionally, in Hard mode, the "findEnemy" method incorporates
                                    // additional features along with a larger scan area.
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
        SetPlayerCordinates_C(column_move);
        SetSteps(steps);
    }

    public synchronized node SwitchPlayer(node player, node temp) throws InterruptedException {
                                                 //As mentioned earlier, in this method, we switch the essence of nodes to move our player.
                                             // By changing the essence of each node, we create the illusion that the player node is moving.
        AmiDead = AmIDead(Bot.GetBotID());
        if(AmiDead){
                 //This part checks whether the bot is already dead. If the bot player is dead, we reset everything associated with this bot and regenerate it
            Reset(temp, player);
            ReGenerate(false);
            return PlayerNode;
        }else {                                                                //This part performs the essence switching for the robots.
            temp.SetIs_player(true);
            player.SetIs_player(false);
            temp.setColor(Bot.GetBOTColor());
            if (temp.GetIs_colored() && Owner.get(temp) == Bot.GetBotID()) {
                player.setColor(Bot.GetAreaColor());
            } else {
                player.setColor(Bot.GetTraceColor());
            }
              ChecktoKill(temp,Bot.GetBotID());
            temp.setOwnerID(Bot.GetBotID());
                if(!temp.GetIs_colored() ||(temp.GetIs_colored() && Owner.get(temp) != Bot.GetBotID() )){
                    player.SetIs_passed(true);
                    ResetKillIt();
           }else {
                    player.SetIs_passed(true);
                    player.SetKillIt(false);
            }
            temp.setOwnerID(Bot.GetBotID());
            player.setOwnerID(Bot.GetBotID());
            player = temp;
            SetBotScore(CheckScore(Bot.GetBotID()));
            System.out.println( "BOT SCORE : " + BotScore);
            return player;
        }
    }

    public synchronized void LocateMyArea(String ID) throws InterruptedException {
        //As mentioned earlier, in this method, we look for the closest node that is owned by our Bot and return to it
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
        if (MinRow < PlayerNode.GetRow()) {
            for(int i = PlayerNode.GetRow() - 1; i >= MinRow;i--){
                TempNode = FindRowNode(i,PlayerNode.GetColumn());
                if(TempNode.GetIs_colored() && Objects.equals(Owner.get(TempNode), ID) && !AlreadyColored){
                    color_the_path(Bot.GetAreaColor(),Bot.GetBotID(),Bot.GetTraceColor());
                    ColorArea(Bot.GetAreaColor(),Bot.GetBotID());
                    AlreadyColored = true;
                }else if((TempNode.GetIs_colored() && Owner.get(TempNode) != ID) || !TempNode.GetIs_colored()){
                    AlreadyColored = false;

                }
                PlayerNode = SwitchPlayer(PlayerNode,TempNode);
                 Thread.sleep(Speed);
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
                Thread.sleep(Speed);
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
                Thread.sleep(Speed);
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
                Thread.sleep(Speed);
            }
            AlreadyColored = false;
        }
        SetPlayerCordinates_R(PlayerNode.GetRow());
        SetPlayerCordinates_C(PlayerNode.GetColumn());
    }

    public synchronized void FindEnemy(int row , int column , int Sensitivity) throws InterruptedException {
        //As mentioned earlier in this method, we scan 4 sides to check for traces of another player.
        // The size of the area we scan is specified by an Integer called sensitivity.
        node EnemyTrace = null;
        node TempNode;
        String Direction = "";
        int KillSpeed;
                                                                // Here, we scan each side individually

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
            // if we find a trace we will perform an attack
        if(EnemyTrace != null){
            if(Difficulty == "HARD"){   //In hard mode difficulty, in addition to a larger scanning area and a shorter scan period,
                                          // we also boost the speed of the attack to make it more deadly.
              KillSpeed = Speed / 2;
            }else{
                KillSpeed = Speed;
            }
            switch (Direction){

                case("UP"):
                    for(int i = row; i >= EnemyTrace.GetRow();i--){
                        TempNode = FindTemp(i,column);
                        PlayerNode = SwitchPlayer(PlayerNode,TempNode);

                        Thread.sleep(KillSpeed);
                        }
                    break;

                case("DOWN"):
                    for(int i = row; i <= EnemyTrace.GetRow();i++){
                        TempNode = FindTemp(i,column);
                       PlayerNode =  SwitchPlayer(PlayerNode,TempNode);
                        Thread.sleep(KillSpeed);
                    }
                    break;

                case("LEFT"):
                    for(int i = column; i >= EnemyTrace.GetColumn();i--){
                        TempNode = FindTemp(row,i);
                        PlayerNode = SwitchPlayer(PlayerNode,TempNode);
                        Thread.sleep(KillSpeed);
                    }
                    break;

                case("RIGHT"):
                    for(int i = column; i <= EnemyTrace.GetColumn();i++){
                        TempNode = FindTemp(row,i);
                        PlayerNode = SwitchPlayer(PlayerNode,TempNode);
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
                                          //In this function, we regenerate a previously generated bot that has been killed.
        if(!firsttime) {
            Thread.sleep(3000);
        }
        SetDefaultBotArea();
        SetSteps(0);
        SetPlayerCordinates_C(PlayerNode.GetColumn());
        SetPlayerCordinates_R(PlayerNode.GetRow());
    }
    public synchronized void Reset(node temp,node player){
                                                            //This is another auxiliary function that we use to reset a dead bot.
        if(temp.GetIs_colored() && Owner.get(temp) != Bot.GetBotID()){
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

        if(Difficulty == "EASY"){
            SetX(5);
            SetY(7);

        }else{
            SetX(3);
            SetY(5);

        }
    }

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
            }
        }
    }
}
