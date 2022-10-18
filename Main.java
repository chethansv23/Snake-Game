 package snakeGame;
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    JFrame frame;

    Main() //Constructor
    {
        frame = new JFrame("Snake Game");
        frame.setBounds(10,10,905,700);//setting the size of frame
        frame.setResizable(false); //so that frame(window) can't be resized
        GamePanel gamePanel = new GamePanel();
        gamePanel.setBackground(Color.DARK_GRAY);
        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// program will be closed(stoped) once the X(Exit symbol) is pressed
        frame.setVisible(true);//inorder to display jframe it has to be true
    }

    public static void main(String[] args) {
           Main main = new Main();
    }
}