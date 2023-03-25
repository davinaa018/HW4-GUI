package HW4_GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class BoardPanel extends JPanel {
    
    private static final Color boardColor = new Color(253, 218, 13);
    private Board board;

    public BoardPanel(Board board){
        this.board = board;
        setBackground(boardColor);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int size = board.size();
        int squareSize = getWidth() / size;

        // Draw horizontal lines
        for (int i = 0; i < size; i++) {
            g.drawLine(0, i * squareSize, getWidth(), i * squareSize);
        }

        // Draw vertical lines
        for (int i = 0; i < size; i++) {
            g.drawLine(i * squareSize, 0, i * squareSize, getHeight());
        }
    }

    public static void main(String[] args) {
        Board board = new Board(15);
        BoardPanel panel = new BoardPanel(board);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);

    }
}
