package HW4_GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

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

        JPanel North = new JPanel();
        North.setLayout(new BorderLayout());

        // Menu
        createMenu();

        // ToolBar
        JPanel toolBar = createToolBar();
        North.add(toolBar, BorderLayout.NORTH);

        // ComboBox
        JPanel comboBox = createComboBox();
        North.add(comboBox);

        // Board
        JPanel board = createBoard();
        add(board, BorderLayout.CENTER);

        add(North, BorderLayout.NORTH);

    }

    private void createMenu(){
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
        setJMenuBar(menuBar);
    }

    private JPanel createToolBar(){
        JPanel panel = new JPanel();
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(true);
        toolBar.addSeparator();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton button1 = new JButton("Play");
        button1.addActionListener(e -> JOptionPane.showConfirmDialog(null, "Do you want to start a new game?", "New Game", JOptionPane.YES_NO_OPTION));
        JButton button2 = new JButton("About");
        toolBar.add(button1);
        toolBar.add(button2);

        panel.add(toolBar);
        return panel;
    }

    private JPanel createComboBox(){
        JPanel panel = new JPanel();
        JButton play = new JButton("Play");
        panel.add(play);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.add(new JLabel(" Opponent:"));
        var comboBox = new JComboBox<>(new String[] { "Human", "Computer" });
        panel.add(comboBox);
        return panel;
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
