/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.game;
import static game.Game.scoretxt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author MBAWIZY
 */
public class Home extends JPanel implements KeyListener{
    
    public static boolean gameOn = true;
    public static boolean gameStart = false;
    public static boolean gamepause = false;
    private Image snake;
    private JLabel pause;
    private Box box;
    private JPanel highScore;
    private JList list;
    private JPanel p1;
    private start st;
    private JPanel info;
    private JLabel startBtn,scoreBtn,backBtn,exitBtn;
    public static String move = "down";
    public Home hm;
    public JFrame closeframe;
    
    
    public Home(JFrame jf){
        closeframe = jf;
        hm = this;
        setFocusable(true);
        requestFocus();
        addKeyListener(this);

   list = new JList();
   
        box = new Box(BoxLayout.Y_AXIS);
        box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.setAlignmentY(JComponent.CENTER_ALIGNMENT);
       box.add(Box.createVerticalGlue());
       //add(box);
       //box.add(Box.createHorizontalGlue());

       
       /*get images for custom buttons
       in you ide go to your project name right click and choose properties and on sources category under source package
       folders click add folder and select you current project on look in then create a reasouce folder name it any
       folder in my case i named it resource and add any images you would like to use on this game*/
        try{

        File F_btn1 = new File("resource/images/custombuttonstart.png");
        BufferedImage img1 = ImageIO.read(F_btn1);
        Image btn = img1.getScaledInstance(100, 100, 10);
        startBtn = new JLabel(new ImageIcon(btn));
        add(startBtn);
        
        File F_btn2 = new File("resource/images/custombuttonscore.png");
        BufferedImage img2 = ImageIO.read(F_btn2);
        Image btn2 = img2.getScaledInstance(100, 100, 10);
        scoreBtn = new JLabel(new ImageIcon(btn2));
        add(scoreBtn);
        
        File F_btn3 = new File("resource/images/custombuttonexit.png");
        BufferedImage img3 = ImageIO.read(F_btn3);
        Image btn3 = img3.getScaledInstance(100, 100, 10);
        exitBtn = new JLabel(new ImageIcon(btn3));
        add(exitBtn);
        
        File F_btn4 = new File("resource/images/custombuttonBack.png");
        BufferedImage img4 = ImageIO.read(F_btn4);
        Image btn4 = img4.getScaledInstance(100, 100, 10);
        
        //create a listener when these buttnos are clicked
        exitBtn.addMouseListener(new MouseListener(){
        
            public void mousePressed(MouseEvent e){
            
            }
            
            public void mouseExited(MouseEvent e){
            
            }
            
            public void mouseEntered(MouseEvent e){
            
            }
            
            public void mouseReleased(MouseEvent e){
            
            }
            
            public void mouseClicked(MouseEvent e){
             
                //click to close or exit the entire game
               closeframe.dispatchEvent(new WindowEvent(closeframe, WindowEvent.WINDOW_CLOSING));
             
            }
            
        });
        
        
        scoreBtn.addMouseListener(new MouseListener(){
        
            public void mousePressed(MouseEvent e){
            
            }
            
            public void mouseExited(MouseEvent e){
            
            }
            
            public void mouseEntered(MouseEvent e){
            
            }
            
            public void mouseReleased(MouseEvent e){
            
            }
            
            public void mouseClicked(MouseEvent e){//click to open your high score dash board
                
                box.setVisible(true);
                JSONArray jdata = null;
                
                //get the high score saved data as a json from resource file
                try{
                
                    InputStream in = new FileInputStream("resource/score.json");//create input stream to get the data as bytes from resource file
                    ByteArrayOutputStream bo = new ByteArrayOutputStream();//finale out of the whole data after extraction will be stored on this ByteOutputStream object
                    
                    byte[] b = new byte[255];//create temporary buffer to hold the data of bytes
                    int n = 0;
                    
                    //read all bytes from input stream and use the temporary buffer to scope some bytes until all bytes have been read from the input stream
                    while((n = in.read(b)) > 0){
                    bo.write(b, 0, n);//store all bytes that have been scoped in the ByteOutputStream object
                   }
                    bo.close();
                    
                    String data = new String(bo.toByteArray());//convert the bytes into a string
                    jdata = new JSONArray(data);//convert the string into a json format
                   
                }catch(IOException io){
                io.printStackTrace();
                }
                
                String[] lt = new String[jdata.length()];
                ArrayList<String> lt2 = new ArrayList<>();//create a list to hold all records of high core data
                
                for(int i = 0; i < jdata.length();i++){//loop through all data and add the scores each string list
                   JSONObject ob = jdata.getJSONObject(i);
                   lt2.add(Integer.toString(ob.getInt("score")));
                }
                
                Collections.sort(lt2, Collections.reverseOrder());//sort the list in descending order eg start from larger numbers first

                list.setListData(lt2.toArray());//add list to the JList object
                
        list.setFixedCellWidth(200);
        list.setFixedCellHeight(50);
        list.setName("My hight scores");
        
        JScrollPane scrollPane = new JScrollPane();//create a scroll bar
scrollPane.setViewportView(list);
list.setLayoutOrientation(JList.VERTICAL);
list.setCellRenderer(getRenderer());

        JPanel highScore = new JPanel();
        highScore.setBackground(Color.BLACK);
        highScore.add(scrollPane);//add scroll bar to Jlist
        
        JPanel name = new JPanel();
        JLabel nm = new JLabel("My high scores");
        nm.setForeground(Color.red);
        name.add(nm);
        name.setBackground(Color.BLACK);
        
        //add to bundle every thing related to scores together
        box.add(name);
                box.add(highScore);
   
                startBtn.setVisible(false);
             scoreBtn.setVisible(false);
             exitBtn.setVisible(false);
             
             JLabel backBtn = new JLabel(new ImageIcon(btn4));
             box.add(backBtn);
             add(box);
             
             backBtn.addMouseListener(new MouseListener(){
        
            public void mousePressed(MouseEvent e){
            
            }
            
            public void mouseExited(MouseEvent e){
            
            }
            
            public void mouseEntered(MouseEvent e){
            
            }
            
            public void mouseReleased(MouseEvent e){
            
            }
            
            public void mouseClicked(MouseEvent e){//click to to go back after selecting high scores
                  
                box.removeAll();

         box.revalidate();
         box.repaint();
         
         remove(box);
         revalidate();
         repaint();
         
         startBtn.setVisible(true);
             scoreBtn.setVisible(true);
             exitBtn.setVisible(true);
                
            }
            });
             
            }
            });
        
        startBtn.addMouseListener(new MouseListener(){
        
            public void mousePressed(MouseEvent e){
            
            }
            
            public void mouseExited(MouseEvent e){
            
            }
            
            public void mouseEntered(MouseEvent e){
            
            }
            
            public void mouseReleased(MouseEvent e){
            
            }
            
            public void mouseClicked(MouseEvent e){// click to start the game
                
                //add to bundle every thing related to starting the game
                move = "down";
                gameOn = true;
             gameStart = false;
                System.out.println("the game has launched!");
             startBtn.setVisible(false);
             scoreBtn.setVisible(false);
             exitBtn.setVisible(false);

         revalidate();
         
             box.setVisible(true);
             
            start st = new start();
        
        scoretxt = new JLabel("score: "+0);
        game = new JLabel("Game Started");
        pause = new JLabel("Press p to pause");
        JLabel exit = new JLabel("Press x to exit");
        game.setForeground(Color.GREEN);
        scoretxt.setForeground(Color.WHITE);
        JPanel p1 = new JPanel();

        p1.invalidate();
        
        JPanel info = new JPanel();//panel used to show the keys and infomation of ongoing game such as game start, pause and quit
        info.setBackground(Color.BLACK);
        info.add(game);
        info.add(scoretxt);
        info.add(pause);
        info.add(exit);
        
        p1.setBackground(Color.BLACK);
        
        p1.setBorder(new EmptyBorder(10,10,10,10));

        p1.add(st);

box.add(info);
box.add(p1);
box.invalidate();
box.validate();
add(box);
       
       Thread t = new Thread(new Gameloop(st,hm));//create a game loop for the starting game and add the start and home object variables
     t.start();
             
            }
            
        });
        
        }catch(IOException io){
         io.printStackTrace();
        }
        
        try{
            //get cover photo for the game you can use your own images
        File f3 = new File("resource/images/snake.jpg");
        BufferedImage bi3 = ImageIO.read(f3);
        snake = bi3.getScaledInstance(700, 700, 10);
        }catch(IOException io){
         io.printStackTrace();
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g){
     
        g.drawImage(snake, 0, 0, getSize().width,getSize().height,this);//add snake cover photo on main panel
    
    }
    
    //use keyboard for Gameloop 
    @Override
    public void keyPressed(KeyEvent e){//add Gameloop for moving the snake when game is started
     System.out.println("the key pressed is -> "+e.getKeyChar());
     if(e.getKeyChar() == 'd'){
         if(start.sk[0].getY() != start.sk[1].getY()){//check to see if the snake body is not on the same y axis or left and right to avoid moving across the snake
              move = "right";
        }
         
     }else if(e.getKeyChar() == 'a'){
          if(start.sk[0].getY() != start.sk[1].getY()){
              move = "left";
        }
         
     }else if(e.getKeyChar() == 'w'){
         if(start.sk[0].getX() != start.sk[1].getX()){//check to see if the snake body is not on the same x axis up and down to avoid moving across the snake
             move = "up";
        }
         
     }else if(e.getKeyChar() == 's'){
         if(start.sk[0].getX() != start.sk[1].getX()){
              move = "down";
        }
         
     }else if(e.getKeyChar() == 'p'){//click to pause the game
              gamepause = true;
              game.setText("Game paused");
              game.setForeground(Color.YELLOW);
              pause.setText("Press r to resume");
         
     }else if(e.getKeyChar() == 'r'){//click to resume the game
              gamepause = false;
              game.setText("Game Started");
              game.setForeground(Color.GREEN);
              pause.setText("Press p to pause");
         
     }else if(e.getKeyChar() == 'x'){//click to quit the game and return to main menu of the game
         gameOn = false;
         box.removeAll();

         box.revalidate();
         box.repaint();
         
         box.setVisible(false);
         remove(box);
         revalidate();
         repaint();
         
         startBtn.setVisible(true);
             scoreBtn.setVisible(true);
             exitBtn.setVisible(true);

     }
     
    }
    
    @Override
    public void keyReleased(KeyEvent e){
    System.out.println("the key released is -> "+e.getKeyChar());
    }
    
    @Override
    public void keyTyped(KeyEvent e){
    System.out.println("the key typed is -> "+e.getKeyChar());

    }
    
    private ListCellRenderer<? super String> getRenderer() {//fuction for creating borders for each high score cell, this function is optional and not recommanded 
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0,Color.BLACK));
                return listCellRendererComponent;
            }
        };
    }
    
}
