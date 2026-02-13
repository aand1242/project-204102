package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ItemslotGUI extends JPanel implements ActionListener{
    // for item slot with point at the two side im lazy
    private JButton[] itemlist = new JButton[5];
    private JLabel scoreLabel;
    public ItemslotGUI(int n){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        scoreLabel = new JLabel(Integer.toString(n));
        scoreLabel.setPreferredSize(new Dimension(20,20));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 12, 0);
        add(scoreLabel,gbc);
        for(int i = 0; i < 5 ;i++){
            itemlist[i] = new JButton();
            itemlist[i].setPreferredSize(new Dimension(48,48));
            itemlist[i].putClientProperty("item", i);


            gbc.gridy = i+1;
            gbc.insets = new Insets(0,0,8,0);
            
            add(itemlist[i],gbc);
        }
        setOpaque(false );
    }

    public void changeScore(int n){
        scoreLabel.setText(Integer.toString(n));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // for add item and use
        
    }
}