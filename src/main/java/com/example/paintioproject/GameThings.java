package com.example.paintioproject;


import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GameThings {
    public static ArrayList<node> nodes = new ArrayList<>();
    public ArrayList<node> tempNodes = new ArrayList<>();
    public static HashMap<node,String> Owner = new HashMap<>();
    public static ArrayList <node> toCheck = new ArrayList<>();
    public static ArrayList <node> toColor = new ArrayList<>();
    public static HashSet<node> toRemove = new HashSet<>();
    ArrayList  <node> TEMPNODES = new ArrayList<>();
    public ArrayList<node> toShootCheck = new ArrayList<>();
    private node tempnode;
    private node Helptempnode;
    Rectangle rect = new Rectangle(25, 25);// GetPlayerColor());

    private boolean is_grey = false;
    private boolean is_exists = false;
    private boolean is_found =false;
    private int MaxRow = -1000000;
    private int MinRow = 1000000;
    private int MaxColumn = -1000000;
    private int MinColumn = 1000000;



    public void DefaultNodeGenerator() {
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


    public void RowNodeGenerator(int row_move, int column_move) {
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

    public void ColumnNodeGenerator(int row_move, int column_move) {
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



    public void ShootBulletA(KeyEvent Direction,int row, int column,Color color,String ID) throws InterruptedException {
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
                        TempNode2.ResettoDefaultColor();
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
                        TempNode.SetIs_colored(true);
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);
                    }
                }
                color_the_path(color,ID);
                for(int j = 0; j < toShootCheck.size();j++){
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
                      TempNode2.ResettoDefaultColor();
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
                      TempNode.SetIs_colored(true);
                      Owner.put(TempNode,ID);
                      toShootCheck.add(TempNode);
                  }
              }
                color_the_path(color,ID);
                for(int j = 0; j < toShootCheck.size();j++){
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
                        TempNode2.ResettoDefaultColor();
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
                        TempNode.SetIs_colored(true);
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);
                    }
                }
                color_the_path(color,ID);
                for(int j = 0; j < toShootCheck.size();j++){
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
                        TempNode2.ResettoDefaultColor();
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
                        TempNode.SetIs_colored(true);
                        Owner.put(TempNode,ID);
                        toShootCheck.add(TempNode);
                    }
                }
                color_the_path(color,ID);
                for(int j = 0; j < toShootCheck.size();j++){
                    toShootCheck.get(j).setColor(color);
                }
            }

            }
        }
        public synchronized void color_the_path(Color color,String ID){
        for(int j = 0; j < nodes.size(); j++){
            if(nodes.get(j).GetIs_passed() && !nodes.get(j).GetIs_colored() && (Owner.get(nodes.get(j))
             == ID || !Owner.containsKey(nodes.get(j)))){
                nodes.get(j).setColor(color);
                nodes.get(j).SetIs_colored(true);
                //nodes.get(j).SetIs_passed(false);
            }
        }
    }


    public synchronized void ColorArea(Color color, String ID) {  ///////////////////////////////////////////////////////////////////////////////////
        toColor.clear();
        toCheck.clear();
        toRemove.clear();
        for (int j = 0; j < nodes.size(); j++) {
            if (nodes.get(j).GetIs_colored()) {
                if (Owner.get(nodes.get(j)) == ID) {
                    if (nodes.get(j).GetRow() > MaxRow) {
                        MaxRow = nodes.get(j).GetRow();
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
            for (int j = MinColumn; j <= MaxColumn; j++) {
                for (int n = 0; n < nodes.size(); n++) {
                    if (nodes.get(n).GetRow() == i && nodes.get(n).GetColumn() == j) {
                        toColor.add(nodes.get(n));
                        System.out.println("ColorSize : " + toColor.size());
                    }
                }
            }
        }

        for (int i = MinRow; i <= MaxRow; i++) {       //UP
            for (int j = MinColumn; j <= MaxColumn; j++) {
                tempnode = FindtoColorTemp(i, j);
                if (!tempnode.GetIs_colored()) {
                    is_found = Checkneighbour(i - 1, j);
                    if (!is_found) {
                        for(int n = j;n <= MaxColumn; n++){
                            tempnode = FindtoColorTemp(i, n);
                            if(tempnode.GetIs_colored()){
                                break;
                            }
                            else{
                                toRemove.add(tempnode);
                            }

                        }
                         //toRemove.add(tempnode);
                    } else {
                        toCheckArea(tempnode,"UP",i,j);
                        //toCheck.add(tempnode);
                    }
                }
                else{
                    toCheck.add(tempnode);
                }
            }
        }
        toCheck.clear();
        for (int i = MaxRow; i >= MinRow; i--) { //DOWN
            for (int j = MaxColumn; j >= MinColumn; j--) {
                tempnode = FindtoColorTemp(i, j);
                if (!tempnode.GetIs_colored()) {
                    is_found = Checkneighbour(i + 1, j);
                    if (!is_found) {
                        for(int n = j; n >= MinColumn; n--){
                            tempnode = FindtoColorTemp(i, n);
                            if(tempnode.GetIs_colored()){
                                break;
                            }
                            else{
                                toRemove.add(tempnode);
                            }
                        }
                        //toRemove.add(tempnode);
                    } else {
                        toCheckArea(tempnode,"DOWN",i,j);
                        //toCheck.add(tempnode);
                    }
                }
                else{
                    toCheck.add(tempnode);
                }
            }
        }
        toCheck.clear();
        for (int j = MinColumn; j <= MaxColumn; j++) {  //LEFT
            for (int i = MaxRow; i >= MinRow; i--) {
                tempnode = FindtoColorTemp(i, j);
                if (!tempnode.GetIs_colored()) {
                    is_found = Checkneighbour(i, j - 1);
                    if (!is_found) {
                        for(int n = i; n >= MinRow; n--) {
                            tempnode = FindtoColorTemp(n, j);
                            if (tempnode.GetIs_colored()) {
                                break;
                            } else {
                                toRemove.add(tempnode);
                            }
                        }
                       // toRemove.add(tempnode);
                    } else {
                        toCheckArea(tempnode,"LEFT",i,j);
                        // toCheck.add(tempnode);
                    }

                }
                else{
                    toCheck.add(tempnode);
                }
            }
        }
        toCheck.clear();
        for (int j = MaxColumn; j >= MinColumn; j--) { //RIGHT
            for (int i = MinRow; i <= MaxRow; i++) {
                tempnode = FindtoColorTemp(i,j);
                if(!tempnode.GetIs_colored()){
                    is_found = Checkneighbour(i, j + 1);
                    if(!is_found){
                       for (int n = i; n <= MaxRow;n++){
                           tempnode = FindtoColorTemp(n, j);
                           if (tempnode.GetIs_colored()) {
                               break;
                           } else {
                               toRemove.add(tempnode);
                           }
                        }
                        //toRemove.add(tempnode);
                    }else{
                        toCheckArea(tempnode,"RIGHT",i,j);
                        //toCheck.add(tempnode);
                    }
                }else{
                    toCheck.add(tempnode);
                }
            }
        }
        toCheck.clear();
        System.out.println( "SIZE : " + toRemove.size());
       // for(int i = 0; i < toRemove.size();i++){
            //System.out.println(" ROW : "+toRemove.get(i).GetRow() +"\n COLUMN : " + toRemove.get(i).GetColumn() );
       // }
        //boolean tocolor = false;
        for(int i = 0; i < toColor.size();i++){
            //for(int j = 0; j < toRemove.size();j++){
                if(!toRemove.contains(toColor.get(i))){
                    toColor.get(i).setColor(color);
                    toColor.get(i).SetIs_colored(true);
                  //  tocolor = true;
                }//else{
                   // tocolor = false;
                   // break;
                }
            //}
           // if(tocolor){
              //  toColor.get(i).setColor(color);
               // toColor.get(i).SetIs_colored(true);
          //  }
        }
       /* for(int i = 0; i <toColor.size();i++){
            toColor.get(i).setColor(color);
            toColor.get(i).SetIs_colored(true);
        }*/
       // System.out.println( "SIZE : " + toRemove.size());
       // for(int i = 0; i < toRemove.size();i++){
        //    System.out.println(" ROW : "+toRemove.get(i).GetRow() +"\n COLUMN : " + toRemove.get(i).GetColumn() );
      //  }












   /* public synchronized  void ColorTheArea(Color color,String ID){
        toColor.clear();



        for(int j = 0; j < nodes.size(); j++){
            if(nodes.get(j).GetIs_passed()){
                if(Owner.get(nodes.get(j)) == ID){
                    if(nodes.get(j).GetRow() > MaxRow){
                        MaxRow = nodes.get(j).GetRow();
                    }else if(nodes.get(j).GetRow() < MinRow){
                        MinRow = nodes.get(j).GetRow();
                    } if( nodes.get(j).GetColumn() > MaxColumn){
                        MaxColumn = nodes.get(j).GetColumn();
                    }else if(nodes.get(j).GetColumn() < MinColumn){
                        MinColumn = nodes.get(j).GetColumn();
                    }
                }
            }
        }
        System.out.println(MinRow);
        System.out.println(MaxRow);
        System.out.println(MinColumn);
        System.out.println(MaxColumn);
        for(int i = MinRow; i <= MaxRow; i++){
            for(int j = MinColumn; j <= MaxColumn;j++){
                for(int n = 0; n < nodes.size();n++){
                    if(nodes.get(n).GetRow() == i && nodes.get(n).GetColumn() == j){
                       toColor.add(nodes.get(n));
                    }
                }
            }
        }
        while(MinRow < MaxRow &&  MinColumn < MaxColumn){

            for( TEMP =  MinColumn; TEMP <= MaxColumn;TEMP++){ //UP
                tempnode = FindtoColorTemp(MinRow,TEMP);
                if(!tempnode.GetIs_passed()){
                   is_found = Checkneighbour(MinRow - 1,TEMP);
                    if(!is_found){
                        FindtoRemove(tempnode);
                    }else{
                    toCheckArea(tempnode,"UP");
                    }
                }
            }
            for( TEMP = MinRow + 1; TEMP <= MaxRow; TEMP++){ //RIGHT
                tempnode = FindtoColorTemp(TEMP,MaxColumn);
                if(!tempnode.GetIs_passed()){
                    is_found = Checkneighbour(TEMP,MaxColumn + 1);
                    if(!is_found){
                        FindtoRemove(tempnode);
                    }else{
                        toCheckArea(tempnode,"RIGHT");
                    }

                }
            }
            for( TEMP = MaxColumn - 1; TEMP >= MinColumn;TEMP--){ // DOWN
                tempnode = FindtoColorTemp(MaxRow,TEMP);
                if(!tempnode.GetIs_passed()){
                    is_found = Checkneighbour(MaxRow + 1,TEMP);
                    if(!is_found){
                        FindtoRemove(tempnode);
                    }else{
                        toCheckArea(tempnode,"DOWN");
                    }
                }
            }
            for( TEMP = MaxRow - 1; TEMP <= MinRow; TEMP--){   //LEFT
                tempnode = FindtoColorTemp(TEMP,MinColumn);
                if(!tempnode.GetIs_passed()){
                    is_found = Checkneighbour(TEMP,MinColumn - 1);
                    if(!is_found){
                        FindtoRemove(tempnode);
                    }else{
                        toCheckArea(tempnode,"LEFT");
                    }
                }
            }
            MinRow++;
            MinColumn++;
            MaxRow--;
            MaxColumn--;
        }
        if(MinRow == MaxRow && MinColumn == MaxColumn){
            tempnode = FindtoColorTemp(MinRow,MinColumn);
            if(!tempnode.GetIs_passed()){
                is_found  = Checkneighbour(MinRow - 1, MinColumn); //UP
                if(!is_found) {
                    FindtoRemove(tempnode);
                }else {
                    is_found  = Checkneighbour(MinRow, MinColumn + 1); //RIGHT
                    if(!is_found) {
                        FindtoRemove(tempnode);
                    }else {
                        is_found  = Checkneighbour(MinRow + 1, MinColumn); //DOWN
                        if(!is_found) {
                            FindtoRemove(tempnode);
                        }else{
                            is_found  = Checkneighbour(MinRow + 1, MinColumn); // LEFT
                            if(!is_found) {
                                FindtoRemove(tempnode);
                            }
                        }
                    }
                }
            }
        }
        else if(MinRow == MaxRow){
           for(TEMP = MinColumn; TEMP <= MaxColumn;TEMP++){
               tempnode = FindtoColorTemp(MinRow,TEMP);
               if(!tempnode.GetIs_passed()){
                   is_found = Checkneighbour(MinRow - 1,TEMP); //UP NODE
                   if (!is_found){
                       FindtoRemove(tempnode);
                   }else {
                       is_found = Checkneighbour(MinRow + 1,TEMP); // DOWN NODE
                       if(!is_found){
                           FindtoRemove(tempnode);
                       }else{
                            ToCheckBothSide(tempnode,"UP&DOWN");
                       }
                   }
               }
           }
        }
        else if(MinColumn == MaxColumn) {
           for(TEMP = MinRow; TEMP <= MaxRow;TEMP++){
               tempnode = FindtoColorTemp(TEMP,MinColumn);
               if(!tempnode.GetIs_passed()){
                   is_found = Checkneighbour(TEMP,MinColumn - 1);// LEFT NODE
                   if (!is_found) {
                       FindtoRemove(tempnode);
                   }else{
                       is_found = Checkneighbour(TEMP,MinColumn + 1); //RIGHT NODE
                       if(!is_found){
                           FindtoRemove(tempnode);
                       }else{
                           ToCheckBothSide(tempnode,"LEFT&RIGHT");
                       }
                   }
               }
           }
        }
        for (int j = 0; j < toColor.size(); j++) { // Coloring the Area
            toColor.get(j).setColor(color);
            toColor.get(j).SetIs_colored(true);
        }
    }*/
    public node FindtoColorTemp(int row, int column){
                tempnode = null;
        for(int j = 0; j < toColor.size();j++){
            if(toColor.get(j).GetRow() == row && toColor.get(j).GetColumn() == column){
                tempnode = toColor.get(j);
                break;
            }
        }
        return tempnode;
    }
    public node FindTemp(int row, int column){
         node TempNode = null;
        for (com.example.paintioproject.node node : nodes) {
            if (node.GetRow() == row && node.GetColumn() == column) {
                TempNode = node;
                break;
            }
        }
         return TempNode;
    }
    public boolean Checkneighbour(int row, int column){
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

  /* public void FindtoRemove(node Tempnode){
        for(int j = 0; j < toColor.size();j++){
            if(toColor.get(j).equals(Tempnode)){
                toColor.remove(j);
                break;
            }
        }
    }*/
    public void toCheckArea(node Tempnode, String Direction,int row, int column){ /*-------------------------------------------------*/
        TEMPNODES.clear();
        TEMPNODES.add(Tempnode);
        Helptempnode = null;
        switch (Direction){
            case("UP"):
                for(int i = Tempnode.GetColumn(); i<= MaxColumn;i++){
                     Helptempnode = FindtoColorTemp(row,i);
                     TEMPNODES.add(Helptempnode);
                    if(Helptempnode.GetIs_colored()){
                        toCheck.addAll(TEMPNODES);
                        // SetTEMP(i);
                        break;
                    }else {
                        // toCheck.add(Helptempnode);
                        is_found = Checkneighbour(row - 1, i);
                        if (!is_found) {
                            toRemove.addAll(TEMPNODES);
                            //CleartoCheck();
                            // SetTEMP(i + 1);
                            break;
                        }
                    }
                }
                break;

            case("RIGHT"):
                for(int i = Tempnode.GetRow(); i<= MaxRow;i++){
                    Helptempnode = FindtoColorTemp(i,column);
                    TEMPNODES.add(Helptempnode);
                    //toCheck.add(Helptempnode);
                    is_found = Checkneighbour(i, column + 1);
                    if(Helptempnode.GetIs_colored()){
                        toCheck.addAll(TEMPNODES);
                        //   SetTEMP(i);
                        break;
                    }
                    if(!is_found){
                        toRemove.addAll(TEMPNODES);
                        //   CleartoCheck();
                       // SetTEMP(i + 1);
                        break;
                    }
                }

                break;

            case("DOWN"):
                for(int i = Tempnode.GetColumn(); i>= MinColumn;i--){
                    Helptempnode = FindtoColorTemp(row,i);
                    TEMPNODES.add(Helptempnode);
                    //toCheck.add(Helptempnode);
                    is_found = Checkneighbour(row + 1,i);
                    if(Helptempnode.GetIs_colored()){
                       toCheck.addAll(TEMPNODES);
                        // SetTEMP(i);
                        break;
                    }
                    if(!is_found){
                        toRemove.addAll(TEMPNODES);
                       // CleartoCheck();
                       // SetTEMP(i - 1);
                        break;
                    }
                }
                break;

            case("LEFT"):
                for(int i = Tempnode.GetRow(); i>= MinRow;i--){
                    Helptempnode = FindtoColorTemp(i,column);
                    TEMPNODES.add(Helptempnode);
                    //toCheck.add(Helptempnode);
                    is_found = Checkneighbour(i, column- 1);
                    if(Helptempnode.GetIs_colored()){
                        toCheck.addAll(TEMPNODES);
                        // SetTEMP(i);
                        break;
                    }
                    if(!is_found){
                        toRemove.addAll(TEMPNODES);
                        //SetTEMP(i - 1);
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }
   // public void CleartoCheck(){
       //  for(int i = 0; i < toColor.size(); i++){
      //       for(int j = 0; j < toCheck.size();j++){
           //      if(toColor.get(i) == toCheck.get(j)){
         //           toColor.remove(i);
            //     }
          //  }
       //  }
  //  }
   // public void SetTEMP(int TEMP){
     //  this.TEMP = TEMP;
   // }
    //public void setToRemove(node TempNode,){

   // }

  /*  public void ToCheckBothSide(node TempNode, String Sides){
        switch(Sides){
            case("UP&DOWN"):
                for(int i = TempNode.GetColumn() + 1; i <= MaxColumn;i++) {
                    Helptempnode = FindtoColorTemp(MinRow, i);
                    toCheck.add(Helptempnode);
                    if (Helptempnode.GetIs_passed()) {
                        SetTEMP(i);
                        break;
                    }
                        is_found = Checkneighbour(MinRow - 1, i); //UP NODE
                        if (!is_found) {
                            CleartoCheck();
                            SetTEMP(i + 1);
                            break;
                        }else {
                            is_found = Checkneighbour(MinRow + 1, i); //DOWN NODE
                            if (!is_found) {
                                CleartoCheck();
                                SetTEMP(i + 1);
                                break;
                            }
                        }
                }
                break;

                case("LEFT&RIGHT"):
                for(int i = TempNode.GetRow() + 1; i <= MaxRow; i++){
                    Helptempnode = FindtoColorTemp(i, MinColumn);
                    toCheck.add(Helptempnode);
                    if (Helptempnode.GetIs_passed()) {
                        SetTEMP(i);
                        break;
                    }
                    is_found = Checkneighbour(i,MinColumn - 1);  //LEFT NODE
                    if (!is_found) {
                        CleartoCheck();
                        SetTEMP(i + 1);
                        break;
                    }else {
                        is_found = Checkneighbour(i, MinColumn + 1); //RIGHT NODE
                        if (!is_found) {
                            CleartoCheck();
                            SetTEMP(i + 1);
                            break;
                        }
                    }
            }
                break;

                default:
                break;
        }





    }*/


}
