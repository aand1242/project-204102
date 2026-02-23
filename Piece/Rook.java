package Piece;

import Main.logic.Board;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(isWhite);
    }
    public void setPieceRC(int r,int c){
        super.setPieceRC(r, c);
    }
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
            if (board.isPathClaer(oldRow, oldCol, newRow, newCol)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getScore() {
        return 5;
    }

    @Override
    public Piece getCopy() {
        Rook new_p = new Rook(this.isWhite, this.row, this.col);
        new_p.setHasMoved(this.hasMoved);
        new_p.setSuperMove(this.superMove);
        return new_p;
    }
;

}
