package HW4_GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OmokUI extends JFrame {
    
    private BoardPanel boardPanel;

    public OmokUI(Board board){
        super("Omok Game");
        boardPanel = new BoardPanel(board);
        add(boardPanel);
        configureUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    private void configureUI(){
        setLayout(new BorderLayout());


        // Menu
        JPanel menu = createMenu();
        add(menu, BorderLayout.NORTH);

        // Board
        JPanel board = createBoard();
        add(board, BorderLayout.CENTER);




    }

    private JPanel createMenu(){
        JPanel menus = new JPanel();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");

        menu.setMnemonic(KeyEvent.VK_G);
        menu.getAccessibleContext().setAccessibleDescription("MENU");
        menuBar.add(menu);

        JMenuItem menuItem1 = new JMenuItem("Play", KeyEvent.VK_N);
        JMenuItem menuItem2 = new JMenuItem("About", KeyEvent.VK_N);
        JMenuItem menuItem3 = new JMenuItem("Exit", KeyEvent.VK_N);

        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);

        menuItem1.addActionListener(e -> JOptionPane.showConfirmDialog(null, "Do you want to start a new game?", "New Game", JOptionPane.YES_NO_OPTION));

        setJMenuBar(menuBar);
        setVisible(true);
        return menus;
    }


    private JPanel createBoard(){
        JPanel board = new JPanel();
        // board.setBorder(BorderFactory.createEmptyBorder(10,30,20,30));
        board.setLayout(new GridLayout(1,1));
        board.add(boardPanel);
        return board;
    }



    public static void main(String[] args) {
        Board board = new Board(15);
        new OmokUI(board);
    }


}
