
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class App extends JFrame {

    private final int boardWith = 600;
    private final int boardHeight = 650;

    private final JLabel textLabel = new JLabel();
    private final JPanel textPanel = new JPanel();
    private final JPanel boardPanel = new JPanel();

    private JButton[][] board = new JButton[3][3];
    private String playerX = "X";
    private String playerY = "Y";
    private String currentPlayer = playerX;
    private boolean gameOver = false;

    public App() {
        setResizable(false);
        setSize(boardWith, boardHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic Tac Toe");
        textLabel.setFont(new Font("Fira Code", Font.BOLD, 50));
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                button.setBackground(Color.darkGray);
                button.setForeground(Color.white);
                button.setFont(new Font("Fira Code", Font.BOLD, 100));
                button.setFocusable(false);
                button.addActionListener(event -> {

                    if (gameOver) {
                        return;
                    }
                    if (!button.getText().isEmpty()) {
                        return;
                    }

                    button.setText(currentPlayer);

                    checkWinner();

                    if (gameOver) {
                        return;
                    }

                    if (currentPlayer.equals(playerX)) {
                        currentPlayer = playerY;
                    } else {
                        currentPlayer = playerX;
                    }

                    textLabel.setText(currentPlayer + "'s turn!");
                });

                board[i][j] = button;
                boardPanel.add(button);
            }
        }

        add(textPanel, BorderLayout.NORTH);
        add(boardPanel);
    }

    private void checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().isEmpty()) {
                continue;
            }

            if (board[i][0].getText().equals(board[i][1].getText())
                    && board[i][1].getText().equals(board[i][2].getText())) {

                for (int j = 0; j < 3; j++) {
                    setWinner(board[i][j]);
                }

                gameOver = true;
                return;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i].getText().isEmpty()) {
                continue;
            }

            if (board[0][i].getText().equals(board[1][i].getText())
                    && board[1][i].getText().equals(board[2][i].getText())) {

                for (int j = 0; j < 3; j++) {
                    setWinner(board[j][i]);
                }

                gameOver = true;
                return;
            }
        }

        if (board[0][0].getText().equals(board[1][1].getText())
                && board[1][1].getText().equals(board[2][2].getText())
                && !board[0][0].getText().isEmpty()) {
            for (int j = 0; j < 3; j++) {
                setWinner(board[j][j]);
            }

            gameOver = true;
            return;
        }

        if (board[0][2].getText().equals(board[1][1].getText())
                && board[1][1].getText().equals(board[2][0].getText())
                && !board[0][2].getText().isEmpty()) {

            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);

            gameOver = true;
        }
    }

    private void setWinner(JButton button) {
        button.setForeground(Color.GREEN);
        button.setBackground(Color.lightGray);
        textLabel.setText(button.getText() + " won the match!");
    }

    public static void main(String[] args) {
        var app = new App();
        app.setVisible(true);
        app.setTitle("Tic Tac Toe");
    }
}
