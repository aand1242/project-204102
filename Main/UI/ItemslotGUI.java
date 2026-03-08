package Main.UI;

import Main.logic.GameControl;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ItemslotGUI extends JPanel implements ActionListener {

    // for item slot with point at the two side im lazy
    private JButton[] itemlist = new JButton[5];
    private JLabel scoreLabel;

    // Item Name
    private String[] itemList = {"RECALL", "SNIPER", "UNICORN", "MOVE+", "SHIELD"};
    private final ImageIcon[] IconItem = {getScaledIcon("Main\\source_pic\\Recall_icon.png"),
        getScaledIcon("Main\\source_pic\\Sniper_icon.png"),
        getScaledIcon("Main\\source_pic\\Unicorn_icon.png"),
        getScaledIcon("Main\\source_pic\\Moveplus_icon.png"),
        getScaledIcon("Main\\source_pic\\Shield_icon.png")};
    private GameControl controller;

    public ItemslotGUI(int n, GameControl ctrl) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        scoreLabel = new JLabel(Integer.toString(n));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setPreferredSize(new Dimension(75 * 2, 50 * 2));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Jacquard 24", Font.PLAIN, 50 * 2));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 12 * 2, 0);
        add(scoreLabel, gbc);
        for (int i = 0; i < 5; i++) {
            itemlist[i] = new JButton(IconItem[i]);
            itemlist[i].setPreferredSize(new Dimension(48 * 2, 48 * 2));
            itemlist[i].putClientProperty("item", i);
            itemlist[i].putClientProperty("name", itemList[i]);
            itemlist[i].addActionListener(this);
            itemlist[i].setOpaque(false);
            itemlist[i].setContentAreaFilled(false);
            itemlist[i].setBorderPainted(false);
            //itemlist[i].setIcon(IconItem[i]);

            gbc.gridy = i + 1;
            gbc.insets = new Insets(0, 0, 8 * 2, 0);

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

    private ImageIcon getScaledIcon(String path) {
        ImageIcon originalIcon = new ImageIcon(path);

        Image scaledImage = originalIcon.getImage().getScaledInstance(
                originalIcon.getIconWidth() * 2,
                originalIcon.getIconHeight() * 2,
                Image.SCALE_REPLICATE
        );
        return new ImageIcon(scaledImage);
    }

}
