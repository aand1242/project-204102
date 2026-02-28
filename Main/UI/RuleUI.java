package Main.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class RuleUI extends JPanel implements ActionListener {
    private int count;
    public RuleUI(){
        setLayout(null);
        JButton close = new JButton();
        close.addActionListener(this);
        close.setActionCommand("close");
        close.setText("X");
        close.setBackground(Color.RED);
        close.setBounds(968,0,40,40);
        add(close);
        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String order = e.getActionCommand();
        if (order.equals("close")){
            setVisible(false);
        }else if(order.equals("nextp")){
            count++;
        }else if(order.equals("backp")){
            if (count != 0){
                count--;
            }
        }
    }
}


