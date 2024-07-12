
import javax.swing.JFrame;

public class App{

    public static void main(String[] args) throws Exception {

        var frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.setTitle("Snake");
        frame.setVisible(true);

        var snake = new Snake(600, 600);
        frame.add(snake);
        snake.requestFocus();
        frame.pack();
    }
}
