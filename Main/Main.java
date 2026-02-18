package Main;

import java.awt.*;
import javax.swing.*;
// import javax.xml.stream.FactoryConfigurationError;


public class Main {
    // DEBUG
    public static final boolean DEBUG = true;

    public static final int height = 640;
    public static final int width = 360;
    
    public static void main(String[] args) {
        // screen create
        JFrame screen = new JFrame();
        JLayeredPane layer = new JLayeredPane();
        screen.setSize(height,width);
        layer.setPreferredSize(new Dimension(height,width));

        Board board = new Board();
        
        GameControl gameLogic = new GameControl(board);

        JPanel middle = new JPanel();
        middle.setPreferredSize(new Dimension(328,328));
        middle.setLayout(new GridBagLayout());
        middle.setOpaque(false);
        
        BoardGUI boardUI = new BoardGUI(gameLogic);
        boardUI.setPreferredSize(new Dimension(288,288));
        gameLogic.setBordGUI(boardUI);
        boardUI.setPiece(board.getBoard());
        
        middle.add(boardUI);
        
        JPanel midslot = new JPanel();
        midslot.setPreferredSize(new Dimension(328,360));
        midslot.setOpaque(false);
        midslot.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // 1. บอกตำแหน่งพิกัดในตาราง (ถ้ามีอันเดียวก็เริ่มที่ 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        // 2. ให้น้ำหนักพื้นที่ เพื่อให้มันแผ่ขยายออกไปดันขอบจอ
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        // 3. สั่งให้ตัววัตถุมาเกาะที่จุดศูนย์กลางของพื้นที่ที่แผ่ออกมา
        gbc.anchor = GridBagConstraints.CENTER;

        midslot.add(middle, gbc);  

        Player white = new Player();
        Player black = new Player();
        gameLogic.setPlayer(white, black);

        ItemslotGUI leftslot = new ItemslotGUI(white.getScore());
        leftslot.setPreferredSize(new Dimension(156,360));
        leftslot.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 44));
        leftslot.setBackground(Color.DARK_GRAY);
        ItemslotGUI rightslot = new ItemslotGUI(black.getScore());
        rightslot.setPreferredSize(new Dimension(156,360));
        rightslot.setBorder(BorderFactory.createEmptyBorder(0, 44, 0, 0));
        rightslot.setBackground(Color.DARK_GRAY);

        gameLogic.setItemscore(leftslot, rightslot);

        Tranfrom tranfromlayer = new Tranfrom();
        gameLogic.setTranfrom(tranfromlayer);
        tranfromlayer.setPreferredSize(new Dimension(200,200));
        tranfromlayer.setBounds(220, 80, 200, 200);

        Background bg = new Background();
        bg.setBounds(0,0 ,640,360);
        bg.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        bg.add(leftslot);
        bg.add(midslot);
        bg.add(rightslot);
        // screen setting for JFrame
        layer.add(bg,JLayeredPane.DEFAULT_LAYER);
        layer.add(tranfromlayer,JLayeredPane.MODAL_LAYER);

        screen.add(layer);
        screen.pack();
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setResizable(false);
        screen.setVisible(true);

        




    }
}
