package Main.UI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Revive extends JPanel implements ActionListener{
    private boolean currentTurn
    private final String[] pieceNames = {"Pawn","Rook", "Knight", "Bishop", "Queen","King"};
    private static final ImageIcon[] b_piece = {new ImageIcon("Main\\source_pic\\b_pawn.png"),new ImageIcon("Main\\source_pic\\b_rook.png"), new ImageIcon("Main\\source_pic\\b_knight.png"),
        new ImageIcon("Main\\source_pic\\b_bishop.png"), new ImageIcon("Main\\source_pic\\b_queen.png"),new ImageIcon("Main\\source_pic\\b_king.png")
    };
    private static final ImageIcon[] w_piece = {new ImageIcon("Main\\source_pic\\w_pawn.png"),new ImageIcon("Main\\source_pic\\w_rook.png"),new ImageIcon("Main\\source_pic\\w_knight.png") 
           ,new ImageIcon("Main\\source_pic\\w_bishop.png"),new ImageIcon("Main\\source_pic\\w_king.png"),new ImageIcon("Main\\source_pic\\w_queen.png")
        };
    public Revive(boolean isWhite){
        this.setLayout(new GridLayout(2, 3));
        JButton[][] button = new JButton[2][3];
        int count = 0;
        ActionListener addclick = this;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                button[i][j] = new JButton();
                button[i][j].addActionListener(addclick);;
                if (isWhite){
                    button[i][j].setIcon(w_piece[count]);
                }else{
                    button[i][j].setIcon(b_piece[count]);
                }
                button[i][j].setActionCommand(pieceNames[count]);
                count++;
                this.add(button[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String thatpiece = e.getActionCommand();
        
    }
}
