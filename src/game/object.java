/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author MWA Tech
 * This is the object class it's used to define any object within the game such as the snake body segments including the head and tail of the snake
 * as well as the food and obstacles
 */
public class object {
    
    int x,y,px,py,width,height,prexX,prevY;
    
    public object(){//create a constructor which does not take any arguments
    
    }
    
    public object(int x1,int y2){//create another constructor which takes two arguments
     x = x1;
     y = y2;
    }
    
    //create setters and getters for this object class all they do is set and get variables for this object class. Straight forward stuff
    public void setX(int x1){
     x = x1;
    }
    
    public void setY(int y1){
     y = y1;
    }
    
    public void setPrevX(int x1){
       prexX = x1;
    }
    
    public void setPrevY(int y1){
      prevY = y1;
    }
    
    public void setWidth(int x1){
     width = x1;
    }
    
    public void setHeight(int y1){
      height = y1;
    }
    
    public int getX(){
     return x;
    }
    
    public int getY(){
      return y;
    }
    
    public int getPrevX(){
     return prexX;
    }
    
    public int getPrevY(){
      return prevY;
    }
    
    public int getWidth(){
     return width;
    }
    
    public int getHeight(){
      return height;
    }
    
}
