package Piece;

import Main.Board;

public abstract class Piece {
    public boolean isWhite;
    public int row;
    public int col;
    public boolean hasMoved = false; // เช็กว่าเคยเดินหรือยัง สำหรับการเดินสองช่องในตาแรกของ pawn และการเข้าป้อมของ rook กับ king

    public Piece(boolean isWhite, int row, int col) {
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved() {
        this.hasMoved = true;
    }

    public abstract boolean canMove(int oldRow, int oldCol, int newRow, int newCol, Board board);

    public abstract int getScore();
}
