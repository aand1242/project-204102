package Piece;

import Main.Board;

public class King extends Piece {
    
    public King(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    // ตรวจสอบช่องที่เดินได้
    @Override
    public boolean canMove(int oldRow, int oldCol, int newRow, int newCol, Board board) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);
        
        // เดินปกติ
        if ((rowDiff == 1 && colDiff == 1) || (rowDiff == 0 && colDiff == 1) || (rowDiff == 1 && colDiff == 0)) {
            return true;
        }

        //เข้าป้อม ค่อยทำ งง จะตาย

        return false;
    }

    @Override
    public int getScore() { return 10000; }

}
