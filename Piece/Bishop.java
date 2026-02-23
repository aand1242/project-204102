package Piece;

import Main.logic.Board;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }
    public void setPieceRC(int r,int c){
        super.setPieceRC(r, c);
    }
    public Bishop(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    // ตรวจสอบช่องที่เดินได้
    @Override
    public boolean canMove(int oldRow, int oldCol, int newRow, int newCol, Board board) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);

        // เดินปกติ (แนวทแยง)
        if (rowDiff == colDiff) {
            if (board.isPathClaer(oldRow, oldCol, newRow, newCol)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getScore() {
        return 3;
    }

    @Override
    public Piece getCopy() {
        Bishop new_p = new Bishop(this.isWhite, this.row, this.col);
        new_p.setHasMoved(this.hasMoved);
        new_p.setSuperMove(this.superMove);
        return new_p;
    }
;

}
