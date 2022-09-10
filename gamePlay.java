package BrickBreaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;

import javax.swing.JPanel;

public class gamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;//intialially it should not start the gameplay

    private int score = 0;//initial score =0

    private int totalBricks = 21;//initial number of bricks(7x3)

    private Timer timer;
    private int delay =15;

    private int playerX = 310;//starting position of slider
    private int ballposX = 120;//starting x position for ball
    private int ballposY = 350;//strating y position for ball
    private int ballXdir = -1;
    private int ballYdir = -4;

    private MapGenerator map;

    public gamePlay(){
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        
    }

    public void paint(Graphics g){
        //bcakground
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif" , Font.BOLD, 25));
        g.drawString(""+score, 600, 30);

        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        //After win
        if(totalBricks<=0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("YOU WON", 190, 300);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart ", 230, 350);

        }

        //After game over
        if(ballposY> 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: "+score, 190, 300);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart ", 230, 350);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){
            //detecting the intersection of the ball with the paddle
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))//create a virtual rectangle around the ball and check if it intersects with the paddle(other rectangle)
            {
                ballYdir = -ballYdir;//
            }
            A: for(int i=0; i<map.map.length; i++){
                for(int j=0; j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickx = j*map.brickWidth +80;
                        int bricky = i*map.brickHeight +50;
                        int brickwidth = map.brickWidth;
                        int brickheight = map.brickHeight;
                        //create a rectangle around the bricks
                        Rectangle rect = new Rectangle(brickx, bricky, brickwidth, brickheight);
                        //create a rectangle aound the ball
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks= totalBricks -1;
                            score =  score + 5;

                            if(ballposX +19 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }

            }


            ballposX+= ballXdir;
            ballposY+= ballYdir;
            //checking for left border
            if(ballposX<0){
                ballXdir = -ballXdir;//move the ball in oposite x direction when it touches left border
            }
            //checking for top border
            if(ballposY <0){
                ballYdir = -ballYdir;//move the ball in oposite y direction when it touches top border
            }
            //checking for right border
            if(ballposX > 670){
                ballXdir = -ballXdir;//move the ball x direction when it touches the right border
            }

        }
        repaint();//calls the paint method again whenever whenever action is performed 
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX >=600){
                playerX = 600;//if slider has reached the end then dont move it further
            }
            else{
                moveRight();
            }

        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX <10){
                playerX = 10;//if slider has reached the end then dont move it further
            }
            else{
                moveLeft();
            }
            
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = 300;
                ballposY = 350;
                ballXdir =-1;
                ballYdir = -4;
                playerX = 310;
                score = 0;
                totalBricks =21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
        
    }

    public void moveRight(){
        play =true;//starts the game
        playerX+= 20;//move right by 20 when right key is pressed
    }

    public void moveLeft(){
        play = true;//starts the game
        playerX-=20;
    }

    
}
