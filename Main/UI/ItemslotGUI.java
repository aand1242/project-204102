package Main.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Main.logic.GameControl;

public class ItemslotGUI extends JPanel implements ActionListener {

    // for item slot with point at the two side im lazy
    private JButton[] itemlist = new JButton[5];
    private JLabel scoreLabel;

    // Item Name
    private String[] itemList = {"RECALL", "SNIPER", "UNICORN", "MOVE+", "SHIELD"};
    private static final ImageIcon[] IconItem = {new ImageIcon("Main\\source_pic\\recall_test.png"),
                                                new ImageIcon("Main\\source_pic\\sniper.png"),
                                                new ImageIcon("Main\\source_pic\\unicon.png"),
                                                new ImageIcon("Main\\source_pic\\pluspng.png"),
                                                new ImageIcon("Main\\source_pic\\shield.png")};
    private GameControl controller;

    public ItemslotGUI(int n, GameControl ctrl) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        scoreLabel = new JLabel(Integer.toString(n));
        scoreLabel.setPreferredSize(new Dimension(40, 20));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 12, 0);
        add(scoreLabel, gbc);
        for (int i = 0; i < 5; i++) {
            itemlist[i] = new JButton();
            itemlist[i].setPreferredSize(new Dimension(48, 48));
            itemlist[i].putClientProperty("item", i);
            itemlist[i].putClientProperty("name", itemList[i]);
            itemlist[i].addActionListener(this);
            itemlist[i].setIcon(IconItem[i]);

            gbc.gridy = i + 1;
            gbc.insets = new Insets(0, 0, 8, 0);

            add(itemlist[i], gbc);
        }
        setOpaque(false);

        controller = ctrl;
    }

    public void changeScore(int n) {
        scoreLabel.setText(Integer.toString(n));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedItem = (JButton) e.getSource();

        String item = (String) clickedItem.getClientProperty("name");

        controller.itemUsed(item);
    }

}
