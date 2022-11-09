package snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon food = new ImageIcon(getClass().getResource("food.png"));
    private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private boolean gameover = false;

    private int [] snakeXlength = new int[750];
    private int[] snakeYlength = new int[750];
    private int snakelength = 3;
    int moves=0;

    //timer to move snake
    private Timer timer;
    private int delay = 150;//to reduce the speed of snake

    private int[] foodxpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] foodypos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random = new Random(); // for food position

    private int foodx,foody;

    private int score=0;


GamePanel()
{
    addKeyListener(this);
    setFocusable(true);
     timer = new Timer(delay,this);
     timer.start();

     newFood();
}
//creating food position
    private void newFood() {
          foodx = foodxpos[random.nextInt(34)];
          foody = foodypos[random.nextInt(23)];
            // food position should not be at snake positions
           for(int i=snakelength-1;i>=0;i--)
           {
               if(snakeXlength[i]==foodx && snakeYlength[i]==foody)
               {
                   newFood();
               }
           }

    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);//color of rectangle

        g.drawRect(23,10,851,55);   //for heading
        g.drawRect(24,74,851,576);  // for game space

        snaketitle.paintIcon(this,g,24,11); //placing snake title image inside heading rectangle
        g.setColor(Color.BLACK);
        g.fillRect(25,75,850,575);  //BLACK background for playground

        if(moves==0) // initially it snake starts top left
        {
            snakeXlength[0] = 100;
            snakeXlength[1] = 75;
            snakeXlength[2] = 50;

            snakeYlength[0] = 100;
            snakeYlength[1] = 100;
            snakeYlength[2] = 100;
            moves++;
        }
        if(left) {  //when left arrow is pressed
            leftmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(right) {  //when right arrow is pressed
            rightmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(down) {   // when down arrow is pressed
            downmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(up) { //when up arrow is pressed
            upmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }

        for(int i=1;i<snakelength;i++) //remaining parts of snake
        {
            snakeimage.paintIcon(this,g,snakeXlength[i],snakeYlength[i]);
        }
        food.paintIcon(this,g,foodx,foody); //placing position of food

        if(gameover) //game over part
        {
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("Game Over",300,300);

            g.setColor(Color.GREEN);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
            g.drawString("Your Score : "+score,310,360);

            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,15));
            g.drawString("Press Space bar to restart the game",270,385);

        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,15));
        g.drawString("Score : "+score,750,30);
        g.drawString("Length : "+snakelength,750,50);

        g.dispose(); //remove the previous one
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    for(int i=snakelength-1;i>0;i--)
    {
        snakeXlength[i] = snakeXlength[i-1];
        snakeYlength[i] = snakeYlength[i-1];
    }
        if(left)
        {
            snakeXlength[0] = snakeXlength[0]-25;
        }
        if(right)
        {
            snakeXlength[0] = snakeXlength[0]+25;
        }
        if(up)
        {
            snakeYlength[0] = snakeYlength[0]-25;
        }
        if(down)
        {
            snakeYlength[0] = snakeYlength[0]+25;
        }
        // inorder to pass through the wall
        if(snakeXlength[0]>850) snakeXlength[0]=25;
        if(snakeXlength[0]<25)  snakeXlength[0]=850;
        if(snakeYlength[0]>625) snakeYlength[0]=75;
        if(snakeYlength[0]<75)  snakeYlength[0]=625;

         //eatingfood
        eatingFood();
        //if snake eats itself the Game Over
        eatingItself();

        //loop
        repaint();
    }

    private void eatingItself() {
          for(int i=snakelength-1;i>0;i--)
          {
              //checking each body of snake with head if same "Game Over"
              if(snakeXlength[i]==snakeXlength[0] && snakeYlength[i]==snakeYlength[0])
              {
                  timer.stop();
                  gameover=true;
              }
          }

    }

    private void eatingFood() { //when snake eats food
        if(snakeXlength[0]==foodx && snakeYlength[0]==foody)
        {
        score++;
        snakelength++;
        //if below 2 lines is not written then newly created body of snake will appear at top left corner for some fraction of seconds
        snakeXlength[snakelength - 1] = snakeXlength[snakelength - 2];
        snakeYlength[snakelength - 1] = snakeYlength[snakelength - 2];
        newFood();
        }
    }

    //for movement keyboard press
    @Override
    public void keyPressed(KeyEvent e) {

    if(e.getKeyCode() == KeyEvent.VK_SPACE && gameover) // gameover is true then only restart
    {
        restart();
    }
    if(e.getKeyCode() == KeyEvent.VK_LEFT && !right)//when left is pressed
       {
          left = true;
          right = false;
          up = false;
          down = false;
          moves++;
       }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && !left)//when right is pressed and left should be false so it should not move back
        {
            left = false;
            right = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP  && !down)//when up is pressed
        {
            left = false;
            right = false;
            up = true;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && !up)//when down is pressed
        {
            left = false;
            right = false;
            up = false;
            down = true;
            moves++;
        }

    }

    private void restart() {
        gameover = false;
        moves = 0;
        score = 0;
        snakelength = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
    }

    //not using below two methods
    @Override
    public void keyTyped(KeyEvent e) {

    }



    @Override
    public void keyReleased(KeyEvent e) {

    }
}