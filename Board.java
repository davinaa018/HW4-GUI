package HW4_GUI;

import java.util.ArrayList;
import java.util.List;



// Complete the methods in the following code
//  import omok.model.Player;

/**
 * Abstraction of an Omok board, which consists of n x n intersections
 * or places where players can place their stones. The board can be 
 * accessed using a pair of 0-based indices (x, y), where x and y 
 * denote the column and row number, respectively. The top-left 
 * intersection is represented by the indices (0, 0), and the
 * bottom-right intersection is represented by the indices (n-1, n-1).
 */
public class Board {
    private int[][] board;
    private List<Place> winningRow;
    private int size;
    private Player player1;
    private Player player2;
    

    /** Create a new board of the default size. */
    public Board() {
        this(15);
    }

    /** Create a new board of the specified size. */
    public Board(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.winningRow = new ArrayList<>();
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
    }

    /** Return the size of this board. */
    public int size() {
        return size;
    }
    
    /** Removes all the stones placed on the board, effectively 
     * resetting the board to its original state. 
     */
    public void clear() {
        board = new int[size][size];
        winningRow.clear();
    }

    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     */
    public boolean isFull() {
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++){
                if (board[row][col] == 0){
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Place a stone for the specified player at a specified 
     * intersection (x, y) on the board.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {
        if (!isEmpty(x, y)){
            throw new IllegalArgumentException("Place is already occupied");
        }
        // 1 represents Player 1, 2 represents Player 2
        board[x][y] = player.name().equals(player1.name()) ? 1 : 2;
    }
    
    /**
     * Return a boolean value indicating whether the specified 
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isEmpty(int x, int y) {
        return board[x][y] == 0;
    }
    
    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupied(int x, int y) {
        return board[x][y] != 0;
    }

    /**
     * Rreturn a boolean value indicating whether the specified 
     * intersection (x, y) on the board is occupied by the given 
     * player or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupiedBy(int x, int y, Player player) {
        return board[x][y] == (player.name().equals(player1.name()) ? 1 : 2);
    }

    /**
     * Return the player who occupies the specified intersection (x, y) 
     * on the board. If the place is empty, this method returns null.
     * 
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public Player playerAt(int x, int y) {
        if (board[x][y] == 1){
            return player1;
        }else if (board[x][y] == 2){
            return player2;
        }else{
            return null;
        }
    }

    /** 
     * Return a boolean value indicating whether the given player 
     * has a winning row on the board. A winning row is a consecutive 
     * sequence of five or more stones placed by the same player in 
     * a horizontal, vertical, or diagonal direction.
     */
    public boolean isWonBy(Player player) {
        int count = 0;

        // Check Horizontal Win
        for (int y = 0; y < size; y++){
            for (int x = 0; x < size; x++){
                if (isOccupiedBy(x, y, player)){
                    count++;

                    if (count == 5){
                        return true;
                    }
                }else{
                    count = 0;

                }
            }
        }

        // Check Vertical Win
        count = 0;
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                if (isOccupiedBy(x, y, player)){
                    count++;
                    if (count == 5){
                        return true;
                    }
                }else{
                    count = 0;
                }
            }
        }

        //  Check Diagonal Win (top-left to bottom-right)
        count = 0;
        for (int x = 0; x <= size - 5; x++){
            for (int y = 0; y <= size - 5; y++){
                for (int i = 0; i < 5; i++){
                    if (isOccupiedBy(x+i, y+i, player)){
                        count++;
                        if (count == 5){
                            return true;
                        }
                    }else{
                        count = 0;
                    }
                }
            }
        }
        

        // Check Diagonal Win (top-right to bottom-left)
        count = 0;
        for (int x = size - 1; x >= 4; x--){
            for (int y = 0; y <= size - 5; y++){
                for (int i = 0; i < 5; i++){
                    if (isOccupiedBy(x-i, y+i, player)){
                        count++;
                        if (count == 5){
                            return true;
                        }
                    }else{
                        count = 0;
                    }
                }
            }
        }
        return false;
    }

