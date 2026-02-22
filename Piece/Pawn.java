package Piece;

import Main.logic.Board;

public class Pawn extends Piece {

    public Pawn(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    // ตรวจสอบช่องที่เดินได้
    @Override
    public boolean canMove(int oldRow, int oldCol, int newRow, int newCol, Board board) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);

        // 1. กำหนดทิศทาง (Direction)
        // หมากขาวเดินไปทางขวา (+1), หมากดำเดินไปทางซ้าย (-1)
        int direction = isWhite ? 1 : -1;

        Piece target = board.getPiece(newRow, newCol);

        // เดินปกติ 1 ช่อง
        if (oldRow == newRow && newCol == oldCol + direction) {
            return target == null;
        }

        // เดิน 2 ช่อง (ครั้งแรก) หรือใช้ไอเทม MOVE+
        if (oldRow == newRow && newCol == oldCol + (2 * direction)) {
            if (!hasMoved || superMove) {
                boolean pathClear = board.getPiece(oldRow, oldCol + direction) == null;
                return target == null && pathClear;
            }
        }

        // การกินเฉียง
        if (rowDiff == 1 && newCol == oldCol + direction) {
            return target != null && target.isWhite() != this.isWhite;
        }

        return false;

        // En Passant (ยาก ไว้ทำทีหลัง)
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public Piece getCopy() {
        Pawn new_p = new Pawn(this.isWhite, this.row, this.col);
        new_p.setHasMoved(this.hasMoved);
        new_p.setSuperMove(this.superMove);
        return new_p;
    }
;
}
