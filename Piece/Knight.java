package Piece;

import Main.logic.Board;

public class Knight extends Piece {

    public Knight(boolean isWhite) {
        super(isWhite);
    }
    public void setPieceRC(int r,int c){
        super.setPieceRC(r, c);
    }
    public Knight(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    // ตรวจสอบช่องที่เดินได้
    @Override
    public boolean canMove(int oldRow, int oldCol, int newRow, int newCol, Board board) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);

        // ถ้ากดใช้ item UNICORN
        if (superMove) {
            return (rowDiff == 1 && colDiff == 3) || (rowDiff == 3 && colDiff == 1);
        } else { // เดินปกติ
            return (rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1);
        }
    }

    @Override
    public int getScore() {
        return 3;
    }

    @Override
    public Piece getCopy() {
        Knight new_p = new Knight(this.isWhite, this.row, this.col);
        new_p.setHasMoved(this.hasMoved);
        new_p.setSuperMove(this.superMove);
        return new_p;
    }
}
