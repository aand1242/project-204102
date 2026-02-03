import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        JFrame screen = new JFrame();
        screen.setSize(640,360);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            File backgroundpath = new File("C:\\Users\\natha\\Documents\\project\\source_pic\\bordtest.jpg");
            BufferedImage background =  ImageIO.read(backgroundpath);
            ImageIcon icon = new ImageIcon(background);
            JLabel label = new JLabel(icon);
            screen.add(label);
        }catch(Exception e){
            System.out.println("i cant see");
        }
        
        
        screen.setVisible(true);
    }
}
