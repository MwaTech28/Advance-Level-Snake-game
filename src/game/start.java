 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author MBAWIZY
 */
public class start extends JPanel {

    private static final int width = 500;
    private static final int height = 500;
    private int holdobst = 0;
    private Robot robot;
    public static char ch = 'g';
    public static object[] sk;//snake body array
    private ConcurrentHashMap<String,object[]> obstHash;//snake game obstacles array
    ArrayList<object[]> obst;//snake game single obstacle array to be bundled in all obstacles array above
    public int headX,headY;//position of the snake head
    private int foodX,foodY;//position of the snake food
    private JLabel scoretxt,game;
    private int score;
    private Random ran;
    private int tail;//amount of current snake size
    public int speed;
    private int eat;
    private int wallSize;
    
    public start(){
        System.out.println("the start started");
        speed = 160;
        score = 0;
        eat = 0;
        wallSize = 0;
        headX = 0;
        headY = 0;
        holdobst = 0;
        tail = 0;
        JButton start = new JButton("start");
        start.setBackground(Color.red);
        
        JButton scr = new JButton("high score");
        JButton exit = new JButton("quit");
        start.setMargin(new Insets(10,100,10,100));
        scr.setMargin(new Insets(10,100,10,100));
        exit.setMargin(new Insets(10,100,10,100));
        
        obstHash = new ConcurrentHashMap<>();
        sk = new object[100];

        Border redline = BorderFactory.createLineBorder(Color.RED);

        scoretxt = new JLabel("score: "+score);
        game = new JLabel("Game Started");
        game.setForeground(Color.GREEN);
        scoretxt.setForeground(Color.WHITE);

        setBorder(redline);
        setMaximumSize(new Dimension(width,height));
        setMinimumSize(new Dimension(width,height));
        setPreferredSize(new Dimension(width,height));
        setBackground(Color.RED);

        ran = new Random();
        obst = new ArrayList<>();
        int f = 20;
        headX = f;
        headY = 20;
        foodX = width / 2;
        foodY = height / 2;
        for(int x = 0;x < 4;x++){//loop 4 times to create and initialise a snake of size 4
            object ob = new object();
       ob.setX(f);
       ob.setY(20);
       ob.setWidth(10);//give the snake a width of 10
       ob.setHeight(10);//give the snake a hight of 10
       sk[x] = ob;
       f += 10;
       tail++;
       }
        System.out.println("the game started "+sk.length);
    }
    
    @Override
    public void paintComponent(Graphics g){//function for creating the motion or animations of the snake game, this fuction will be repeated every milliseconds by the game loop
       super.paintComponent(g);
       this.setBackground(Color.BLACK);//give the screen a black background
       
       for(int x = 0;x < tail;x++){//loop throught the entire snake
           if(x == 0){
           g.setColor(Color.YELLOW);//give the snake head a yellow color
           }else if(x > 0){
           g.setColor(Color.RED);//give the snake tail portion a red color
           }
           
       object ob = sk[x];
       
       g.fillOval(ob.getX(), ob.getY(), ob.getWidth(), ob.getHeight());//draw the snake to have round corners

       }
       
       if(obstHash.size() > 0){
         for(String w: obstHash.keySet()){//loop throught availabe obstacles

           object[] walls = obstHash.get(w);
           for(object b : walls){
               g.setColor(Color.WHITE);//give the obstacles a white color
           g.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());//draw the obstacles to have square corners
         }
       
       }
       }
       
