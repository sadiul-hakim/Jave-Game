
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener, KeyListener {

    private int boardWidth;
    private int boardHeight;
    private int tileSize = 25;
    private Tile snakeHead;
    private List<Tile> body;
    private Tile food;
    private Random random;

    private Timer gameLoop;
    private int volacityX;
    private int volacityY;
    private boolean gameOver;

    public Snake(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        body = new ArrayList<>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();
        volacityX = 0;
        volacityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        for (int i = 0; i < boardWidth / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }

        // food
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        // snake head
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        // body
        body.forEach(b -> {
            g.fillRect(b.x * tileSize, b.y * tileSize, tileSize, tileSize);
        });

        g.setFont(new Font("Fira Code", Font.BOLD, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(body.size()), tileSize - 16, tileSize);
        }else{
            g.setColor(Color.green);
            g.drawString("Score: " + String.valueOf(body.size()), tileSize - 16, tileSize);
        }
    }

    public boolean collision(Tile snake, Tile food) {
        return snake.x == food.x && snake.y == food.y;
    }

    private void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeight / tileSize);
    }

    private void move() {

        if (collision(snakeHead, food)) {
            body.add(new Tile(food.x, food.y));
            placeFood();
        }

        // move body
        for (int i = body.size() - 1; i >= 0; i--) {
            var part = body.get(i);
            if (i == 0) {
                part.x = snakeHead.x;
                part.y = snakeHead.y;
            } else {
                var prev = body.get(i - 1);
                part.x = prev.x;
                part.y = prev.y;
            }
        }

        snakeHead.x += volacityX;
        snakeHead.y += volacityY;

        body.forEach(part -> {
            if (collision(part, snakeHead)) {
                gameOver = true;
            }
        });

        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth
                || snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                if (volacityY != 1) {
                    volacityX = 0;
                    volacityY = -1;
                }
            }
            case KeyEvent.VK_DOWN -> {
                if (volacityY != -1) {
                    volacityX = 0;
                    volacityY = 1;
                }
            }
            case KeyEvent.VK_LEFT -> {
                if (volacityX != 1) {
                    volacityX = -1;
                    volacityY = 0;
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (volacityX != -1) {
                    volacityX = 1;
                    volacityY = 0;
                }
            }
        }
    }

    private class Tile {

        private int x;
        private int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
