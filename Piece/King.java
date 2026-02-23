package Piece;

import Main.logic.Board;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
    }
    public void setPieceRC(int r,int c){
        super.setPieceRC(r, c);
    }
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
        if (isWhite) {
            if ((this.hasMoved() == false && board.getPiece(0, 0) instanceof Rook && board.getPiece(0, 0).hasMoved() == false && rowDiff == 2 && colDiff == 0) || (this.hasMoved() == false && board.getPiece(7, 0).getClass().getSimpleName().equals("Rook") && board.getPiece(7, 0).hasMoved() == false && rowDiff == 2 && colDiff == 0)) {

                return true;
            }
        } else {
            if ((this.hasMoved() == false && board.getPiece(0, 7) instanceof Rook && board.getPiece(0, 7).hasMoved() == false && rowDiff == 2 && colDiff == 0) || (this.hasMoved() == false && board.getPiece(7, 7).getClass().getSimpleName().equals("Rook") && board.getPiece(7, 7).hasMoved() == false && rowDiff == 2 && colDiff == 0)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getScore() {
        return 10000;
    }

    @Override
    public Piece getCopy() {
        King new_p = new King(this.isWhite, this.row, this.col);
        new_p.setHasMoved(this.hasMoved);
        new_p.setSuperMove(this.superMove);
        return new_p;
    }
;

}
