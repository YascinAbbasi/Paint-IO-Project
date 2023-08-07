package com.example.paintioproject;


import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameThings{
    //This is the main class where the game algorithm and required game information are defined.
    //These are the different lists and objects that we use to build the game algorithm.
    public static CopyOnWriteArrayList<node> nodes = new CopyOnWriteArrayList<>();
    public ArrayList<node> tempNodes = new ArrayList<>();
    public static ConcurrentHashMap<node,String> Owner = new ConcurrentHashMap<>();
    private  ArrayList <node> toCheck = new ArrayList<>();
    private  ArrayList <node> toColor = new ArrayList<>();
    private  HashSet<node> toRemove = new HashSet<>();

     public ArrayList  <node> TEMPNODES = new ArrayList<>();
    public ArrayList<node> toShootCheck = new ArrayList<>();
    private  node tempnode;
    private node Helptempnode;
    Rectangle rect = new Rectangle(25, 25);


    private boolean is_grey = false;
    private boolean is_exists = false;
    private boolean is_found =false;
    private boolean AmIAKiller = false;
    private int MaxRow = -1000000;
    private int MinRow = 1000000;
    private int MaxColumn = -1000000;
    private int MinColumn = 1000000;

    private static int Score;
    private MediaPlayer SoundEffectPlayer;
    private File file = new File();



    public synchronized void DefaultNodeGenerator() {  //Generating the first 625 nodes
        for (int i = 0; i <= 24; i++) {
            for (int j = 0; j <= 24; j++) {
                node temp;
                if ((i + j) % 2 == 0) {
                    temp = new node(Color.WHITE, i, j, true);
                } else {
                    temp = new node(Color.LIGHTGREY, i, j, true);
                }
                nodes.add(temp);
            }
            }
        }


    public synchronized void RowNodeGenerator(int row_move, int column_move) {  //This is one of the auxiliary methods that generate a row of nodes with 25 columns.
        System.out.println("r gen working");

        if (((row_move % 2 == 0) && (column_move % 2 == 0)) || ((row_move % 2 != 0) && (column_move % 2 != 0))) {
            is_grey = false;
        } else {
            is_grey = true;
        }
        for (int i = column_move; i <= 24 + column_move; i++) {
            if (!is_grey) {
                node temp = new node(Color.WHITE, row_move, i, false);
                nodes.add(temp);
                tempNodes.add(temp);
                is_grey = true;
            } else {
                node temp = new node(Color.LIGHTGREY, row_move, i, false);
                nodes.add(temp);
                tempNodes.add(temp);
                is_grey = false;
            }
        }

    }

    public synchronized void ColumnNodeGenerator(int row_move, int column_move) {  //This is one of the auxiliary methods that generate a column of nodes with 25 rows.
        System.out.println("c gen working");

        if (((row_move % 2 == 0) && (column_move % 2 == 0)) || ((row_move % 2 != 0) && (column_move % 2 != 0))) {
            is_grey = false;
        } else {
            is_grey = true;
        }
        for (int i = row_move; i <= 24 + row_move; i++) {
            if (!is_grey) {
                node temp = new node(Color.WHITE, i, column_move, false);
                nodes.add(temp);
                tempNodes.add(temp);
                is_grey = true;
            } else {
                node temp = new node(Color.LIGHTGREY, i, column_move, false);
                nodes.add(temp);
                tempNodes.add(temp);
                is_grey = false;
            }
        }
    }

    public synchronized void FindRowNodes(int row_move, int column_move, boolean UP ) {
        //This is one of the main methods used to expand the map by searching and creating the required nodes.
        //How it works: First, because the player is constant and the map is moving based on the player's movement, we need to update our map.
        // In each move, we need to display 25 nodes that either already exist or need to be generated. We also need to remove 25 nodes from the GridPane
        // that is used to display the map.
        //At the beginning, we search for the 25 nodes that we are about to display among the other 600 nodes.
        // We perform a search for each of these 25 nodes to determine if they already exist or need to be generated.
        // If we don't find any of these 25 nodes, we call the row and column generators to generate all of them for us.
        System.out.println("r find working");
        tempNodes.clear();
        if (!UP) {
            row_move += 24;
        }
        for (int j = 0; j < nodes.size(); j++) {
            if (nodes.get(j).GetRow() == row_move) {
                for (int i = column_move; i <= column_move + 24; i++) {
                    if (nodes.get(j).GetColumn() == i) {
                        tempNodes.add(nodes.get(j));

                    }
                }
            }

        }
        if (tempNodes.size() == 0) {
            System.out.println("Called!");
            RowNodeGenerator(row_move, column_move);
        } else {
            for (int i = column_move; i <= column_move + 24; i++) {
                for (int j = 0; j < tempNodes.size(); j++) {
                    if (tempNodes.get(j).GetColumn() == i) {
                        is_exists = true;
                        break;
                    }else{
                        is_exists = false;
                    }
                }
                if (!is_exists) {
                    if (((row_move % 2 == 0) && (i % 2 == 0)) || ((row_move % 2 != 0) && (i % 2 != 0))) {
                        is_grey = false;
                    } else {
                        is_grey = true;
                    }
                    if (is_grey) {
                        node temp = new node(Color.LIGHTGREY, row_move, i, false);
                        nodes.add(temp);
                        tempNodes.add(temp);
                    } else {
                        node temp = new node(Color.WHITE, row_move, i, false);
                        nodes.add(temp);
                        tempNodes.add(temp);
                    }

                }
            }
        }

        System.out.println(nodes.size());

    }



    public synchronized void FindColumnNodes(int row_move, int column_move, boolean LEFT) {
        //This is one of the main methods used to expand the map by searching and creating the required nodes.
        //How it works: First, because the player is constant and the map is moving based on the player's movement, we need to update our map.
        // In each move, we need to display 25 nodes that either already exist or need to be generated. We also need to remove 25 nodes from the GridPane
        // that is used to display the map.
        //At the beginning, we search for the 25 nodes that we are about to display among the other 600 nodes.
        // We perform a search for each of these 25 nodes to determine if they already exist or need to be generated.
        // If we don't find any of these 25 nodes, we call the row and column generators to generate all of them for us.
        System.out.println("c find working");
        tempNodes.clear();
        if (!LEFT) {
            column_move += 24;
        }
        for (int j = 0; j < nodes.size(); j++) {
            if (nodes.get(j).GetColumn() == column_move) {
                for (int i = row_move; i <= row_move + 24; i++) {
                    if (nodes.get(j).GetRow() == i) {
                        tempNodes.add(nodes.get(j));
                    }
                }
            }
        }
        if (tempNodes.size() == 0) {
            System.out.println("Called!");
            ColumnNodeGenerator(row_move, column_move);
        } else {
            for (int i = row_move; i <= row_move + 24; i++) {
                for (int j = 0; j < tempNodes.size(); j++) {
                    if (tempNodes.get(j).GetRow() == i) {
                        is_exists = true;
                        break;
                    }else{
                        is_exists = false;
                    }
                }
                if(!is_exists){
                    if(((column_move % 2 == 0) && (i % 2 == 0)) || (( column_move % 2 != 0 ) && (i % 2 != 0))){
                        is_grey = false;
                    }else {
                        is_grey = true;
                    }
                    if(is_grey) {
                        node temp = new node(Color.LIGHTGREY, i, column_move, false);
                        nodes.add(temp);
                        tempNodes.add(temp);
                    }else{
                        node temp = new node(Color.WHITE, i, column_move, false);
                        nodes.add(temp);
                        tempNodes.add(temp);
                    }

                }
            }
        }
        System.out.println(nodes.size());

    }



    public void ShootBulletA(KeyEvent Direction,int row, int column,Color color,Color TraceColor,String ID) throws InterruptedException {
        //this is shoot bullet A method
        // in Here with using the player's position and movement direction, we create an area that is colored and owned by the main player.
        // Additionally, the bullet has the ability to kill other players if any PlayerNode is within the explosion range of the bullet.
        // Similarly, with bot movement and switching the essence of each node, we perform our actions.
        toShootCheck.clear();
        node TempNode = null;
         node TempNode2 ;
         boolean firstime;
         int i;
         switch (Direction.getCode()){
            case UP -> {
               firstime = false;
                toShootCheck.clear();
                for( i = row - 1; i >= row - 5;i--){
                    if(firstime){
                        TempNode2 = TempNode;
                        TempNode = FindTemp(i, column);
                        TempNode.setColor(Color.BLACK);
                        TempNode.SetIs_colored(true);
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);

                    }else {
                        TempNode = FindTemp(i, column);
                        TempNode.setColor(Color.BLACK);
                        TempNode.SetIs_colored(true);
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);
                        firstime = true;
                    }


                }
                for(;i>= row - 8;i--){
                    for(int j = column - 1; j <= column + 1;j++){
                        TempNode = FindTemp(i,j);
                        TempNode.SetIs_colored(true); //Coloring and collecting the nodes.
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);
                    }
                }
                color_the_path(color,ID,TraceColor);
                for(int j = 0; j < toShootCheck.size();j++){
                    if(toShootCheck.get(j).Getis_player()){
                        Kill(toShootCheck.get(j).getOwnerID());   //Checking if the explosion killed someone and playing the kill sound effect.
                        MediaPlayerManager.loadSoundEffect(file.DeathEffect);
                        SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                        SoundEffectPlayer.play();
                    }
                    toShootCheck.get(j).setColor(color);
                }
            }
            case DOWN -> {
                firstime = false;
                toShootCheck.clear();
              for(i = row + 1; i <= row + 5;i++){
                  if(firstime){
                      TempNode2 = TempNode;
                      TempNode = FindTemp(i, column);
                      TempNode.setColor(Color.BLACK);
                      TempNode.SetIs_colored(true);
                      Owner.put(TempNode,ID);
                      toShootCheck.add(TempNode);

                  }else {
                      TempNode = FindTemp(i, column);
                      TempNode.setColor(Color.BLACK);
                      TempNode.SetIs_colored(true);
                      Owner.put(TempNode,ID);
                      toShootCheck.add(TempNode);
                      firstime = true;
                  }
              }
              for(;i <= row + 8;i++){
                  for(int j = column - 1; j <= column + 1;j++){
                      TempNode = FindTemp(i,j);
                      TempNode.SetIs_colored(true);  //Coloring and claiming the nodes.
                      Owner.put(TempNode,ID);
                      toShootCheck.add(TempNode);
                  }
              }
                color_the_path(color,ID,TraceColor);
                for(int j = 0; j < toShootCheck.size();j++){
                    if(toShootCheck.get(j).Getis_player()){
                        Kill(toShootCheck.get(j).getOwnerID());    //Checking if the explosion killed someone and playing the kill sound effect.
                        MediaPlayerManager.loadSoundEffect(file.DeathEffect);
                        SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                        SoundEffectPlayer.play();
                    }
                    toShootCheck.get(j).setColor(color);
                }
              }


            case LEFT -> {
                firstime = false;
                toShootCheck.clear();

                for(i = column - 1; i >= column - 5; i--){
                    if(firstime){
                        TempNode2 = TempNode;
                        TempNode = FindTemp(row,i);
                        TempNode.setColor(Color.BLACK);
                        TempNode.SetIs_colored(true);
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);

                    }else {
                        TempNode = FindTemp(row, i);
                        TempNode.setColor(Color.BLACK);
                        TempNode.SetIs_colored(true);
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);
                        firstime = true;
                    }
                }
                for(;i >= column - 8;i--){
                    for(int j = row- 1; j <= row + 1;j++){
                        TempNode = FindTemp(j,i);
                        TempNode.SetIs_colored(true); //Coloring and claiming the nodes.
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);
                    }
                }
                color_the_path(color,ID,TraceColor);
                for(int j = 0; j < toShootCheck.size();j++){
                   if(toShootCheck.get(j).Getis_player()){
                       Kill(toShootCheck.get(j).getOwnerID());   //Checking if the explosion killed someone and playing the kill sound effect.
                       MediaPlayerManager.loadSoundEffect(file.DeathEffect);
                       SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                       SoundEffectPlayer.play();
                   }
                    toShootCheck.get(j).setColor(color);
                }
            }


            case RIGHT -> {
                firstime = false;
                toShootCheck.clear();
                for(i = column + 1; i <= column + 5; i++){
                    if(firstime){
                        TempNode2 = TempNode;
                        TempNode = FindTemp(row,i);
                        TempNode.setColor(Color.BLACK);
                        TempNode.SetIs_colored(true);
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);

                    }else {
                        TempNode = FindTemp(row, i);
                        TempNode.setColor(Color.BLACK);
                        TempNode.SetIs_colored(true);
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);
                        firstime = true;
                    }
                }
                for(;i <= column + 8;i++){
                    for(int j = row- 1; j <= row + 1;j++){
                        TempNode = FindTemp(j,i);
                        TempNode.SetIs_colored(true); //Coloring and claiming the nodes.
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);
                    }
                }
                color_the_path(color,ID,TraceColor);
                for(int j = 0; j < toShootCheck.size();j++){
                    if(toShootCheck.get(j).Getis_player()){
                        Kill(toShootCheck.get(j).getOwnerID());   //Checking if the explosion killed someone and playing the kill sound effect.
                        MediaPlayerManager.loadSoundEffect(file.DeathEffect);
                        SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                        SoundEffectPlayer.play();
                    }
                    toShootCheck.get(j).setColor(color);
                }
            }

            }
        }

        public synchronized void color_the_path(Color color,String ID,Color TraceColor){
         //This function colors the path of the player or bot.
        for(int j = 0; j < nodes.size(); j++){
            if(nodes.get(j).GetIs_passed() && (nodes.get(j).GetColor() == TraceColor || nodes.get(j).GetColor() == color)){
                nodes.get(j).setColor(color);
                nodes.get(j).SetIs_colored(true);
                Owner.put(nodes.get(j),ID);
                nodes.get(j).SetIs_passed(false);
                nodes.get(j).SetPrevious();
            }
        }
        System.out.println("COLOR THE PATH CALLED BY " + ID);
    }


    public synchronized void ColorArea(Color color, String ID) {
        //this is the main coloring method that both player and bot use to color their earned area
        //How it works: First, we try to cover the entire already colored area, including the path (as it was colored before calling this method),
        // with a rectangle made up of nodes. This rectangle covers the entire area we are trying to color.
        // Then, we scan this rectangle from four sides (UP, DOWN, LEFT, RIGHT) to find the nodes that should not be colored.
        // We initially imagine that the entire area should be colored and then start searching for nodes that should not be in the coloring list.
        //For example, in the UP check, we start from the minimum row and scan each row down to the maximum row.
        // We check if each node in this list is already colored by the player or bot or if there is an UP node neighbor for that node.
        // We use two different algorithms to ensure that the node we are scanning is not inside a hole or outside our collected area.
        // We perform similar checks for the other three sides to ensure that all collected nodes are properly colored
        // And there are no incorrectly colored nodes that should not be colored.
        //And also, we use some auxiliary functions that I will describe.
        toColor.clear();
        toCheck.clear();
        toRemove.clear();
        for (int j = 0; j < nodes.size(); j++) {
            if (nodes.get(j).GetIs_colored()) {
                if (Owner.get(nodes.get(j)) == ID) {
                    if (nodes.get(j).GetRow() > MaxRow) {
                        MaxRow = nodes.get(j).GetRow();               //finding each vertex of the rectangle
                    } else if (nodes.get(j).GetRow() < MinRow) {
                        MinRow = nodes.get(j).GetRow();
                    }
                    if (nodes.get(j).GetColumn() > MaxColumn) {
                        MaxColumn = nodes.get(j).GetColumn();
                    } else if (nodes.get(j).GetColumn() < MinColumn) {
                        MinColumn = nodes.get(j).GetColumn();
                    }
                }
            }
        }
        System.out.println(MinRow);
        System.out.println(MaxRow);
        System.out.println(MinColumn);
        System.out.println(MaxColumn);

        for (int i = MinRow; i <= MaxRow; i++) {
            for (int j = MinColumn; j <= MaxColumn; j++) {  //adding nodes to tocolor list
                for (int n = 0; n < nodes.size(); n++) {
                    if (nodes.get(n).GetRow() == i && nodes.get(n).GetColumn() == j) {
                        toColor.add(nodes.get(n));
                    }
                }
            }
        }

        for (int i = MinRow; i <= MaxRow; i++) {       //UP Check
            for (int j = MinColumn; j <= MaxColumn; j++) {
                tempnode = FindtoColorTemp(i, j);
                if (tempnode != null) {
                    if (!tempnode.GetIs_colored() || (tempnode.GetIs_colored() && !Objects.equals(Owner.get(tempnode), ID))) {
                        is_found = Checkneighbour(i - 1, j);
                        if (!is_found) {
                            for (int n = j; n <= MaxColumn; n++) {
                                tempnode = FindtoColorTemp(i, n);
                                if (tempnode != null) {
                                    if (tempnode.GetIs_colored() && Objects.equals(Owner.get(tempnode), ID)) {
                                        break;
                                    } else {
                                        toRemove.add(tempnode);
                                    }

                                }
                            }
                        } else {
                            toCheckArea(tempnode, "UP", i, j, ID);
                        }
                    } else {
                        toCheck.add(tempnode);
                    }
                }

            }
        }
        toCheck.clear();
        for (int i = MaxRow; i >= MinRow; i--) { //DOWN check
            for (int j = MaxColumn; j >= MinColumn; j--) {
                tempnode = FindtoColorTemp(i, j);
                if (tempnode != null) {
                    if (!tempnode.GetIs_colored() || (tempnode.GetIs_colored() && !Objects.equals(Owner.get(tempnode), ID))) {
                        is_found = Checkneighbour(i + 1, j);
                        if (!is_found) {
                            for (int n = j; n >= MinColumn; n--) {
                                tempnode = FindtoColorTemp(i, n);
                                if (tempnode != null) {
                                    if (tempnode.GetIs_colored() && Objects.equals(Owner.get(tempnode), ID)) {
                                        break;
                                    } else {
                                        toRemove.add(tempnode);
                                    }
                                }
                            }

                        } else {
                            toCheckArea(tempnode, "DOWN", i, j, ID);

                        }
                    } else {
                        toCheck.add(tempnode);
                    }
                }

            }
        }
        toCheck.clear();
        for (int j = MinColumn; j <= MaxColumn; j++) {  //LEFT check
            for (int i = MaxRow; i >= MinRow; i--) {
                tempnode = FindtoColorTemp(i, j);
                if (tempnode != null) {
                    if (!tempnode.GetIs_colored() || (tempnode.GetIs_colored() && !Objects.equals(Owner.get(tempnode), ID))) {
                        is_found = Checkneighbour(i, j - 1);
                        if (!is_found) {
                            for (int n = i; n >= MinRow; n--) {
                                tempnode = FindtoColorTemp(n, j);
                                if (tempnode != null) {
                                    if (tempnode.GetIs_colored() && Objects.equals(Owner.get(tempnode), ID)) {
                                        break;
                                    } else {
                                        toRemove.add(tempnode);
                                    }
                                }
                            }

                        } else {
                            toCheckArea(tempnode, "LEFT", i, j, ID);

                        }


                    } else {
                        toCheck.add(tempnode);
                    }
                }
            }
        }
        toCheck.clear();
        for (int j = MaxColumn; j >= MinColumn; j--) { //RIGHT check
            for (int i = MinRow; i <= MaxRow; i++) {
                tempnode = FindtoColorTemp(i, j);
                if (tempnode != null) {
                    if (!tempnode.GetIs_colored() || (tempnode.GetIs_colored() && !Objects.equals(Owner.get(tempnode), ID))) {
                        is_found = Checkneighbour(i, j + 1);
                        if (!is_found) {
                            for (int n = i; n <= MaxRow; n++) {
                                tempnode = FindtoColorTemp(n, j);
                                if (tempnode != null) {
                                    if (tempnode.GetIs_colored() && Objects.equals(Owner.get(tempnode), ID)) {
                                        break;
                                    } else {
                                        toRemove.add(tempnode);
                                    }
                                }
                            }

                        } else {
                            toCheckArea(tempnode, "RIGHT", i, j, ID);

                        }
                    } else {
                        toCheck.add(tempnode);
                    }
                }
            }
        }
        toCheck.clear();

        //coloring the collected nodes
        for (int i = 0; i < toColor.size(); i++) {
            if (!toRemove.contains(toColor.get(i)) && !toColor.get(i).Getis_player() && !toColor.get(i).GetIs_passed()) {
                toColor.get(i).setColor(color);
                toColor.get(i).SetIs_colored(true);
                toColor.get(i).setOwnerID(ID);
                Owner.put(toColor.get(i),ID);
                toColor.get(i).SetPrevious();
            }
        }
    }
    public synchronized node FindtoColorTemp(int row, int column){   // one of the auxiliary functions that we use to search & scan
        tempnode = null;
        for(int j = 0; j < toColor.size();j++){
            if(toColor.get(j).GetRow() == row && toColor.get(j).GetColumn() == column){
                tempnode = toColor.get(j);
                break;
            }
        }
        return tempnode;
    }
    public synchronized node FindTemp(int row, int column){  //one of the auxiliary functions that we use to search & scan
         node TempNode = null;
        for (int j = 0; j < nodes.size();j++) {
            if (nodes.get(j).GetRow() == row && nodes.get(j).GetColumn() == column) {
                TempNode = nodes.get(j);
                break;
            }
        }
         return TempNode;
    }
    public synchronized boolean Checkneighbour(int row, int column){ //This is one of the auxiliary functions that we use to check if the side neighbor exists.
        is_found = false;
        for(int j = 0 ; j < toCheck.size(); j++){
            if(toCheck.get(j).GetRow() == row && toCheck.get(j).GetColumn() == column){
                is_found = true;
                break;
            }else {
                is_found = false;
            }
        }
        return is_found;
    }


    public synchronized void toCheckArea(node Tempnode, String Direction,int row, int column,String ID){
        //this is one functions that we use to check holes(the other Algorithm is actually declared inside the color area method)
        //Here's how this function works: It employs a clockwise search. When we call this method during the upward check,
        // it examines the right nodes of the node we are attempting to verify for collection. Likewise, during the right check,
        // we inspect the downward nodes, and so on. The purpose of this process is to ensure that the node is not situated within a hole.
        // (The counter-clockwise search is implemented within the color area method itself).
        TEMPNODES.clear();
        TEMPNODES.add(Tempnode);
        Helptempnode = null;
        switch (Direction){
            case("UP"):
                for(int i = Tempnode.GetColumn(); i<= MaxColumn;i++){
                     Helptempnode = FindtoColorTemp(row,i);
                    if (Helptempnode != null) {
                        TEMPNODES.add(Helptempnode);
                        if (Helptempnode.GetIs_colored() && Owner.get(Helptempnode) == ID) {
                            toCheck.addAll(TEMPNODES);
                            break;
                        } else {
                            is_found = Checkneighbour(row - 1, i);
                            if (!is_found) {
                                toRemove.addAll(TEMPNODES);
                                break;
                            }
                        }
                    }
                }
                break;

            case("RIGHT"):
                for(int i = Tempnode.GetRow(); i<= MaxRow;i++){
                    Helptempnode = FindtoColorTemp(i,column);
                    if (Helptempnode != null) {
                        TEMPNODES.add(Helptempnode);
                        if (Helptempnode.GetIs_colored() && Owner.get(Helptempnode) == ID) {
                            toCheck.addAll(TEMPNODES);
                            break;
                        } else {
                            is_found = Checkneighbour(i, column + 1);
                            if (!is_found) {
                                toRemove.addAll(TEMPNODES);
                                break;
                            }
                        }
                    }
                }

                break;

            case("DOWN"):
                for(int i = Tempnode.GetColumn(); i>= MinColumn;i--) {
                    Helptempnode = FindtoColorTemp(row, i);
                    if (Helptempnode != null) {
                        TEMPNODES.add(Helptempnode);
                        if (Helptempnode.GetIs_colored() && Owner.get(Helptempnode) == ID) {
                            toCheck.addAll(TEMPNODES);
                            break;
                        } else {
                            is_found = Checkneighbour(row + 1, i);
                            if (!is_found) {
                                toRemove.addAll(TEMPNODES);
                                break;
                            }
                        }
                    }
                }
                break;

            case("LEFT"):
                for(int i = Tempnode.GetRow(); i>= MinRow;i--) {
                    Helptempnode = FindtoColorTemp(i, column);
                    if (Helptempnode != null) {
                        TEMPNODES.add(Helptempnode);
                        if (Helptempnode.GetIs_colored() && Owner.get(Helptempnode) == ID) {
                            toCheck.addAll(TEMPNODES);
                            break;
                        } else {
                            is_found = Checkneighbour(i, column - 1);
                            if (!is_found) {
                                toRemove.addAll(TEMPNODES);
                                break;
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    public synchronized void Kill(String ID){
        //In this function, we remove everything that belongs to the ID, such as the area, PlayerNode, path, and so on.
        //Each of these if statements checks how the belongings of the ID should be removed,
        // whether they should be reset to their default values or restored to their previous state.
        for(int j = 0 ; j <nodes.size();j++){
            if(Objects.equals(Owner.get(nodes.get(j)), ID)){
                if(nodes.get(j).GetIs_passed() && nodes.get(j).getOwnerID() != ID){ ///////
                    nodes.get(j).SetIs_colored(false);
                    nodes.get(j).SetPrevious();
                }else{
                    nodes.get(j).ResetoDefault();
                }
                Owner.remove(nodes.get(j));
            }
            if(nodes.get(j).GetIs_passed() && nodes.get(j).GetIs_colored() && nodes.get(j).getOwnerID() == ID){
                nodes.get(j).ResetToPrevious();
            }else if(nodes.get(j).GetIs_passed() && nodes.get(j).getOwnerID() == ID && !nodes.get(j).GetIs_colored()){
                nodes.get(j).ResetoDefault();
            }
        }
    }

    public synchronized void ChecktoKill(node Player, String ID){
                              //In this method, we determine whether the ID is dead or alive by checking the number of its collected nodes.
        if(Player.GetIs_passed() && Player.getOwnerID() != ID && Player.GetKillIt()){
            AmIaKiller(true);
            Kill(Player.getOwnerID());
            System.out.println("CHECK TO KILL CALLED");
        }else{
            AmIaKiller(false);
        }
    }
    public synchronized boolean AmIDead(String ID){
        //This method checks whether the ID is dead or not!
       ArrayList <node> ImAlive =  new ArrayList<>();
        for(Map.Entry <node,String> entry : Owner.entrySet()){
            if(entry.getValue() == ID){
                ImAlive.add(entry.getKey());
            }
        }
        if(ImAlive.size() == 0){
            return true;
        }else{
            return false;
        }
    }
    public void SetScore(int Score){
        this.Score = Score;
    }
    public int GetScore(){
        return Score;
    }
    public int CheckScore(String ID){
        Score = 0;
        for(int j = 0; j < nodes.size();j++){
            if(Objects.equals(Owner.get(nodes.get(j)), ID)){
                Score += 5;
            }
        }
        SetScore(Score);
        return GetScore();
    }
    public void AmIaKiller(boolean Killer){
         AmIAKiller = Killer;
    }

    public boolean GetAmIAKiller() {
        return AmIAKiller;
    }
}