       g.setColor(Color.GREEN);//give the food a green color
       g.fillOval(foodX, foodY, 10,10);//draw the food to have round corners
       
    
    }
    
    public void dir(){//function to be used to always increment the direction of the last button pressed or chosen

      if(Home.move.equals("right")){//eg if the the character (d) was pressed while moving the snake the snake will always be moving right without having to press it repeatedly
              headX +=10;

      }
      else if(Home.move.equals("left")){
              headX -=10;

      }else if(Home.move.equals("up")){
              headY -=10;
 
      }
      else if(Home.move.equals("down")){
              headY +=10;
      }
    }
    
    public void moveHead(){//funtion to move the head of the snake, only take care of the head moving because the tails will just follow the head
 
       object ob = sk[0];
       ob.setPrevX(ob.getX());//use the head's current x axis coordinate to set as its previous x axis to allow the following tail or object to use this coordinate as its new x axis
       ob.setPrevY(ob.getY());//use the head's current y axis coordinate to set as its previous y axis to allow the following tail or object to use this coordinate as its new y axis
       ob.setX(headX);//now set the head's new x coordinates with the latest headX coordinates
       ob.setY(headY);//now set the head's new y coordinates with the latest headY coordinates
    }
    
    public void traceAndLogic(){//function for the mathematical logics

            for(int x = 0;x < tail;x++){//loop the entire snake and make the whole array of tails and head to be 1 whole snake an make each snake object or body section follow each other
                int lev = x;
                    
                if(lev > 0){//make sure to only loop the tail section on the snake an avoid the head which starts at 0 i already took care of the head in the moveHead() function
                    
                    object FnewOB = sk[lev];//get the current chosen tail
                    object SnewOB = sk[lev-1];//get the previous tail that comes before the current tail above

       //set the current tail or body section's new previous x and y axis's to it's current x and y axis
       FnewOB.setPrevX(FnewOB.getX());
       FnewOB.setPrevY(FnewOB.getY());
       //set the current tail or body section's new x and y axis's to the previous tail's previous x and y axis
       FnewOB.setX(SnewOB.getPrevX());
       FnewOB.setY(SnewOB.getPrevY());
       
                }

       }
            difficultyLevel();
            
            //find eat food and place food else where
            if(headX == foodX && headY == foodY){//check to see if the head and food's x and y axis are the same
                
                eat++;// if this variable reaches 5 increase the speed of the game by a know interval
                score+=10;//increase the score

                int xr,yr;
              do{

              //generate random numbers from 0 to 39 and add plus 1 to make it from 1 to 40
              xr = 1 + ran.nextInt(40);// yo can add any number than 40 but make sure it is less that your width and height when multiplied by 10 the number is not out of bound
              yr = 1 + ran.nextInt(40);   
                
              // then multiply the result by 10
              xr = xr * 10;
              yr = yr * 10;
              
              // give the new resuts as the new coordinates of the food
              foodX = xr;
              foodY = yr;
              
              }while(DontBlockWall(xr,yr) || DontBlockTail(xr,yr));//check flags
   

              //increase new tail by creating new snake body section after eating the food
              object ob = new object();
              object last = sk[tail - 1];
       ob.setX(last.getPrevX());
       ob.setY(last.getPrevY());
       ob.setWidth(10);
       ob.setHeight(10);
       sk[tail] = ob;
       tail++;
            }
            
            Game.scoretxt.setText("score: "+score);
            crossline();
            gameover();
            
            if(Home.gameOn == false){//if its game over or when the snake crushes into an obstacle ot it's self
                Game.game.setText("GameOver!");
            Game.game.setForeground(Color.red);
            
            //check to see if game is over if so get all high scrores through json
            //go through all scores to check if the new score is higher than each rewride and place all scores in order
            
            if(score > 0){
            JSONArray jdata = null;
                try{
                
                    InputStream in = new FileInputStream("resource/score.json");//create input stream to get the data as bytes from resource file
                    ByteArrayOutputStream bo = new ByteArrayOutputStream();//finale out of the whole data after extraction will be stored on this ByteOutputStream object
                    byte[] b = new byte[255];//create temporary buffer to hold the data of bytes
                    int n = 0;
                    
                    //read all bytes from input stream and use the temporary buffer to scope some bytes until all bytes have been read from the input stream
                    while((n = in.read(b)) > 0){
                    bo.write(b, 0, n);//store all bytes that have been scoped in the ByteOutputStream object
                   }
                    
                    String data = new String(bo.toByteArray());//convert the bytes into a string
                    jdata = new JSONArray(data);//convert the string into a json format
                    
                    for(int j = 0; j< jdata.length(); j++){//loop the entire json high dcore data
                     JSONObject jo = jdata.getJSONObject(j);//get each json object of the list
                     int old = jo.getInt("score");//get the score number of each
                     
                     if(score > old){//check if the new score is greater than any saved score add it to list of scores
                     
                         JSONObject jo2 = new JSONObject();
                         jo2.put("id", jdata.length()+1);
                         jo2.put("score", score);
                         jdata.put(jo2);
                         
                         File f = new File("resource/score.json");//recreate high score saved data file
                         OutputStream out = new FileOutputStream(f);
                        
                         byte[] newdscores = jdata.toString().getBytes();//get the new update list and convert it into bytes
                         out.write(newdscores, 0, newdscores.length);//write the new list to file output location
                         
                         break;
                         
                     }
                     
                     
                     
                    }
                    
                    
                   
                }catch(IOException io){
                io.printStackTrace();
                }
            }
            
            }
            
            repaint();//repain function used to change any updates from this logics in the trace and logics function through the paintComponent() function
    }
    
    private boolean DontBlockWall(int x,int y){//used to check if the obstacles objects don't aline in the same axis with other objects such as the food or snake segments 
    
        for(String delobj: obstHash.keySet()){
                  
                  object[] p = obstHash.get(delobj);   
                  
                  for(object c : p){
                      
                      if(c.getX() == x && c.getY() == y){
              return true;
                      }
                      
                  }
        
                  
               }
        
        
        return false;
    };
    
    private boolean DontBlockTail(int x,int y){//used to check if the snake boody segments objects don't aline in the same axis with other objects such as the food or obstacles 
    
                  
                  for(int x2 = 0;x2 < tail;x2++){
                      object c = sk[x2];

                      if(c.getX() == x && c.getY() == y){
                          return true;
                      }
                      
                  }
                  
             return false;
        
    };
    
    private boolean DontBlockFood(int x,int y){//used to check if the food object don't aline in the same axis with the obstacles objects

                      if(foodX == x && foodY == y){
                          return true;
                      }

                  
             return false;
        
    };
    
    private void crossline(){//function to make the snake apeare on the other side of the screen if it crosses the boundary of the width or height of the screen
  
            if(headX <= 0){
                //if snake body part is on the left end of the screen appear on the right side of the screen
               headX = width;
               
            }else if(headX >= width){
                //if snake body part is on the right end of the screen appear on the left side of the screen
            headX = 0;
            }if(headY <= 0){
                //if snake body part is on the top end of the screen appear on the bottom side of the screen
               headY = height;

            }else if(headY >= height){
               //if snake body part is on the bottom end of the screen appear on the top side of the screen
            headY = 0;
            }

        
    }
    
    private void gameover(){//gameover function to check if snake head crashes on its self or crashes on the obstacles for

    if(sk.length >0){
     
        for(int x = 1; x < sk.length;x++){
            object ob = sk[x];
            try{
                
        if(headX == ob.getX() && headY == ob.getY()){
            
          Home.gameOn = false;
        }
            }catch(NullPointerException n){
                    }
        }
    }
    
    if(obstHash.size() >0){
            
            for(String w: obstHash.keySet()){

           object[] bk = obstHash.get(w);
            
            try{
                
            for(object b : bk){
                int xd = b.getX();
                int yd = b.getY();
                
            if(headX == xd && headY == yd){
            
          Home.gameOn = false;
        }
            }
        
            }catch(NullPointerException n){
                    }
        }
    }
        
    }
    
    public void difficultyLevel(){
        
       if(eat == 5){
           // if the eat variable reaches 5 increase the speed of the game by a know interval then resert it back to zero
        speed -= 20;
        eat = 0;
       }
       
       //create obstacles in the snake game
       int end = 1 + ran.nextInt(150);//get a random number from 1 to 150 every millisecond
       if(end == 1 || end == 3){//if the random number is either 1 or 3 then create an obstacle
           
           int x,y;
           do{
        int block = 1 + ran.nextInt(2);//get a random number from 1 to 2
        object[] bk = new object[block];//use the bloack result to create an array of 1 0r 2
        
        //create x and y posistions of an object
        x = 1 + ran.nextInt(40);
        y = 1 + ran.nextInt(40);
              x = x * 10;
              y = y * 10;
        
        for(int i = 0;i < bk.length;i++){//loop through the block of obstacle array if the lenght is 1 then it will only be 1 or else 2
                
                 object ob = new object();
          ob.setX(x);
       ob.setY(y);
       ob.setWidth(10);
       ob.setHeight(10);
       bk[i] = ob;
       if(end == 1){//if obstacle generated random number from 1 to 150 is equal to 1 then make obstacle wall horizontal
       x+=10;
       
       }else if (end == 3){//if obstacle generated random number from 1 to 150 is equal to 3 then make obstacle wall vertical
       y+=10;
       }
       
       int cur = holdobst;
        cur++;//use this variable result as key for obstacle hashmap
        
        obstHash.put(Integer.toString(cur), bk);

        holdobst +=1; //use this variable result as new as possible valuse of all obstacles in a list
        
        wallSize = holdobst;
           
        }
       }while(DontBlockTail(x,y) || DontBlockFood(x,y));//check flags if either is true loop again if false continue to the next statements
        
       }else if (end == 66){//if the random number is 66 delete an obstacle
           
           if(wallSize > 0){
               int del = 1+ran.nextInt(wallSize); //get any random number that belongs to a key of obstacles in a hashmap list
               for(String delobj: obstHash.keySet()){//loop throght hashmap keys
                      if(delobj.equals(Integer.toString(del))){//if keys match remove the obstacle
                        obstHash.remove(delobj);
                      }
               }
       
           }
       }
    }
    
    
}
