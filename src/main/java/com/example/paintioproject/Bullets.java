package com.example.paintioproject;

import javafx.scene.input.KeyEvent;

public class Bullets extends GameThings implements Runnable {
    private KeyEvent Direction;
    private int BulletRow;
    private int BulletColumn;
    private String ShooterID;

    public Bullets(KeyEvent Direction, int BulletRow , int BulletColumn,String ShooterID){
        this.Direction = Direction;
        this.BulletRow = BulletRow;
        this.BulletColumn = BulletColumn;
        this.ShooterID = ShooterID;
    }
    public void ShootBulletB(KeyEvent Direction,int row,int column,String ShooterID) throws InterruptedException {
        node TempNode = null;
        node BulletNode;
        switch (Direction.getCode()){
            case UP -> {
                System.out.println("UP BULLET CALLED");
                BulletNode = FindTemp(row - 1,column);
                while(TempNode != null){
                    row--;
                    TempNode = FindTemp(row,column);
                    if(TempNode == null){
                        System.out.println("BREAKKKKKKKKKK!");
                        break;
                    }
                    BulletNode = MoveBullet(BulletNode,TempNode);
                    Thread.sleep(100);
                    if(BulletNode.GetIs_passed()){
                        System.out.println("BREAKKKKKKKKKK2!");
                        break;
                    }
                }
            }
            case DOWN -> {
                System.out.println("DOWN BULLET CALLED");
                BulletNode = FindTemp(row + 1,column);
                while(TempNode != null){
                    row++;

                    TempNode = FindTemp(row,column);
                    if(TempNode == null){
                        System.out.println("BREAKKKKKKKKKK!");
                        break;
                    }
                    BulletNode = MoveBullet(BulletNode,TempNode);
                    Thread.sleep(100);
                    if(BulletNode.GetIs_passed()){
                        System.out.println("BREAKKKKKKKKKK2!");
                        break;
                    }
                }
            }
            case LEFT -> {
                System.out.println("LEFT BULLET CALLED");
                BulletNode = FindTemp(row,column - 1);
                while(TempNode != null){
                    column--;
                    TempNode = FindTemp(row,column);
                    if(TempNode == null){
                        System.out.println("BREAKKKKKKKKKK!");
                        break;
                    }
                    BulletNode = MoveBullet(BulletNode,TempNode);
                    Thread.sleep(100);
                    if(BulletNode.GetIs_passed()){
                        System.out.println("BREAKKKKKKKKKK2!");
                        break;
                    }
                }
            }
            case RIGHT -> {
                System.out.println("RIGHT BULLET CALLED");
                BulletNode = FindTemp(row,column  + 1);
                while(TempNode != null){
                    column++;
                    TempNode = FindTemp(row,column);
                    if(TempNode == null){
                        System.out.println("BREAKKKKKKKKKK!");
                        break;
                    }
                    BulletNode = MoveBullet(BulletNode,TempNode);
                    Thread.sleep(100);
                    if(BulletNode.GetIs_passed()){
                        System.out.println("BREAKKKKKKKKKK2!");
                        break;
                    }

                }
            }
            default -> {

            }
        }
    }

    @Override
    public void run() {
        try {
            ShootBulletB(Direction,BulletRow,BulletColumn,ShooterID);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
