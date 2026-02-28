package Main.UI;

import Main.logic.GameControl;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Revive extends JPanel implements ActionListener {

    private GameControl gct;
    private boolean currentTurn;
    private final String[] pieceNames = {"Pawn", "Rook", "Knight", "Bishop", "Queen", "King"};
    private final ImageIcon[] b_piece = {getScaledIcon("Main\\source_pic\\b_pawn.png"), getScaledIcon("Main\\source_pic\\b_rook.png"), getScaledIcon("Main\\source_pic\\b_knight.png"),
        getScaledIcon("Main\\source_pic\\b_bishop.png"), getScaledIcon("Main\\source_pic\\b_queen.png"), getScaledIcon("Main\\source_pic\\b_king.png")
    };
    private final ImageIcon[] w_piece = {getScaledIcon("Main\\source_pic\\w_pawn.png"), getScaledIcon("Main\\source_pic\\w_rook.png"), getScaledIcon("Main\\source_pic\\w_knight.png"),
        getScaledIcon("Main\\source_pic\\w_bishop.png"), getScaledIcon("Main\\source_pic\\w_king.png"), getScaledIcon("Main\\source_pic\\w_queen.png")
    };

    public void setGamecontrol(GameControl gct) {
        this.gct = gct;
    }

    public Revive(boolean isWhite) {
        currentTurn = isWhite;
        this.setLayout(new GridLayout(2, 3));
        JButton[][] button = new JButton[2][3];
        int count = 0;
        ActionListener addclick = this;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                button[i][j] = new JButton();
                button[i][j].addActionListener(addclick);;
                if (isWhite) {
                    button[i][j].setIcon(w_piece[count]);
                } else {
                    button[i][j].setIcon(b_piece[count]);
                }
                button[i][j].setActionCommand(pieceNames[count]);
                count++;
                this.add(button[i][j]);
            }
        }
        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String thatpiece = e.getActionCommand();
        gct.setReviePiece(thatpiece, currentTurn);
        setVisible(false);
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
