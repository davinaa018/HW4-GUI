package HW4_GUI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {
    
    private static final Color boardColor = new Color(253, 218, 13);
    private Coordinate hoverIntersection;
    private Board board;
    private Player player;


    public BoardPanel(Board board){
        this.board = board;
        player = new Player("Player 1");
        setBackground(boardColor);

        // Place stones on the board
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = e.getX() / (getWidth() / board.size());
                int j = e.getY() / (getWidth() / board.size());
                if (i >= 0 && i < board.size() && j >= 0 && j < board.size()) {
                    if (board.isEmpty(i, j)) {
                        // place stone and switch player
                        board.placeStone(i, j, player);
                        // repaint board
                        repaint();
                    }
                }
            }
        });

        // Hovering effect
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int size = board.size();
                int squareSize = getWidth() / size;
                int i = e.getX() / squareSize;
                int j = e.getY() / squareSize;
                if (i >= 0 && i < size && j >= 0 && j < size) {
                    hoverIntersection = new Coordinate(i, j);
                } else {
                    hoverIntersection = null;
                }
                repaint();
            }
        });
        
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

        //  Draw Stones
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = i * squareSize + squareSize / 2;
                int y = j * squareSize + squareSize / 2;
                if (board.isOccupiedBy(i, j, player)) {
                    g.setColor(Color.BLACK);
                    g.fillOval(x+8, y+8, squareSize / 2, squareSize / 2);
                } 
            }
        }

        // Hovering effect
        if (hoverIntersection != null) {
            int x = hoverIntersection.getX() * squareSize + squareSize-7;
            int y = hoverIntersection.getY() * squareSize + squareSize-7;
            int ovalSize = squareSize / 2;
            g.drawOval(x, y, ovalSize, ovalSize);
        }
    }
}
