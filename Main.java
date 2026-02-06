import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Main {
    public static final int height = 640;
    public static final int width = 360;
    
    public static void main(String[] args) {
        JFrame screen = new JFrame();
        screen.setSize(height,width);
        // screen.setLayout(new GridBagLayout());
        boardGui boardPanel = new boardGui();
        boardPanel.setPreferredSize(new Dimension(288,288));
        background bg = new background();
        bg.add(boardPanel);
        screen.add(bg);
        screen.pack();
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setResizable(false);
        screen.setVisible(true);
        
    }
}
