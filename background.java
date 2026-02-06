import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.GridBagLayout;

public class background extends JPanel {
    private Image img;

    public background() {
        setLayout(new GridBagLayout()); // เพื่อให้บอร์ดมาวางตรงกลางได้
        try {
            img = ImageIO.read(new File("source_pic/BG.png"));
        } catch (Exception e) {
            System.out.println("i cant see the image");
        }
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