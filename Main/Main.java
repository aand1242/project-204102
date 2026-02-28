package Main;

import Main.UI.Background;
import Main.UI.BoardGUI;
import Main.UI.ItemslotGUI;
import Main.UI.Revive;
import Main.UI.RuleUI;
import Main.UI.StartUI;
import Main.UI.Tranfrom;
import Main.logic.Board;
import Main.logic.GameControl;
import Main.logic.Player;
import Main.logic.StartLogic;
import java.awt.*;
import javax.swing.*;

public class Main {

    // DEBUG
    public static final boolean DEBUG = true;

    public static final int SCALE = 2;
    public static final int width = 640;
    public static final int height = 360;

    public static void main(String[] args) {
        int finalWidth = width * SCALE;
        int finalHeight = height * SCALE;

        // screen create
        JFrame screen = new JFrame();
        //screen.setSize(width, height);
        JLayeredPane layer = new JLayeredPane();
        layer.setPreferredSize(new Dimension(finalWidth, finalHeight));

        Board board = new Board();

        GameControl gameLogic = new GameControl(board);

        JPanel middle = new JPanel();
        middle.setPreferredSize(new Dimension(328 * 2, 328 * 2));
        middle.setLayout(new GridBagLayout());
        middle.setOpaque(false);

        BoardGUI boardUI = new BoardGUI(gameLogic);
        boardUI.setPreferredSize(new Dimension(288 * 2, 288 * 2));
        gameLogic.setBordGUI(boardUI);
        boardUI.setPieceGUI(board.getBoard());

        middle.add(boardUI);

        JPanel midslot = new JPanel();
        midslot.setPreferredSize(new Dimension(328 * 2, 360 * 2));
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

        ItemslotGUI leftslot = new ItemslotGUI(white.getScore(), gameLogic);
        leftslot.setPreferredSize(new Dimension(156 * 2, 360 * 2));
        leftslot.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 44 * 2));
        leftslot.setBackground(Color.DARK_GRAY);
        ItemslotGUI rightslot = new ItemslotGUI(black.getScore(), gameLogic);
        rightslot.setPreferredSize(new Dimension(156 * 2, 360 * 2));
        rightslot.setBorder(BorderFactory.createEmptyBorder(0, 44 * 2, 0, 0));
        rightslot.setBackground(Color.DARK_GRAY);

        Revive whRevive = new Revive(true);
        whRevive.setBounds(220 * 2, 80 * 2, 240 * 2, 160 * 2);
        Revive bRevive = new Revive(false);
        bRevive.setBounds(220 * 2, 80 * 2, 240 * 2, 160 * 2);
        gameLogic.setReviveUI(whRevive, bRevive);

        gameLogic.setItemscore(leftslot, rightslot);

        Tranfrom tranfromlayer = new Tranfrom();
        gameLogic.setTranfrom(tranfromlayer);
        tranfromlayer.setPreferredSize(new Dimension(200 * 2, 200 * 2));
        tranfromlayer.setBounds(220 * 2, 80 * 2, 200 * 2, 200 * 2);

        Background bg = new Background();
        bg.setBounds(0, 0, finalWidth, finalHeight);
        bg.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        bg.add(leftslot);
        bg.add(midslot);
        bg.add(rightslot);
        // screen setting for JFrame
        StartLogic stg = new StartLogic();

        StartUI startUI = new StartUI();
        RuleUI ruleui = new RuleUI();
        startUI.setStartLogic(stg);
        startUI.setBounds(0, 0, finalWidth, finalHeight);
        ruleui.setBounds(136,77,1008,567);
        stg.setStartUI(startUI);
        stg.setRuleUI(ruleui);

        layer.add(bg, JLayeredPane.DEFAULT_LAYER);
        layer.add(tranfromlayer, JLayeredPane.MODAL_LAYER);
        layer.add(bRevive, JLayeredPane.MODAL_LAYER);
        layer.add(whRevive, JLayeredPane.MODAL_LAYER);
        layer.add(startUI, JLayeredPane.POPUP_LAYER);
        layer.add(ruleui,Integer.valueOf(301));

        screen.add(layer);
        screen.pack();
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setResizable(false);
        screen.setVisible(true);

    }
}
