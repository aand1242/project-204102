package Piece;

import Main.Board;

public class Rook extends Piece {

    public Rook(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    // ตรวจสอบช่องที่เดินได้
    @Override
    public boolean canMove(int oldRow, int oldCol, int newRow, int newCol, Board board) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);
        
        // เดินปกติ (แนวบวก)
        if (rowDiff == 0 || colDiff == 0) {
            if (board.isPathClaer(oldRow, oldCol, newRow, newCol))
                return true;
        }

        return false;
    }

    @Override
    public int getScore() { return 5; }

}
