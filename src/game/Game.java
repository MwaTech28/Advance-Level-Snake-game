/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author MBAWIZY
 */
public class Game extends JFrame {
     private Scanner in;
    private String move = "b";
    private start st;
    private static final int width = 600;
    private static final int height = 600;
    public static String dir = "none";
    public static JLabel scoretxt,game;
    private static int score = 0;
    
    private JButton start;
    private JButton hscore;
    private JButton exit;
    //privae Image
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        new Game().createFrame();
        
    }
    
    private void createFrame(){
        JFrame f = new JFrame("game");

       Home home = new Home(f);

        f.add(home);
        
        try{
            //get cover photo for the game you can use your own images
        File f3 = new File("resource/images/snake.jpg");
        BufferedImage bi3 = ImageIO.read(f3);
        Image snake = bi3;
        f.setIconImage(snake);
        }catch(IOException io){
         io.printStackTrace();
        }
        
        f.setTitle("Snake game by Mwa tech");
        f.setMaximumSize(new Dimension(width,height));
        f.setMinimumSize(new Dimension(width,height));
        f.setPreferredSize(new Dimension(width,height));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setSize(new Dimension(width,height));
       
    }


}
