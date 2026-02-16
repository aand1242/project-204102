package Piece;

import Main.Board;

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

        // เดิน 2 ช่อง (First Move)
        if (oldRow == newRow && newCol == oldCol + (2 * direction)) {
            if (!hasMoved && target == null) {
                // เช็คช่องตรงกลางว่ามีใครขวางไหม
                if (board.getPiece(oldRow, oldCol + direction) == null) {
                    return true;
                }
            }
            return false;
        }

        // กินเฉียง
        if (Math.abs(newRow - oldRow) == 1 && newCol == oldCol + direction) {
            // แก้ Bug: ต้องเช็ค target != null ก่อน ไม่งั้นจะเกิด NullPointerException
            if (target != null && target.isWhite() != this.isWhite) {
                return true;
            }
        }

        // En Passant (ยาก ไว้ทำทีหลัง)
        
        return false;
    }

    @Override
    public int getScore() {
        return 1;
    }
    
    @Override
    public Piece getCopy() {
        Pawn new_p = new Pawn(this.isWhite, this.row, this.col);
        new_p.setHasMoved(this.hasMoved);
        return new_p;
    };
}
