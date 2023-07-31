package com.example.paintioproject;


import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class GameThings{
    public static ArrayList<node> nodes = new ArrayList<>();
    public ArrayList<node> tempNodes = new ArrayList<>();
    public static HashMap<node,String> Owner = new HashMap<>();
    public static ArrayList <node> toCheck = new ArrayList<>();
    public static ArrayList <node> toColor = new ArrayList<>();
    public static HashSet<node> toRemove = new HashSet<>();
    private ArrayList <node> DeleteKeys = new ArrayList<>();
     public ArrayList  <node> TEMPNODES = new ArrayList<>();
    public ArrayList<node> toShootCheck = new ArrayList<>();
    private node tempnode;
    private node Helptempnode;
    Rectangle rect = new Rectangle(25, 25);// GetPlayerColor());
    private KeyEvent Direction;

    private boolean is_grey = false;
    private boolean is_exists = false;
    private boolean is_found =false;
    private int MaxRow = -1000000;
    private int MinRow = 1000000;
    private int MaxColumn = -1000000;
    private int MinColumn = 1000000;
    private int BulletRow = 0;
    private int BulletColumn = 0;



    public synchronized void DefaultNodeGenerator() {
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


    public synchronized void RowNodeGenerator(int row_move, int column_move) {
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

    public synchronized void ColumnNodeGenerator(int row_move, int column_move) {
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



    public void ShootBulletA(KeyEvent Direction,int row, int column,Color color,Color TraceColor,String ID) throws InterruptedException {
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
                       // TempNode2.ResettoDefaultColor(); ??@@@@@@@@@@@@@@@@
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
                color_the_path(color,ID,TraceColor);
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
                     // TempNode2.ResettoDefaultColor(); //@@@@@@@@@@@@@@
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
                color_the_path(color,ID,TraceColor);
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
                      //  TempNode2.ResettoDefaultColor(); //@@@@@@@@@@@@@@@@@
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
                color_the_path(color,ID,TraceColor);
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
                      //  TempNode2.ResettoDefaultColor(); @@@@@@@@@@@@@@@@
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
                color_the_path(color,ID,TraceColor);
                for(int j = 0; j < toShootCheck.size();j++){
                    toShootCheck.get(j).setColor(color);
                }
            }

            }
        }

         public void ShootBulletB(KeyEvent Direction,int row,int column) throws InterruptedException {
        node TempNode = null;
        node BulletNode = null;


        switch (Direction.getCode()){
             case UP -> {
                 BulletNode = FindTemp(row - 1,column);
               while(TempNode != null){
                   row--;
                  TempNode = FindTemp(row,column);
                  if(TempNode == null){
                      break;
                  }
                  BulletNode = MoveBullet(BulletNode,TempNode);
                  Thread.sleep(70);
                   if(BulletNode.GetIs_passed()){
                       break;
                   }

               }
             }
             case DOWN -> {
                 BulletNode = FindTemp(row + 1,column);
                 while(TempNode != null){
                     row++;

                     TempNode = FindTemp(row,column);
                     if(TempNode == null){
                         break;
                     }
                     BulletNode = MoveBullet(BulletNode,TempNode);
                     Thread.sleep(70);
                     if(BulletNode.GetIs_passed()){
                         break;
                     }
                 }
             }
             case LEFT -> {
                 BulletNode = FindTemp(row,column - 1);
                 while(TempNode != null){
                     column--;
                     TempNode = FindTemp(row,column);
                     if(TempNode == null){
                         break;
                     }
                     BulletNode = MoveBullet(BulletNode,TempNode);
                     Thread.sleep(70);
                     if(BulletNode.GetIs_passed()){
                         break;
                     }
                 }
             }
             case RIGHT -> {
                 BulletNode = FindTemp(row,column  + 1);
                 while(TempNode != null){
                     column++;
                     TempNode = FindTemp(row,column);
                     if(TempNode == null){
                         break;
                     }
                     BulletNode = MoveBullet(BulletNode,TempNode);
                     Thread.sleep(70);
                     if(BulletNode.GetIs_passed()){
                         break;
                     }

                 }
             }
             default -> {

             }
         }
        }

        public synchronized void color_the_path(Color color,String ID,Color TraceColor){
        for(int j = 0; j < nodes.size(); j++){
            if(nodes.get(j).GetIs_passed() && (nodes.get(j).GetColor() == TraceColor || nodes.get(j).GetColor() == color)){/*&& !nodes.get(j).GetIs_colored()*/
                    /*&& (Owner.get(nodes.get(j)) == ID /* || !Owner.containsKey(nodes.get(j))*///){
                nodes.get(j).setColor(color);
                nodes.get(j).SetIs_colored(true);
                Owner.put(nodes.get(j),ID);
                nodes.get(j).SetIs_passed(false);
                nodes.get(j).SetPrevious();
            }
        }
        System.out.println("COLOR THE PATH CALLED BY " + ID);
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
                    if (nodes.get(n).GetRow() == i && nodes.get(n).GetColumn() == j /*&&
                            (Objects.equals(Owner.get(nodes.get(n)), ID) || !Owner.containsKey(nodes.get(n)))*/) {
                        toColor.add(nodes.get(n));
                        // System.out.println("ColorSize : " + toColor.size());
                    }
                }
            }
        }

        for (int i = MinRow; i <= MaxRow; i++) {       //UP
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
                            //toRemove.add(tempnode);
                        } else {
                            toCheckArea(tempnode, "UP", i, j, ID);
                            //toCheck.add(tempnode);
                        }
                    } else {
                        toCheck.add(tempnode);
                    }
                }

            }
        }
        toCheck.clear();
        for (int i = MaxRow; i >= MinRow; i--) { //DOWN
            for (int j = MaxColumn; j >= MinColumn; j--) {
                tempnode = FindtoColorTemp(i, j);
                if (tempnode != null) {/////////////
                    if (!tempnode.GetIs_colored() || (tempnode.GetIs_colored() && !Objects.equals(Owner.get(tempnode), ID))) {
                        is_found = Checkneighbour(i + 1, j);
                        if (!is_found) {
                            for (int n = j; n >= MinColumn; n--) {
                                tempnode = FindtoColorTemp(i, n);
                                if (tempnode != null) { ///////////////////
                                    if (tempnode.GetIs_colored() && Objects.equals(Owner.get(tempnode), ID)) {
                                        break;
                                    } else {
                                        toRemove.add(tempnode);
                                    }
                                }
                            }
                            //toRemove.add(tempnode);
                        } else {
                            toCheckArea(tempnode, "DOWN", i, j, ID);
                            //toCheck.add(tempnode);
                        }
                    } else {
                        toCheck.add(tempnode);
                    }
                }

            }
        }
        toCheck.clear();
        for (int j = MinColumn; j <= MaxColumn; j++) {  //LEFT
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
                            // toRemove.add(tempnode);
                        } else {
                            toCheckArea(tempnode, "LEFT", i, j, ID);
                            // toCheck.add(tempnode);
                        }


                    } else {
                        toCheck.add(tempnode);
                    }
                }
            }
        }
        toCheck.clear();
        for (int j = MaxColumn; j >= MinColumn; j--) { //RIGHT
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
                            //toRemove.add(tempnode);
                        } else {
                            toCheckArea(tempnode, "RIGHT", i, j, ID);
                            //toCheck.add(tempnode);
                        }
                    } else {
                        toCheck.add(tempnode);
                    }
                }
            }
        }
        toCheck.clear();

        for (int i = 0; i < toColor.size(); i++) {
            if (!toRemove.contains(toColor.get(i)) && !toColor.get(i).Getis_player() && !toColor.get(i).GetIs_passed()) {
                toColor.get(i).setColor(color);
                toColor.get(i).SetIs_colored(true);
                toColor.get(i).setOwnerID(ID);
                Owner.put(toColor.get(i),ID);
                toColor.get(i).SetPrevious(); //???????????
                // System.out.println("TO COLOR CALLED BY " + ID);
            }
        }
    }
    public synchronized node FindtoColorTemp(int row, int column){
        tempnode = null;
        for(int j = 0; j < toColor.size();j++){
            if(toColor.get(j).GetRow() == row && toColor.get(j).GetColumn() == column){
                tempnode = toColor.get(j);
                break;
            }
        }
        return tempnode;
    }
    public synchronized node FindTemp(int row, int column){
         node TempNode = null;
        for (int j = 0; j < nodes.size();j++) {
            if (nodes.get(j).GetRow() == row && nodes.get(j).GetColumn() == column) {
                TempNode = nodes.get(j);
                break;
            }
        }
         return TempNode;
    }
    public synchronized boolean Checkneighbour(int row, int column){
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


    public synchronized void toCheckArea(node Tempnode, String Direction,int row, int column,String ID){ /*-------------------------------------------------*/
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
                            // SetTEMP(i);
                            break;
                        } else {
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
                }
                break;

            case("RIGHT"):
                for(int i = Tempnode.GetRow(); i<= MaxRow;i++){
                    Helptempnode = FindtoColorTemp(i,column);
                    if (Helptempnode != null) {
                        TEMPNODES.add(Helptempnode);
                        //toCheck.add(Helptempnode);
                       // is_found = Checkneighbour(i, column + 1);
                        if (Helptempnode.GetIs_colored() && Owner.get(Helptempnode) == ID) {
                            toCheck.addAll(TEMPNODES);
                            //   SetTEMP(i);
                            break;
                        } else {
                            is_found = Checkneighbour(i, column + 1);
                            if (!is_found) {
                                toRemove.addAll(TEMPNODES);
                                //   CleartoCheck();
                                // SetTEMP(i + 1);
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
                        //toCheck.add(Helptempnode);
                        //is_found = Checkneighbour(row + 1, i);
                        if (Helptempnode.GetIs_colored() && Owner.get(Helptempnode) == ID) {
                            toCheck.addAll(TEMPNODES);
                            // SetTEMP(i);
                            break;
                        } else {
                            is_found = Checkneighbour(row + 1, i);
                            if (!is_found) {
                                toRemove.addAll(TEMPNODES);
                                // CleartoCheck();
                                // SetTEMP(i - 1);
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
                        //toCheck.add(Helptempnode);
                        //is_found = Checkneighbour(i, column - 1);
                        if (Helptempnode.GetIs_colored() && Owner.get(Helptempnode) == ID) {
                            toCheck.addAll(TEMPNODES);
                            // SetTEMP(i);
                            break;
                        } else {
                            is_found = Checkneighbour(i, column - 1);
                            if (!is_found) {
                                toRemove.addAll(TEMPNODES);
                                //SetTEMP(i - 1);
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
     public synchronized node MoveBullet(node BulletNode,node TempNode){
        TempNode.setColor(Color.BLACK);
        BulletNode.setColor(Color.WHITE);
        BulletNode = TempNode;
    return BulletNode;
    }

    public void SetBulletB(KeyEvent Direction,int BulletRow, int BulletColumn){
    this.Direction = Direction;
    this.BulletRow = BulletRow;
    this.BulletColumn = BulletColumn;
    }
    public synchronized void Kill(String ID){
        DeleteKeys.clear();
        for(Map.Entry <node,String> entry : Owner.entrySet()){
            if(entry.getValue() == ID){
                DeleteKeys.add(entry.getKey());
            }
        }
        for(node delete : DeleteKeys){
            Owner.remove(delete);
            delete.ResetoDefault();
          //  delete.ResetOwnerID();
        }
        for(int j = 0; j < nodes.size();j++){
            if(nodes.get(j).GetIs_passed() && nodes.get(j).getOwnerID() == ID && nodes.get(j).GetIs_colored()){

                nodes.get(j).ResetToPrevious();
               // nodes.get(j).ResetToPrevious();
              // nodes.get(j).ResetOwnerID();//
            }
            else if(nodes.get(j).GetIs_passed() && nodes.get(j).getOwnerID() == ID && !nodes.get(j).GetIs_colored()){

                nodes.get(j).ResetoDefault();
                //nodes.get(j).ResetToPrevious();
               // nodes.get(j).ResetOwnerID();
            }
             if(nodes.get(j).Getis_player() && nodes.get(j).getOwnerID() == ID &&nodes.get(j).GetIs_colored()){
                // nodes.get(j).ResetToPrevious();
                 nodes.get(j).ResetoDefault();

            }
             else if(nodes.get(j).Getis_player() && nodes.get(j).getOwnerID() == ID && !nodes.get(j).GetIs_colored()){
                // nodes.get(j).ResetoDefault();
                 nodes.get(j).ResetToPrevious();
             }
        }

    }

    public synchronized void ChecktoKill(node Player, String ID){
        if(Player.GetIs_passed() && Player.getOwnerID() != ID){
            Kill(Player.getOwnerID());
            System.out.println("CHECK TO KILL CALLED");
        }
    }
    public synchronized boolean AmIDead(String ID){
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

}
