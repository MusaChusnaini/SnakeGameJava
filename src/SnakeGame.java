import java.awt.*; // Import library untuk grafis
import java.awt.event.*; // Import library untuk event handling
import java.util.ArrayList; // Import library untuk array dinamis
import java.util.Random; // Import library untuk angka acak
import javax.swing.*; // Import library untuk GUI

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    // Kelas untuk merepresentasikan satu bagian dari ular atau makanan
    public static class Tile {
        int x, y;
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth, boardHeight; // Ukuran papan permainan
    int tileSize = 25; // Ukuran tiap kotak di grid
    Tile snakeHead, food; // Objek kepala ular dan makanan
    Random random; // Objek random untuk menempatkan makanan secara acak
    Timer gameLoop; // Timer untuk loop game
    boolean gameOver = false; // Menyimpan status game over
    ArrayList<Tile> snakeBody; // List untuk menyimpan bagian tubuh ular
    int velocityX; // Kecepatan ular pada sumbu X
    int velocityY; // Kecepatan ular pada sumbu Y

    // Konstruktor untuk menginisialisasi permainan
    SnakeGame(int x, int y) {
        random = new Random(); // Inisialisasi random
        gameLoop = new Timer(50, this); // Timer dengan interval 50ms
        this.boardHeight = y;
        this.boardWidth = x;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight)); // Mengatur ukuran panel
        setBackground(Color.black); // Mengatur latar belakang panel

        snakeHead = new Tile(5, 5); // Inisialisasi kepala ular
        snakeBody = new ArrayList<Tile>(); // Inisialisasi tubuh ular
        food = new Tile(10, 10); // Inisialisasi makanan

        addKeyListener(this); // Menambahkan listener keyboard
        setFocusable(true); // Memungkinkan panel menerima input keyboard

        placeFood(); // Menempatkan makanan secara acak
        gameLoop.start(); // Memulai game loop
    }

    // Method untuk menggambar game
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Method untuk menggambar elemen game
    public void draw(Graphics g) {
        // Menggambar grid
        for (int i = 0; i < boardWidth / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }

        // Menggambar makanan
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        // Menggambar kepala ular
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        // Menggambar tubuh ular
        for (Tile snakePart : snakeBody) {
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game over " + snakeBody.size(), tileSize - 16, tileSize);
        } else {
            g.drawString("Score: " + snakeBody.size(), tileSize - 16, tileSize);
        }
    }

    // Menempatkan makanan secara acak di dalam grid
    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeight / tileSize);
    }

    // Mengecek apakah dua tile bertabrakan
    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    // Menggerakkan ular
    public void move() {
        // Jika kepala ular bertabrakan dengan makanan
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y)); // Tambahkan tubuh
            placeFood(); // Tempatkan makanan baru
        }

        // Pergerakan tubuh ular mengikuti kepala
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            if (i == 0) {
                snakeBody.get(i).x = snakeHead.x;
                snakeBody.get(i).y = snakeHead.y;
            } else {
                snakeBody.get(i).x = snakeBody.get(i - 1).x;
                snakeBody.get(i).y = snakeBody.get(i - 1).y;
            }
        }

        // Menggerakkan kepala ular
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Mengecek tabrakan dengan tubuh sendiri
        for (Tile snakePart : snakeBody) {
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        // Mengecek tabrakan dengan dinding
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth ||
                snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight) {
            gameOver = true;
        }
    }

    // Event yang dipanggil saat timer berjalan (Game Loop)
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // Pindahkan ular
        repaint(); // Gambar ulang
        if (gameOver) {
            gameLoop.stop(); // Hentikan game loop jika game over
            System.out.print("Game Stopped");
        }
    }

    // Mendeteksi input keyboard
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
