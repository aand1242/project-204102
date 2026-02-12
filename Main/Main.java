package Main;

import java.awt.*;
import javax.swing.*;
import javax.xml.stream.FactoryConfigurationError;


public class Main {
    // DEBUG
    public static final boolean DEBUG = true;

    public static final int height = 640;
    public static final int width = 360;
    
    public static void main(String[] args) {
        // screen create
        JFrame screen = new JFrame();
        screen.setSize(height,width);

        Board board = new Board();
        
        GameControl gameLogic = new GameControl(board);

        BoardGUI boardUI = new BoardGUI(gameLogic);
        boardUI.setPreferredSize(new Dimension(288,288));
        gameLogic.setBordGUI(boardUI);
        boardUI.setPiece(board.getBoard());

        ItemslotGUI leftslot = new ItemslotGUI();
        leftslot.setPreferredSize(new Dimension(156,360));
        leftslot.setBackground(Color.DARK_GRAY);
        ItemslotGUI rightslot = new ItemslotGUI();
        rightslot.setPreferredSize(new Dimension(156,360));
        rightslot.setBackground(Color.DARK_GRAY);

        
        Background bg = new Background();
        bg.add(leftslot);
        bg.add(boardUI);
        bg.add(rightslot);
        // screen setting for JFrame
        screen.add(bg);
        
        screen.pack();
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setResizable(false);
        screen.setVisible(true);

        




    }
}
