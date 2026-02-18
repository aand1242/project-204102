package Main;
// import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Tranfrom extends JPanel implements ActionListener{
    private int targetRow, targetCol;
    private boolean isWhite;
    private GameControl gamelogic;
    private final String[] pieceNames = {"Rook", "Knight", "Bishop", "Queen"};
    private static final ImageIcon[] piece = {new ImageIcon("Main\\source_pic\\b_rook.png"),new ImageIcon("Main\\source_pic\\b_knight.png") 
           ,new ImageIcon("Main\\source_pic\\b_bishop.png"),new ImageIcon("Main\\source_pic\\b_queen.png")
        };
    public Tranfrom(){
        this.setLayout(new GridLayout(2,2));
        JButton[][] button = new JButton[2][2];
        int count = 0;
        ActionListener addclick = this;
        for (int i = 0 ;i<2 ; i++){
            for(int j = 0 ; j < 2; j++ ){
                button[i][j] = new JButton();
                button[i][j].addActionListener(addclick);;
                button[i][j].setIcon(piece[count]);
                button[i][j].setActionCommand(pieceNames[count]);
                count++;
                this.add(button[i][j]);
            }
        }
        this.setVisible(false);
    }
    public void preparePromotion(int r, int c, boolean white, GameControl gc) {
        this.targetRow = r;
        this.targetCol = c;
        this.isWhite = white;
        this.gamelogic = gc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedPiece = e.getActionCommand();
        gamelogic.executePromotion(selectedPiece, targetRow, targetCol, isWhite);
        this.setVisible(false);
    }
    
}
