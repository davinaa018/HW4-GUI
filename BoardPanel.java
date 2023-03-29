package HW4_GUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {
    
    private static final Color boardColor = new Color(253, 218, 13);
    private  JLabel turnLabel;
    private Coordinate hoverIntersection;
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;


    public BoardPanel(Board board){
        this.board = board;
        turnLabel = new JLabel();
        this.player1 = new HumanPlayer("Player1", 'X',null);
        this.player2 = new HumanPlayer("Player2", 'O',null);
        this.currentPlayer = player1;
        setBackground(boardColor);
        // Place stones on the board
        placeStone(false);
        // Hovering effect
        hoveringEffect(false);
        
    }

    public JLabel getJLabel(){
        return turnLabel;
    }

    public JLabel setLabel(String text){
        turnLabel.setText(text);
        return turnLabel;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int size = board.getSize();
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
                if (board.getPieceAt(i, j) == player1.getSymbol()) {
                    g.setColor(Color.BLACK);
                    g.fillOval(x+8, y+8, squareSize / 2, squareSize / 2);
                } else if(board.getPieceAt(i, j) == player2.getSymbol() ){
                    g.setColor(Color.WHITE);
                    g.fillOval(x+8, y+8, squareSize / 2, squareSize / 2);
                    g.setColor(Color.BLACK);
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

        if(currentPlayer == player1){
            turnLabel.setText("Black's turn");
        }else{
            turnLabel.setText("White's turn");
        }
    }

    private void checkWinner(int i, int j, Player currentPlayer){
        if(board.hasWinner(i, j)){
            JOptionPane.showMessageDialog(null, currentPlayer.getName() + " wins!");
            System.exit(0);
        }
    }

    private void switchPlayer(){
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }


    public void placeStone(boolean startGame) {
        if(startGame){
            // Place stones on the board
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int i = e.getX() / (getWidth() / board.getSize());
                    int j = e.getY() / (getWidth() / board.getSize());
                    if (i >= 0 && i < board.getSize() && j >= 0 && j < board.getSize()) {
                        if (board.getPieceAt(i, j) == '-') {
                            // place stone and switch player
                            board.makeMove(i, j, currentPlayer.getSymbol());
                            // repaint board
                            repaint();
                            checkWinner(i, j, currentPlayer);
                            switchPlayer();
                        }
                    }
                }
            });


        }
    }

    public void hoveringEffect(boolean startGame){
        if(startGame){
            // Hovering effect
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    int size = board.getSize();
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
    }

    
}
