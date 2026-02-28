package Main.UI;

import Main.logic.GameControl;
import Piece.Piece;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BoardGUI extends JPanel implements ActionListener {

    private ChessButton[][] buttons = new ChessButton[8][8];
    private final ImageIcon[] B_piece = {getScaledIcon("Main\\source_pic\\b_pawn.png"),
        getScaledIcon("Main\\source_pic\\b_rook.png"),
        getScaledIcon("Main\\source_pic\\b_knight.png"),
        getScaledIcon("Main\\source_pic\\b_bishop.png"),
        getScaledIcon("Main\\source_pic\\b_king.png"),
        getScaledIcon("Main\\source_pic\\b_queen.png")
    };
    private final ImageIcon[] W_piece = {getScaledIcon("Main\\source_pic\\w_pawn.png"),
        getScaledIcon("Main\\source_pic\\w_rook.png"),
        getScaledIcon("Main\\source_pic\\w_knight.png"),
        getScaledIcon("Main\\source_pic\\w_bishop.png"),
        getScaledIcon("Main\\source_pic\\w_king.png"),
        getScaledIcon("Main\\source_pic\\w_queen.png")
    };

    private int clickedRow = -1;
    private int clickedCol = -1;

    private GameControl controller;

    public BoardGUI(GameControl controller) {
        this.controller = controller;

        // setDefaultCloseOperation(EXIT_ON_CLOSE);//เราสารมารถ set ด้วยคำสั่งของ JFrame ได้เลยเนื่องด้วย extends Jfarme
        this.setLayout(new GridLayout(8, 8)); //ทำพื้นที่ไว้ใส่ปุ่มซึ่งใช้ girdlayout ทำให้จัดง่ายขึ้น
        // JButton[][] buttons = new JButton[8][8];//สร้างปุ่มไว้
        ActionListener addclick = this;

        Color Dark = new Color(97, 86, 129);
        Color Light = new Color(238, 226, 223);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                buttons[row][col] = new ChessButton();
                buttons[row][col].addActionListener(addclick);
                buttons[row][col].putClientProperty("row", row);
                buttons[row][col].putClientProperty("col", col);
                // 2. ปรับแต่งปุ่ม (เช่น ใส่สีสลับขาว-ดำ)
                if ((row + col) % 2 == 0) {
                    buttons[row][col].setBackground(Dark);
                } else {
                    buttons[row][col].setBackground(Light);
                }

                // 3. สำคัญมาก! ต้อง add ลงใน panel ด้วยเพื่อให้มันแสดงผล
                add(buttons[row][col]);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        clickedRow = (Integer) clickedButton.getClientProperty("row");
        clickedCol = (Integer) clickedButton.getClientProperty("col");

        //-----DEBUG-----
        //if (Main.DEBUG) System.out.println("i got click "+ clickedButton.getClientProperty("row")+" "+clickedButton.getClientProperty("col"));
        //---------------
        controller.processClick(clickedRow, clickedCol);
    }

    public int[] getPosition() {
        return new int[]{clickedRow, clickedCol};
    }

    public void setPieceGUI(Piece[][] pieces) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (pieces[row][col] == null) {
                    buttons[row][col].setIcon(null);
                    continue;
                }
                switch (pieces[row][col].getClass().getSimpleName()) {
                    case "Pawn":
                        if (pieces[row][col].isWhite()) {
                            buttons[row][col].setIcon(W_piece[0]);
                        } else {
                            buttons[row][col].setIcon(B_piece[0]);
                        }
                        break;
                    case "Rook":
                        if (pieces[row][col].isWhite()) {
                            buttons[row][col].setIcon(W_piece[1]);
                        } else {
                            buttons[row][col].setIcon(B_piece[1]);
                        }
                        break;
                    case "Knight":
                        if (pieces[row][col].isWhite()) {
                            buttons[row][col].setIcon(W_piece[2]);
                        } else {
                            buttons[row][col].setIcon(B_piece[2]);
                        }
                        break;
                    case "Bishop":
                        if (pieces[row][col].isWhite()) {
                            buttons[row][col].setIcon(W_piece[3]);
                        } else {
                            buttons[row][col].setIcon(B_piece[3]);
                        }
                        break;
                    case "King":
                        if (pieces[row][col].isWhite()) {
                            buttons[row][col].setIcon(W_piece[4]);
                        } else {
                            buttons[row][col].setIcon(B_piece[4]);
                        }
                        break;
                    case "Queen":
                        if (pieces[row][col].isWhite()) {
                            buttons[row][col].setIcon(W_piece[5]);
                        } else {
                            buttons[row][col].setIcon(B_piece[5]);
                        }
                        break;
                    default:
                        buttons[row][col].setIcon(null);
                        break;
                }

            }
        }

    }

    public void highlightButton(int r, int c, Color baseColor) {

        Color transparentColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 125);

        buttons[r][c].setHighlight(transparentColor);

    }

    public void resetColors() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                buttons[row][col].setHighlight(null); // ล้างสีไฮไลท์
            }
        }
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
}

class ChessButton extends JButton {

    private Color highlightColor = null;

    public ChessButton() {
        super();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(true);
    }

    // สีไฮไลท์
    public void setHighlight(Color color) {
        this.highlightColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g2d);

        // วาดไฮไลท์ทับ
        if (highlightColor != null) {
            g2d.setColor(highlightColor);
            // วาดสี่เหลี่ยมเต็มช่อง (แบบโปร่งแสง)
            int size = getWidth();
            g2d.fillOval((getWidth() / 2) - (size / 2), (getHeight() / 2) - (size / 2), size, size);

            // *ทริคเสริม* ถ้าอยากได้จุดวงกลมตรงกลางแบบ Lichess ให้ใช้บรรทัดนี้แทน fillRect:
            // int size = getWidth() / 4;
            // g.fillOval((getWidth()/2)-(size/2), (getHeight()/2)-(size/2), size, size);
        }
    }
}
