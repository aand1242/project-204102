package Main.UI;

// import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background extends JPanel {

    private Image img;

    public Background() {
        setLayout(new GridBagLayout()); // เพื่อให้บอร์ดมาวางตรงกลางได้
        try {
            img = ImageIO.read(new File("Main\\source_pic\\main background.png"));
        } catch (Exception e) {
            System.out.println("I cant see the image");
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // คืนค่าขนาดที่ต้องการ เพื่อให้ screen.pack() ปรับขนาดหน้าต่างตามนี้
        return new Dimension(640*2, 360*2);
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
}
