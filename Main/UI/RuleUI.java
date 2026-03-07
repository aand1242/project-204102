package Main.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class RuleUI extends JPanel implements ActionListener {
    
    private JLabel cards ;
    private JLabel text = new JLabel();
    private JLabel frame = new JLabel(getScaledIcon("Main\\source_pic\\TextFrame.png"));
    private int count;
    private String [] wold = {"bank", "pon", "mint", "pern", "gam"};
    private ImageIcon [] card_BK = {getScaledIcon("Main\\source_pic\\Recall_card.png"), 
    getScaledIcon("Main\\source_pic\\Sniper_card.png"),
    getScaledIcon("Main\\source_pic\\Unicorn_card.png"),
    getScaledIcon("Main\\source_pic\\Moveplus_card.png"),
    getScaledIcon("Main\\source_pic\\Shield_card.png"),
    };

    public RuleUI(){
        setLayout(null);
        this.setBackground(Color.lightGray);

        JButton close = new JButton();
        close.addActionListener(this);
        close.setActionCommand("close");
        close.setText("X");
        close.setMargin(new Insets(0, 0, 0, 0));
        close.setFont(new Font("Jacquard 24", Font.PLAIN, 24));
        close.setBackground(Color.RED);
        close.setBounds(968,0,40,40);
        add(close);
        setVisible(false);

        JButton Next = new JButton();
        Next.addActionListener(this);
        Next.setActionCommand("nextp");
        Next.setMargin(new Insets(0, 0, 0, 0));
        Next.setFont(new Font("Jacquard 24", Font.PLAIN, 24));
        Next.setText(">");
        Next.setBackground(Color.blue);
        Next.setBounds(972, 267, 36, 36);
        add(Next);

        
        JButton Back = new JButton();
        Back.addActionListener(this);
        Back.setActionCommand("backp");
        Back.setMargin(new Insets(0, 0, 0, 0));
        Back.setFont(new Font("Jacquard 24", Font.PLAIN, 24));
        Back.setText("<");
        Back.setBackground(Color.blue);
        Back.setBounds(0, 267, 36, 36);
        add(Back);

        cards = new JLabel(card_BK[count]);
        cards.setBounds(140, 93, 128*2, 192*2);
        add(cards);

        // text.setBackground(null);
        // text.setOpaque(false);
        // text.setBounds(250, 93, 244*2, 176*2);
        // text.setEnabled(false);
        // text.setContentAreaFilled(false);
        // text.setBorderPainted(false);
        // add(text);

        text.setBackground(null);
        text.setOpaque(false);
        // text.setActionCommand("start");
        text.setBounds(280 * 2, 110 * 2, 112 * 2, 48 * 2);
        text.setText(wold[count]);
        text.setFont(new Font("Jacquard 24", Font.PLAIN, 16 * 2));
        add(text);

        frame.setOpaque(false);
        frame.setBounds(430, 93, 244*2, 176*2);
        add(frame);


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }
        });

        


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

    @Override
    public void actionPerformed(ActionEvent e) {
        String order = e.getActionCommand();
        if (order.equals("close")){
            setVisible(false);
        }else if(order.equals("nextp")){
            if (count < card_BK.length - 1 ){
                count++;
                cards.setIcon(card_BK[count]);
                text.setText(wold[count]);
            }
        }else if(order.equals("backp")){
            if (count != 0){
                count--;
                cards.setIcon(card_BK[count]);
                text.setText(wold[count]);
            }
        }
    }
}


