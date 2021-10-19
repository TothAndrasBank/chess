package board;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Font;

public class Board {
    public static final int SIZE = 8;
    private final JFrame frame;
    private final JButton[][] board = new JButton[8][8];
    private final JTextArea text = new JTextArea();

    {
        frame = new JFrame("Chess");
        boardInit();
        borderInit();
        textAreaInit();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public void setBoardImage(final int x, final int y, final ImageIcon icon) {
        board[x][y].setIcon(icon);
    }

    public JButton getBoard(final int x, final int y) {
        return board[x][y];
    }

    private void textAreaInit() {
        text.setText("Text:");
        text.setLocation(530, 150);
        text.setSize(200, 300);
        frame.add(text);
        final JLabel bug = new JLabel();
        frame.add(bug);
    }



    private void borderInit() {
        final JLabel lowerBorder = new JLabel(" A   B   C   D   E   F   G   H");
        lowerBorder.setFont(new Font("Comic sands", Font.BOLD, 34));
        lowerBorder.setSize(450, 25);
        lowerBorder.setLocation(100, 510);
        frame.add(lowerBorder);

        final JLabel[] leftBorder = new JLabel[8];
        for (int i = 0; i < leftBorder.length; i++) {
            leftBorder[i] = new JLabel(i + 1 + "");
            leftBorder[i].setLocation(75, 112 + 50 * i);
            leftBorder[i].setFont(new Font("Comic sands", Font.BOLD, 34));
            leftBorder[i].setSize(25, 25);
            frame.add(leftBorder[i]);
        }
    }

    public void setBoardDefaultColor() {
        boolean isWhite = false;
        for (final JButton[] jButtons : board) {
            for (final JButton jButton : jButtons) {
                if (isWhite) {
                    jButton.setBackground(Color.WHITE);
                    isWhite = false;
                } else {
                    jButton.setBackground(Color.DARK_GRAY);
                    isWhite = true;
                }
            }
            isWhite = !isWhite;
        }
    }


    private void boardInit() {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new JButton();
                board[i][j].setSize(50, 50);
                board[i][j].setLocation(100 + 50 * i, 100 + 50 * j);
                frame.add(board[i][j]);
            }
        }
        setBoardDefaultColor();
    }


}
