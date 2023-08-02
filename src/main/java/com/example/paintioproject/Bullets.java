package com.example.paintioproject;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Bullets extends GameThings implements Runnable {
    private KeyEvent Direction;
    private static int BulletRow;
    private static int BulletColumn;
    private String ShooterID;
    private volatile boolean ShootBullet = false;

    public Bullets(KeyEvent Direction, int BulletRow , int BulletColumn,String ShooterID){
        this.Direction = Direction;
        this.BulletRow = BulletRow;
        this.BulletColumn = BulletColumn;
        this.ShooterID = ShooterID;
    }
    public Bullets(){
        ShootBullet = false;
    }



    public void ShootBulletB() throws InterruptedException {
        node BulletNode = null;
         node TempNode = null;
        Color Help;
        switch (Direction.getCode()){
            case UP ->{
                System.out.println("UP BULLET CALLED!");
                BulletNode = SetFirstBullet(Direction,BulletRow,BulletColumn);
                BulletNode.setColor(Color.YELLOW);
                Help = BulletNode.GetColor();
                SetBulletRow(BulletNode.GetRow());
                SetBulletColumn(BulletNode.GetColumn());
                while(true){
                    TempNode = FindTemp(BulletRow - 1,BulletColumn);
                    if(TempNode != null){
                        if(BulletNode.GetIs_colored()){
                            BulletNode.ResettoPreviousColor();
                        }
                       else if(BulletNode.GetIs_passed()){
                          BulletNode.setColor(Help);
                        }
                        else{
                            BulletNode.ResettoDefaultColor();
                        }
                        Help = TempNode.GetColor();
                        TempNode.setColor(Color.YELLOW);
                        BulletNode = TempNode;
                        if(BulletNode.Getis_player()){
                            Kill(BulletNode.getOwnerID());
                            System.out.println("KILL CALLED");
                            break;
                        }else{
                            SetBulletRow(BulletNode.GetRow());
                            SetBulletColumn(BulletNode.GetColumn());
                        }
                        Thread.sleep(30);
                    }else{
                        System.out.println("TEMP IS NULLLLLLLLL");
                        if(BulletNode.getOwnerID() != null){
                            BulletNode.ResettoPreviousColor();
                        }else{
                            BulletNode.ResettoDefaultColor();
                        }
                        break;
                    }
                }

            }
            case DOWN -> {
                System.out.println("DOWN BULLET CALLED!");
                BulletNode = SetFirstBullet(Direction,BulletRow,BulletColumn);
                BulletNode.setColor(Color.YELLOW);
                Help = BulletNode.GetColor();
                SetBulletRow(BulletNode.GetRow());
                SetBulletColumn(BulletNode.GetColumn());
                while(true){
                    TempNode = FindTemp(BulletRow + 1,BulletColumn);
                    if(TempNode != null){
                        if(BulletNode.GetIs_colored()){
                            BulletNode.ResettoPreviousColor();
                        }
                        else if(BulletNode.GetIs_passed()){
                            BulletNode.setColor(Help);
                        }
                        else{
                            BulletNode.ResettoDefaultColor();
                        }
                        Help = TempNode.GetColor();
                        TempNode.setColor(Color.YELLOW);
                        BulletNode = TempNode;
                        if(BulletNode.Getis_player()){
                            Kill(BulletNode.getOwnerID());
                            System.out.println("KILL CALLED");
                            break;
                        }else{
                                SetBulletRow(BulletNode.GetRow());
                                SetBulletColumn(BulletNode.GetColumn());
                            }

                        Thread.sleep(30);
                    }else{
                        System.out.println("TEMP IS NULLLLLLLLL");
                        if(BulletNode.getOwnerID() != null){
                            BulletNode.ResettoPreviousColor();
                        }else{
                            BulletNode.ResettoDefaultColor();
                        }
                        break;
                    }
                }
            }
            case LEFT -> {
                System.out.println("LEFT BULLET CALLED!");
                BulletNode = SetFirstBullet(Direction,BulletRow,BulletColumn);
                BulletNode.setColor(Color.YELLOW);
                Help = BulletNode.GetColor();
                SetBulletRow(BulletNode.GetRow());
                SetBulletColumn(BulletNode.GetColumn());
                while(true){
                    TempNode = FindTemp(BulletRow,BulletColumn - 1);
                    if(TempNode != null){
                        if(BulletNode.GetIs_colored()){
                            BulletNode.ResettoPreviousColor();
                        }
                       else if(BulletNode.GetIs_passed()){
                            BulletNode.setColor(Help);
                        }
                        else{
                            BulletNode.ResettoDefaultColor();
                        }
                        Help = TempNode.GetColor();
                        TempNode.setColor(Color.YELLOW);
                        BulletNode = TempNode;
                        if(BulletNode.Getis_player()){
                            Kill(BulletNode.getOwnerID());
                            System.out.println("KILL CALLED");
                            break;
                        }else{
                            SetBulletRow(BulletNode.GetRow());
                            SetBulletColumn(BulletNode.GetColumn());
                        }
                        Thread.sleep(30);
                    }else{
                        System.out.println("TEMP IS NULLLLLLLLL");
                        if(BulletNode.getOwnerID() != null){
                            BulletNode.ResettoPreviousColor();
                        }else{
                            BulletNode.ResettoDefaultColor();
                        }
                        break;
                    }
                }
            }
            case RIGHT -> {
                System.out.println("FIND BULLET CALLED!");
                BulletNode = SetFirstBullet(Direction,BulletRow,BulletColumn);
                BulletNode.setColor(Color.YELLOW);
                Help = BulletNode.GetColor();
                SetBulletRow(BulletNode.GetRow());
                SetBulletColumn(BulletNode.GetColumn());
                while(true){
                    TempNode = FindTemp(BulletRow,BulletColumn + 1);
                    if(TempNode != null){
                        if(BulletNode.GetIs_colored()){
                            BulletNode.ResettoPreviousColor();
                        }
                        else if(BulletNode.GetIs_passed()){
                            BulletNode.setColor(Help);
                        }
                        else{
                            BulletNode.ResettoDefaultColor();
                        }
                        Help = TempNode.GetColor();
                        TempNode.setColor(Color.YELLOW);
                        BulletNode = TempNode;
                        if(BulletNode.Getis_player()){
                            Kill(BulletNode.getOwnerID());
                            System.out.println("KILL CALLED");
                            break;
                        }else{
                            SetBulletRow(BulletNode.GetRow());
                            SetBulletColumn(BulletNode.GetColumn());
                        }
                        Thread.sleep(30);
                    }else{
                        System.out.println("TEMP IS NULLLLLLLLL");
                        if(BulletNode.getOwnerID() != null){
                            BulletNode.ResettoPreviousColor();
                        }else{
                            BulletNode.ResettoDefaultColor();
                        }
                        break;
                    }
                }
            }
        }
        SetShootBullet(false);
    }




    /*public void ShootBulletB(/*KeyEvent Direction,int row,int column,String ShooterID) throws InterruptedException {
        node TempNode = null;
        node BulletNode;
        switch (Direction.getCode()){
            case UP -> {
                System.out.println("UP BULLET CALLED");
                BulletNode = FindTemp(BulletRow - 1,BulletColumn);
                while(TempNode != null){
                    BulletRow--;
                    TempNode = FindTemp(BulletRow,BulletColumn);
                    if(TempNode == null){
                        System.out.println("BREAKKKKKKKKKK!");
                        break;
                    }
                    BulletNode = MoveBullet(BulletNode,TempNode);
                    Thread.sleep(300);
                    if(BulletNode.GetIs_passed()){
                        System.out.println("BREAKKKKKKKKKK2!");
                        break;
                    }
                }
                SetShootBullet(false);
            }
            case DOWN -> {
                System.out.println("DOWN BULLET CALLED");
                BulletNode = FindTemp(BulletRow + 1,BulletColumn);
                while(TempNode != null){
                    BulletRow++;

                    TempNode = FindTemp(BulletRow,BulletColumn);
                    if(TempNode == null){
                        System.out.println("BREAKKKKKKKKKK!");
                        break;
                    }
                    BulletNode = MoveBullet(BulletNode,TempNode);
                    Thread.sleep(300);
                    if(BulletNode.GetIs_passed()){
                        System.out.println("BREAKKKKKKKKKK2!");
                        break;
                    }
                }
                SetShootBullet(false);
            }
            case LEFT -> {
                System.out.println("LEFT BULLET CALLED");
                BulletNode = FindTemp(BulletRow,BulletColumn - 1);
                while(TempNode != null){
                    BulletColumn--;
                    TempNode = FindTemp(BulletRow,BulletColumn);
                    if(TempNode == null){
                        System.out.println("BREAKKKKKKKKKK!");
                        break;
                    }
                    BulletNode = MoveBullet(BulletNode,TempNode);
                    Thread.sleep(300);
                    if(BulletNode.GetIs_passed()){
                        System.out.println("BREAKKKKKKKKKK2!");
                        break;
                    }
                }
                SetShootBullet(false);
            }
            case RIGHT -> {
                System.out.println("RIGHT BULLET CALLED");
                BulletNode = FindTemp(BulletRow,BulletColumn + 1);
                while(TempNode != null){
                    BulletColumn++;
                    TempNode = FindTemp(BulletRow,BulletColumn);
                    if(TempNode == null){
                        System.out.println("BREAKKKKKKKKKK!");
                        break;
                    }
                    BulletNode = MoveBullet(BulletNode,TempNode);
                    Thread.sleep(300);
                    if(BulletNode.GetIs_passed()){
                        System.out.println("BREAKKKKKKKKKK2!");
                        break;
                    }

                }
                SetShootBullet(false);
            }
            default -> {

            }
        }
    }*/
    public void SetShootBullet(boolean ShootBullet){
        this.ShootBullet = ShootBullet;
    }
    public void SetBullet(KeyEvent Direction,int BulletRow,int BulletColumn,String ShooterID ){
        this.Direction = Direction;
        this.BulletRow = BulletRow;
        this.BulletColumn = BulletColumn;
        this.ShooterID = ShooterID;
    }
    public node SetFirstBullet(KeyEvent Direction,int row, int column){
        node TempBullet = null;
        switch (Direction.getCode()){
            case UP ->{
                System.out.println("UP SET FIRST CALLED!");
                TempBullet = FindTemp(row - 2,column);
            }
            case DOWN -> {
                System.out.println("DOWN SET FIRST CALLED!");
                TempBullet = FindTemp(row + 2,column);
            }
            case LEFT -> {
                System.out.println("LEFT SET FIRST CALLED!");
                TempBullet = FindTemp(row ,column - 2);
            }
            case RIGHT -> {
                System.out.println("FIND SET FIRST CALLED!");
                TempBullet = FindTemp(row,column + 2);
            }
        }
        return TempBullet;
    }
    public void SetBulletRow(int BulletRow){
        this.BulletRow = BulletRow;
    }
    public void SetBulletColumn(int BulletColumn){
        this.BulletColumn = BulletColumn;
    }


    @Override
    public void run() {
        while (true) {
            if (ShootBullet) {
                System.out.println("THREAD IS SHOOOOOOOTING THE BULLET!!!");
                try {
                   ShootBulletB();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
               }
            }
            else{
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}
