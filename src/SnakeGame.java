import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    public class Tile{
        int x, y;
        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    
    int boardWidth, boardHeight;
    int tileSize = 25;
    Tile snakeHead, food;
    Random random;
    Timer gameLoop;
    ArrayList<Tile> snakeBody;
    int velocityX;
    int velocityY;
    SnakeGame(int x,int y){

        this.boardHeight = y;
        this.boardWidth = x;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);

        snakeHead = new Tile(5, 5);
        snakeBody =new ArrayList<Tile>();
        
        food = new Tile(10,10);
        addKeyListener(this);
        setFocusable(true);
        
        random = new Random(); // inisialisasi untuk fungsi-fungsi randomizer
        placeFood();
        gameLoop =new Timer(50,this); // Inisialisasi Timer untuk Game Loop
        gameLoop.start(); // Timer dimulai
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // Ini adalah for loop untuk menggambar grid
        for(int i =0;i<boardWidth/tileSize;i++){
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }
        // Menggambar Makanan Ular
        g.setColor(Color.red);
        g.fillRect(food.x*tileSize, food.y*tileSize , tileSize, tileSize);

        // Menggambar Kepala Ular
        g.setColor(Color.green);
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);

        // For loop untuk menggambar badan ular
        for(int i= 0;i < snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
        }
    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize); // Random dari nilai 0 (inklusif) ke nilai yang ditentukan
        food.y = random.nextInt(boardHeight/tileSize);
    }
    public boolean collision(Tile tile1,Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){
        // Collision jika terjadi tabrakan kepala dan makanan
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // Posisi X dan Y akan bertambah sesuai kecepatan velocity
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
    }

    @Override // Apabila timer habis maka kode yang ada pada fungsi dibawah ini akan berjalan
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Mendeteksi Input pada Keyboard
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;

        }else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
            
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
}