    /** Return the winning row. For those who are not familiar with
     * the Iterable interface, you may return an object of
     * List<Place>. */
    public Iterable<Place> winningRow() {
        winningRow.clear();

    // Check Horizontal Win
    for (int y = 0; y < size; y++) {
        for (int x = 0; x < size; x++) {
            if (isOccupiedBy(x, y, player1)) {
                int count = 1;
                winningRow.add(new Place(x, y));
                while (x + count < size && isOccupiedBy(x + count, y, player1)) {
                    count++;
                    winningRow.add(new Place(x + count - 1, y));
                }
                if (count >= 5) {
                    return winningRow;
                }
                winningRow.clear();
            }else if (isOccupiedBy(x, y, player2)) {
                int count = 1;
                winningRow.add(new Place(x, y));
                while (x + count < size && isOccupiedBy(x + count, y, player2)) {
                    count++;
                    winningRow.add(new Place(x + count - 1, y));
                }
                if (count >= 5) {
                    return winningRow;
                }
                winningRow.clear();
            }
        }
    }

    // Check Vertical Win
    for (int x = 0; x < size; x++) {
        for (int y = 0; y < size; y++) {
            if (isOccupiedBy(x, y, player1)) {
                int count = 1;
                winningRow.add(new Place(x, y));
                while (y + count < size && isOccupiedBy(x, y + count, player1)) {
                    count++;
                    winningRow.add(new Place(x, y + count - 1));
                }
                if (count >= 5) {
                    return winningRow;
                }
                winningRow.clear();
            }else if (isOccupiedBy(x, y, player2)) {
                int count = 1;
                winningRow.add(new Place(x, y));
                while (y + count < size && isOccupiedBy(x, y + count, player2)) {
                    count++;
                    winningRow.add(new Place(x, y + count - 1));
                }
                if (count >= 5) {
                    return winningRow;
                }
                winningRow.clear();
            }
        }
    }

    //  Check Diagonal Win (top-left to bottom-right)
    for (int x = 0; x <= size - 5; x++) {
        for (int y = 0; y <= size - 5; y++) {
            if (isOccupiedBy(x, y, player1)) {
                int count = 1;
                winningRow.add(new Place(x, y));
                while (x + count < size && y + count < size && isOccupiedBy(x + count, y + count, player1)) {
                    count++;
                    winningRow.add(new Place(x + count - 1, y + count - 1));
                }
                if (count >= 5) {
                    return winningRow;
                }
                winningRow.clear();
            }else if (isOccupiedBy(x, y, player2)) {
                int count = 1;
                winningRow.add(new Place(x, y));
                while (x + count < size && y + count < size && isOccupiedBy(x + count, y + count, player2)) {
                    count++;
                    winningRow.add(new Place(x + count - 1, y + count - 1));
                }
                if (count >= 5) {
                    return winningRow;
                }
                winningRow.clear();
            }
        }
    }

    // Check Diagonal Win (top-right to bottom-left)
    for (int x = size - 1; x >= 4; x--) {
        for (int y = 0; y <= size - 5; y++) {
            if (isOccupiedBy(x, y, player1)) {
                int count = 1;
                winningRow.add(new Place(y, x));
                while (x - count >= 0 && y + count < size && isOccupiedBy(x - count, y + count, player1)) {
                    count++;
                    winningRow.add(new Place(y+count - 1,x - count + 1));
                }
                if (count >= 5) {
                    return winningRow;
                }
                winningRow.clear();
            }else if (isOccupiedBy(x, y, player2)) {
                int count = 1;
                winningRow.add(new Place(x, y));
                while (x - count >= 0 && y + count < size && isOccupiedBy(x - count, y + count, player2)) {
                    count++;
                    winningRow.add(new Place(y+count - 1,x - count + 1));
                }
                if (count >= 5) {
                    return winningRow;
                }
                winningRow.clear();
            }
        }
    }

    return winningRow;

    }

    /**
     * An intersection on an Omok board identified by its 0-based column
     * index (x) and row index (y). The indices determine the position 
     * of a place on the board, with (0, 0) denoting the top-left 
     * corner and (n-1, n-1) denoting the bottom-right corner, 
     * where n is the size of the board.
     */
    public static class Place {
        /** 0-based column index of this place. */
        public final int x;

        /** 0-based row index of this place. */
        public final int y;

        /** Create a new place of the given indices. 
         * 
         * @param x 0-based column (vertical) index
         * @param y 0-based row (horizontal) index
         */
        public Place(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // other methods if needed ...
        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
