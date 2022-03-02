/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.dir;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author MWA Tech
 The Gameloop class is a game loop that loops every millisecond when the game is started 
 * hence it's responsible for dynamically changing the animations on the screen
 */
public class Gameloop implements Runnable{
    
    private start st;
    private Home home;
    
    public Gameloop(start sts,Home hm){//create a constructore and get all necessary objects such as the start and home classes so their function can be used within this loop
      st = sts;
      home = hm;
    }
    
    @Override
    public void run(){
       while(home.gameOn == true){//check if the game is not over
           try{
               if(home.gamepause == false){//check if the game is not paused
                   //perfom the following functions
               st.dir();
               st.moveHead();
               st.traceAndLogic();
               }
       Thread.sleep(st.speed);//because the loop loops very fast you won't be able to see it clearly so make it stop and sleep for a bit as it loops to make it a bit slower the speed of the snake is also controled here
           }catch( InterruptedException i){
               i.printStackTrace();
           }
       }
    }

    
}
