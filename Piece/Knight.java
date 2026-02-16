package Piece;

import Main.Board;

public class Knight extends Piece {

    public Knight(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    // ตรวจสอบช่องที่เดินได้
    @Override
    public boolean canMove(int oldRow, int oldCol, int newRow, int newCol, Board board) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);
        
        // เดินเป็นตัว L
        if ((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1)) {
            return true;
        }

        return false;
    }

    @Override
    public int getScore() { return 3; }

    @Override
    public Piece getCopy() {
        Knight new_p = new Knight(this.isWhite, this.row, this.col);
        new_p.setHasMoved(this.hasMoved);
        return new_p;
    };
}
