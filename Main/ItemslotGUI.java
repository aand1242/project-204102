package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ItemslotGUI extends JPanel implements ActionListener{
    // for item slot with point at the two side im lazy
    private JButton[] itemlist = new JButton[5];
    public ItemslotGUI(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        for(int i = 0; i < 5 ;i++){
            itemlist[i] = new JButton();
            itemlist[i].setPreferredSize(new Dimension(48,48));
            itemlist[i].putClientProperty("item", i);


            gbc.gridy = i;
            gbc.insets = new Insets(0,0,8,0);
            
            add(itemlist[i],gbc);
        }
        setOpaque(false );
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // for add item and use
        
    }
}