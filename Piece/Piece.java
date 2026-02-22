package Piece;

import Main.logic.Board;

public abstract class Piece {

    protected boolean isWhite;
    protected int row;
    protected int col;
    protected boolean hasMoved = false; // เช็กว่าเคยเดินหรือยัง สำหรับการเดินสองช่องในตาแรกของ pawn และการเข้าป้อมของ rook กับ king
    protected boolean superMove = false; // ใช้ตรวจสอบสถานะการใช้ไอเทม Unicorn (Knight), Move+ (Pawn)
    protected boolean hasShield = false;

    protected Piece(boolean isWhite, int row, int col) {
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

    public boolean hasShield() {
        return hasShield;
    }

    public void setHasMoved(boolean status) {
        this.hasMoved = status;
    }

    public void setSuperMove(boolean status) {
        this.superMove = status;
    }

    public void setShield(boolean status) {
        this.hasShield = status;
    }

    public abstract boolean canMove(int oldRow, int oldCol, int newRow, int newCol, Board board);

    public abstract int getScore();

    public abstract Piece getCopy();
}
