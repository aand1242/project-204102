import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class boardGui extends JFrame implements ActionListener{
    // private
    
    public boardGui(){
        // setDefaultCloseOperation(EXIT_ON_CLOSE);//เราสารมารถ set ด้วยคำสั่งของ JFrame ได้เลยเนื่องด้วย extends Jfarme
        JPanel boardpanel = new JPanel(new GridLayout(8, 8)); //ทำพื้นที่ไว้ใส่ปุ่มซึ่งใช้ girdlayout ทำให้จัดง่ายขึ้น
        JButton[][] buttons = new JButton[8][8];//สร้างปุ่มไว้
        ActionListener addclick = this;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
            buttons[row][col] = new JButton();
            buttons[row][col].addActionListener(addclick);
            buttons[row][col].putClientProperty("row", row);
            buttons[row][col].putClientProperty("col", col);
            // 2. ปรับแต่งปุ่ม (เช่น ใส่สีสลับขาว-ดำ)
            if ((row + col) % 2 == 0) {
                buttons[row][col].setBackground(Color.WHITE);
            } else {
                buttons[row][col].setBackground(Color.GRAY);
            }
        
            // 3. สำคัญมาก! ต้อง add ลงใน panel ด้วยเพื่อให้มันแสดงผล
            boardpanel.add(buttons[row][col]);
            }
        }
    add(boardpanel);
    setSize(288,288);
    setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton)e.getSource();

        System.out.println("i got click "+ clickedButton.getClientProperty("row")+" "+clickedButton.getClientProperty("col"));
       
    }   
    public int[] getPosition(int row,int col){
        return new int[]{row,col};
    }
}
