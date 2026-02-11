package Main;

import java.awt.*;
import javax.swing.*;


public class Main {
    // DEBUG
    public static final boolean DEBUG = true;

    public static final int height = 640;
    public static final int width = 360;
    
    public static void main(String[] args) {

        Board board = new Board();
        
        GameControl gameLogic = new GameControl(board);

        BoardGUI boardPanel = new BoardGUI(gameLogic);
        gameLogic.setBordGUI(boardPanel);
        boardPanel.setPiece(board.getBoard());

        boardPanel.setPreferredSize(new Dimension(288,288));

        JFrame screen = new JFrame();
        screen.setSize(height,width);
        // screen.setLayout(new GridBagLayout());
        
        Background bg = new Background();
        bg.add(boardPanel);
        screen.add(bg);
        
        screen.pack();
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setResizable(false);
        screen.setVisible(true);

        




    }
}
