import javax.swing.*; // Import library untuk GUI

public class App {
    public static void main(String[] args) {
        int width = 600; // Lebar frame
        int height = width; // Tinggi frame, dibuat sama dengan lebar
        JFrame frame = new JFrame("Snake"); // Membuat frame dengan judul "Snake"
        frame.setVisible(true); // Menampilkan frame
        frame.setSize(width, height); // Mengatur ukuran frame
        frame.setLocationRelativeTo(null); // Menempatkan frame di tengah layar
        frame.setResizable(false); // Membuat frame tidak bisa di-resize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Menutup program saat frame ditutup

        SnakeGame snakeGame = new SnakeGame(width, height); // Membuat objek game
        frame.add(snakeGame); // Menambahkan game ke frame
        frame.pack(); // Menyesuaikan ukuran frame berdasarkan komponen yang ada
        snakeGame.requestFocus(); // Memfokuskan input ke game
    }
}