import javax.swing.*;

public class App{
    public static void main(String[] args){
        int width = 600;
        int height = width;
        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(width, height);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}