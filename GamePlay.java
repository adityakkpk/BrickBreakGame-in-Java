import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.Timer;


public class GamePlay extends JPanel implements KeyListener, ActionListener{

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 0;

    private int playerX = 310;

    private int bollposX = 350;
    private int bollposy = 525;
    private int bollXdir = -1;
    private int bollYdir = -2;

    private MapGenerator map;

    public GamePlay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

        map = new MapGenerator(3, 7);
    }

    public void paint(Graphics g){
        //Backgound
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //drawing Map
        map.draw((Graphics2D)g);

        //Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //Score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Scores: "+score, 540, 30);

        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // the boll
        g.setColor(Color.yellow);
        g.fillOval(bollposX, bollposy, 20, 20);

        if(totalBricks <= 0){
            play = false;
            bollXdir =0;
            bollYdir =0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won "+score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        if(bollposy > 570){
            play = false;
            bollXdir =0;
            bollYdir =0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+score, 190, 300);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        g.dispose();
    }
 
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}    

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){

            if(new Rectangle(bollposX, bollposy, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                bollYdir = -bollYdir;
            }

            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0){
                        int brickX = j*map.brickWidth+80;
                        int brickY = i*map.brickHeight+50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle bollRect = new Rectangle(bollposX, bollposy, 20, 20);
                        Rectangle brickRect = rect;

                        if(bollRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score+=5;

                            if(bollposX + 19 <= brickRect.x || bollposX +1 >= brickRect.x + brickRect.width){
                                bollXdir = -bollXdir;
                            }else{
                                bollYdir = -bollYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            bollposX += bollXdir;
            bollposy += bollYdir;
            if(bollposX<0){
                bollXdir = -bollXdir;
            }
            if(bollposy<0){
                bollYdir = -bollYdir;
            }
            if(bollposX>670){
                bollXdir = -bollXdir;
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX=600;
            }else{
                moveRight();
            }
        }
        if(e.getKeyCode()== KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX=10;
            }else{
                moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                bollposX=120;
                bollposy=350;
                bollXdir = -1;
                bollYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }
    }


    public void moveRight(){
        play =true;
        playerX+=20;
    }
    public void moveLeft(){
        play =true;
        playerX-=20;
    }
}