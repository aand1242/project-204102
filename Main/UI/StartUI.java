package Main.UI;

import Main.logic.StartLogic;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StartUI extends JPanel implements ActionListener {

    private Image img;
    private StartLogic stg;

    public StartUI() {
        setLayout(null);
        try {
            img = ImageIO.read(new File("Main\\source_pic\\main background.png"));
        } catch (Exception e) {
            System.out.println("I cant see the image");
        }
        JButton start_b = new JButton();
        start_b.setActionCommand("start");
        start_b.setBounds(264 * 2, 200 * 2, 112 * 2, 48 * 2);
        start_b.addActionListener(this);
        start_b.setText("START");
        start_b.setFont(new Font("Arial", Font.BOLD, 20 * 2));

        JButton rule_b = new JButton();
        rule_b.setActionCommand("rule");
        rule_b.setBounds(264 * 2, 260 * 2, 112 * 2, 48 * 2);
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

    public void setStartLogic(StartLogic stg) {
        this.stg = stg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        super.paintComponent(g2d);
        if (img != null) {
            // วาดภาพให้เต็มพื้นที่ของ Panel ตลอดเวลา
            g2d.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String click_b = e.getActionCommand();
        stg.operationButton(click_b);
    }

}
