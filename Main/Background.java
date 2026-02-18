package Main;

// import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background extends JPanel {
    private Image img;

    public Background() {
        setLayout(new GridBagLayout()); // เพื่อให้บอร์ดมาวางตรงกลางได้
        try {
            img = ImageIO.read(new File("Main\\source_pic\\BG.png"));
        } catch (Exception e) {
            System.out.println("I cant see the image");
        }
    }


    @Override
    public Dimension getPreferredSize() {
        // คืนค่าขนาดที่ต้องการ เพื่อให้ screen.pack() ปรับขนาดหน้าต่างตามนี้
        return new Dimension(640, 360);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            // วาดภาพให้เต็มพื้นที่ของ Panel ตลอดเวลา
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
}