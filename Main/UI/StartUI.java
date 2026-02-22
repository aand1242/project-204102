package Main.UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Main.logic.StartLogic;

public class StartUI extends JPanel implements ActionListener{
    private Image img;
    private StartLogic stg;

    public StartUI(){
        setLayout(null);
        try{
            img  =ImageIO.read(new File("Main\\source_pic\\white_op.jpg"));
        }catch (Exception e) {
            System.out.println("I cant see the image");
        }
        JButton start_b = new JButton();
        start_b.setActionCommand("start");
        start_b.setBounds(264, 200, 112, 48);
        start_b.addActionListener(this);
        start_b.setText("START");
        start_b.setFont(new Font("Arial", Font.BOLD, 20));
        JButton rule_b =  new JButton();
        rule_b.setActionCommand("rule");
        rule_b.setBounds(264, 260, 112, 48);
        rule_b.addActionListener(this);
        setVisible(true);
        add(start_b);
        add(rule_b);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }
        });
    }
        @Override
    public Dimension getPreferredSize() {
        // คืนค่าขนาดที่ต้องการ เพื่อให้ screen.pack() ปรับขนาดหน้าต่างตามนี้
        return new Dimension(640, 360);
    }

    public void setStartLogic(StartLogic stg){
        this.stg = stg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            // วาดภาพให้เต็มพื้นที่ของ Panel ตลอดเวลา
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String click_b = e.getActionCommand();
        stg.operationButton(click_b);
    }

}
