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

        // --- กรณี A: เดินปกติ 1 ช่อง (Forward 1) ---
        // เงื่อนไข: แถวเดิม, คอลัมน์ขยับไป 1 ตามทิศทาง, ปลายทางต้องว่าง
        if (oldRow == newRow && newCol == oldCol + direction) {
            return target == null;
        }

        // --- กรณี B: เดิน 2 ช่อง (First Move 2) ---
        // เงื่อนไข: แถวเดิม, คอลัมน์ขยับ 2, ยังไม่เคยเดิน, ปลายทางว่าง, **ระหว่างทางว่าง**
        if (oldRow == newRow && newCol == oldCol + (2 * direction)) {
            if (!hasMoved && target == null) {
                // เช็คช่องตรงกลางว่ามีใครขวางไหม
                if (board.getPiece(oldRow, oldCol + direction) == null) {
                    return true;
                }
            }
            return false;
        }

        // --- กรณี C: กินหมาก (Capture) ---
        // เงื่อนไข: แถวขยับ 1 (ขึ้นหรือลง), คอลัมน์ขยับ 1 ตามทิศทาง, **ต้องมีศัตรู**
        if (Math.abs(newRow - oldRow) == 1 && newCol == oldCol + direction) {
            // แก้ Bug: ต้องเช็ค target != null ก่อน ไม่งั้นจะเกิด NullPointerException
            if (target != null && target.isWhite() != this.isWhite) {
                return true;
            }
        }

        // --- En Passant (ยาก ไว้ทำทีหลัง) ---
        // ต้องให้ Board จำว่าตาที่แล้วฝ่ายตรงข้ามเดิน Pawn 2 ช่องมาเทียบข้างเราหรือไม่
        
        return false;
    }
        /*
        // เดินครั้งแรก
        if (!hasMoved && rowDiff == 0 && (colDiff == 1 || colDiff == 2) && board.getPiece(newRow, newCol) == null) {
            // หมากขาว
            if (isWhite && newCol > oldCol) {
                return true;
            }
            
            // หมากดำ
            if (!isWhite && newCol < oldCol) {
                return true;
            }

            return false;
        }
        if (!hasMoved && rowDiff == 0 && (colDiff == 1 || colDiff == 2) && ((isWhite && newCol > oldCol) || (!isWhite && newCol < oldCol)) && board.getPiece(newRow, newCol) == null) {
            super.setHasMoved();
            if (board.isPathClaer(oldRow, oldCol, newRow, newCol))
                return true;
        }

        // เดินปกติ
        if (rowDiff == 0 && colDiff == 1 && ((isWhite && newCol > oldCol) || (!isWhite && newCol < oldCol)) && board.getPiece(newRow, newCol) == null) {
            return true;
        }

        // กินเฉียง
        if (rowDiff == 1 && colDiff == 1 && ((isWhite && newCol > oldCol) || (!isWhite && newCol < oldCol)) && (board.getPiece(newRow, newCol)).isWhite() != isWhite) {
            return true;
        }

        //En Passant WTF!!!!!!!!!!!


        return false;
        */

    @Override
    public int getScore() { return 1; }
    
}
