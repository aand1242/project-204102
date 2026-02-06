import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Dimension;

public class background extends JPanel {
    private Image img;

    public background() {
        setLayout(new GridBagLayout());
        try {
            img = ImageIO.read(new File("source_pic/BG.png"));
        } catch (Exception e) {
            System.out.println("i cant see the image");
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
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
}