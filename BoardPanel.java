package HW4_GUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import HW4_GUI.console.Board;
import HW4_GUI.console.ComputerPlayer;
import HW4_GUI.console.Coordinate;
import HW4_GUI.console.HumanPlayer;
import HW4_GUI.console.Player;

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
    private Player opponent;
    private Player currentPlayer;


    public BoardPanel(Board board){
        this.board = board;
        turnLabel = new JLabel();
        this.player1 = new HumanPlayer("Player1", 'X',null);
        this.opponent = new HumanPlayer("Player2", 'O',null);
        this.currentPlayer = player1;
        setBackground(boardColor);
        // Place stones on the board
        placeStone(false);
        // Hovering effect
        hoveringEffect(false);
        
    }

    /**
     * Gets the current player
     * @return currentPlayer
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Sets the current player
     * @param player
     */
    public void setCurrentPlayer(Player player){
       this.currentPlayer = player;  
    }

    /**
     * Gets the player1
     * @return player1
     */
    public Player getPlayer1(){
        return player1;
    }

    /**
     * Sets the player1
     * @param player1
     */
    public void setPlayer1(Player player1){
        this.player1 = player1;
    }

    /**
     * Gets the opponent
     * @return opponent
     */
    public Player getOpponent(){
        return opponent;
    }

    /**
     * Sets the opponent
     * @param opponent
     */
    public void setOpponent(Player opponent){
        this.opponent = null;
        this.opponent = opponent;
    }


    /**
     * Gets the label
     * @return turnLabel
     */
    public JLabel getJLabel(){
        return turnLabel;
    }

    /**
     * Sets the label
     * @param label
     */
    public void setLabel(String text){
        turnLabel.setText(text);
    }

    /**
     * Initializes the board
     */
    public void initializeBoard(){
        board.initializeBoard();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int size = board.getSize();
        int squareSize = getWidth() / size;
        // Draw board
        drawBoard(size, squareSize, g);
        // Draw stones
        drawStones(size, squareSize, g);
        // Hovering effect
        drawHoverEffect(size, squareSize, g);
        if(currentPlayer == player1){
            turnLabel.setText("Black's turn");
        }else{
            turnLabel.setText("White's turn");
        }
    }

    /**
     * Draws the board
     * @param size
     * @param squareSize
     * @param g
     */
    private void drawBoard(int size, int squareSize, Graphics g){
         // Draw horizontal lines
         for (int i = 0; i < size; i++) {
            g.drawLine(0, i * squareSize, getWidth(), i * squareSize );
        }

        // Draw vertical lines
        for (int i = 0; i < size; i++) {
            g.drawLine(i * squareSize, 0, i * squareSize, getHeight());
        }
    }

    /**
     * Draws the stones
     * @param size
     * @param squareSize
     * @param g
     */
    private void drawStones(int size, int squareSize, Graphics g){
        //  Draw Stones
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = i * squareSize + squareSize / 2;
                int y = j * squareSize + squareSize / 2;
                if (board.getPieceAt(i, j) == player1.getSymbol()) {
                    g.setColor(Color.BLACK);
                    g.fillOval(x+8, y+8, squareSize / 2, squareSize / 2);
                    if (board.hasWinner(i, j)) {
                        g.setColor(Color.RED);
                        g.fillOval(x+10, y+10, squareSize / 2 - 4, squareSize / 2 - 4);
                    }
                } else if (board.getPieceAt(i, j) == getOpponent().getSymbol()) {
                    g.setColor(Color.WHITE);
                    g.fillOval(x+8, y+8, squareSize / 2, squareSize / 2);
                    g.setColor(Color.BLACK);
                    if (board.hasWinner(i, j)) {
                        g.setColor(Color.RED);
                        g.fillOval(x+10, y+10, squareSize / 2 - 4, squareSize / 2 - 4);
                    }
                }
                
            }
        }
    }

    /**
     * Draws the hovering effect
     * @param size
     * @param squareSize
     * @param g
     */
    private void drawHoverEffect(int size, int squareSize, Graphics g){
        if (hoverIntersection != null) {
            int x = hoverIntersection.getX() * squareSize + squareSize-7;
            int y = hoverIntersection.getY() * squareSize + squareSize-7;
            int ovalSize = squareSize / 2;
            g.drawOval(x, y, ovalSize, ovalSize);
        }
    }

    /**
     * Checks if there is a winner, if so, it displays a message and exits the game
     * @param i
     * @param j
     * @param currentPlayer
     */
    private void checkWinner(int i, int j, Player currentPlayer){
        if(board.hasWinner(i, j)){
            JOptionPane.showMessageDialog(null, currentPlayer.getName() + " wins!");
            System.exit(0);
        }
    }

    /**
     * Checks if the board is full, if so, it displays a message and exits the game
     */
    private void checkDraw(){
        if(board.isFull()){
            JOptionPane.showMessageDialog(null, "Draw!");
            System.exit(0);
        }
    }

    /**
     * Makes a move for the computer
     */
    private void computerMove(){
        Coordinate computerMove = getOpponent().pickPlace(board);
        board.makeMove(computerMove.getX(), computerMove.getY(), getOpponent().getSymbol());
        repaint();
        checkWinner(computerMove.getX(), computerMove.getY(), getOpponent());
        checkDraw();
        switchPlayer();

    }

    /**
     * Switches the player
     */
    private void switchPlayer(){
        currentPlayer = currentPlayer == player1 ? getOpponent() : player1;
    }


    /**
     * Places a stone on the board by using MouseListener
     * @param startGame
     */
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
                            if(currentPlayer instanceof HumanPlayer){
                                board.makeMove(i, j, currentPlayer.getSymbol()); 
                                repaint();
                                checkWinner(i, j, currentPlayer);
                                checkDraw();
                                switchPlayer();
                                if (currentPlayer instanceof ComputerPlayer){
                                    computerMove();  
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * Adds a hovering effect to the board
     * @param startGame
     */
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
