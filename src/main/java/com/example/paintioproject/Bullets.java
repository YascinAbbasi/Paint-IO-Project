package com.example.paintioproject;

import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class Bullets extends GameThings implements Runnable {
     //This class holds the data of a single bullet (bullet b), and whenever we shoot, we reset the bullet data with the information of a new bullet.
    // in here we have a thread that is waiting for a bullet to shoot and after shooting the bullet it will wait for the next bullet to shoot
    private KeyEvent Direction;
    private static int BulletRow;
    private static int BulletColumn;
    private String ShooterID;
    private volatile boolean ShootBullet = false;
    private MediaPlayer SoundEffectPlayer;
    private File file = new File();


    public Bullets(){
        ShootBullet = false;
    }



    public void ShootBulletB() throws InterruptedException {
                   //In this method, we shoot bullet b and create an illusion of movement by switching the essence of the bullet node.
                   //(same as moving the playerNode)
                    // This is how it works: In each direction, we first set the bullet node's position and then begin moving it until
        // the bullet hits the enemy or there are no more nodes available for the bullet to occupy (assuming we haven't expanded our infinite map that far yet).
        node BulletNode = null;
         node TempNode = null;
        Color Help;
        switch (Direction.getCode()){
            case UP ->{
                System.out.println("UP BULLET CALLED!");
                BulletNode = SetFirstBullet(Direction,BulletRow,BulletColumn);
                Help = BulletNode.GetColor();
                BulletNode.setColor(Color.YELLOW);
                SetBulletRow(BulletNode.GetRow());
                SetBulletColumn(BulletNode.GetColumn());
                while(true){
                    TempNode = FindTemp(BulletRow - 1,BulletColumn);
                    if(TempNode != null){
                        if(BulletNode.GetIs_passed()){
                          BulletNode.setColor(Help);
                        }
                        else if(BulletNode.GetIs_colored()){
                            BulletNode.ResettoPreviousColor();
                        }
                        else{
                            BulletNode.ResettoDefaultColor();
                        }
                        Help = TempNode.GetColor();
                        TempNode.setColor(Color.YELLOW);
                        BulletNode = TempNode;
                        if(BulletNode.Getis_player()){
                            Kill(BulletNode.getOwnerID());
                            MediaPlayerManager.loadSoundEffect(file.DeathEffect);
                            SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                            SoundEffectPlayer.play();
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
                Help = BulletNode.GetColor();
                BulletNode.setColor(Color.YELLOW);
                SetBulletRow(BulletNode.GetRow());
                SetBulletColumn(BulletNode.GetColumn());
                while(true){
                    TempNode = FindTemp(BulletRow + 1,BulletColumn);
                    if(TempNode != null){

                         if(BulletNode.GetIs_passed()){
                            BulletNode.setColor(Help);
                        }
                         else if(BulletNode.GetIs_colored()){
                             BulletNode.ResettoPreviousColor();
                         }
                        else{
                            BulletNode.ResettoDefaultColor();
                        }
                        Help = TempNode.GetColor();
                        TempNode.setColor(Color.YELLOW);
                        BulletNode = TempNode;
                        if(BulletNode.Getis_player()){
                            Kill(BulletNode.getOwnerID());
                            MediaPlayerManager.loadSoundEffect(file.DeathEffect);
                            SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                            SoundEffectPlayer.play();
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
                Help = BulletNode.GetColor();
                BulletNode.setColor(Color.YELLOW);
                SetBulletRow(BulletNode.GetRow());
                SetBulletColumn(BulletNode.GetColumn());
                while(true){
                    TempNode = FindTemp(BulletRow,BulletColumn - 1);
                    if(TempNode != null){

                        if(BulletNode.GetIs_passed()){
                            BulletNode.setColor(Help);
                        }
                        else if(BulletNode.GetIs_colored()){
                            BulletNode.ResettoPreviousColor();
                        }
                        else{
                            BulletNode.ResettoDefaultColor();
                        }
                        Help = TempNode.GetColor();
                        TempNode.setColor(Color.YELLOW);
                        BulletNode = TempNode;
                        if(BulletNode.Getis_player()){
                            Kill(BulletNode.getOwnerID());
                            MediaPlayerManager.loadSoundEffect(file.DeathEffect);
                            SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                            SoundEffectPlayer.play();
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
                Help = BulletNode.GetColor();
                BulletNode.setColor(Color.YELLOW);
                SetBulletRow(BulletNode.GetRow());
                SetBulletColumn(BulletNode.GetColumn());
                while(true){
                    TempNode = FindTemp(BulletRow,BulletColumn + 1);
                    if(TempNode != null){
                         if(BulletNode.GetIs_passed()){
                            BulletNode.setColor(Help);
                        }
                         else if(BulletNode.GetIs_colored()){
                             BulletNode.ResettoPreviousColor();
                         }
                        else{
                            BulletNode.ResettoDefaultColor();
                        }
                        Help = TempNode.GetColor();
                        TempNode.setColor(Color.YELLOW);
                        BulletNode = TempNode;
                        if(BulletNode.Getis_player()){
                            Kill(BulletNode.getOwnerID());
                            MediaPlayerManager.loadSoundEffect(file.DeathEffect);
                            SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                            SoundEffectPlayer.play();
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





    public void SetShootBullet(boolean ShootBullet){
        this.ShootBullet = ShootBullet;
    }
    public void SetBullet(KeyEvent Direction,int BulletRow,int BulletColumn,String ShooterID ){
                                                        //The data of the bullet is set in this part of the code.
        this.Direction = Direction;
        this.BulletRow = BulletRow;
        this.BulletColumn = BulletColumn;
        this.ShooterID = ShooterID;
    }
    public node SetFirstBullet(KeyEvent Direction,int row, int column){
        // We utilize this function to set the initial position of the bullet.
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
        // This is the implemented run method that the thread uses. After the shooting ends, the thread will wait for permission to shoot the new bullet
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
