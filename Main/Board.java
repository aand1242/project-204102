package Main;

import Piece.Bishop;
import Piece.King;
import Piece.Knight;
import Piece.Pawn;
import Piece.Piece;
import Piece.Queen;
import Piece.Rook;

public class Board {

    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        setupBoard();   
    }

    // วางหมากเริ่มต้น
    private void setupBoard() {
        // วางหมากขาว
        board[0][0] = new Rook(true, 0, 0);
        board[1][0] = new Knight(true, 1, 0);
        board[2][0] = new Bishop(true, 2, 0);
        board[3][0] = new Queen(true, 3, 0);
        board[4][0] = new King(true, 4, 0);
        board[5][0] = new Bishop(true, 5, 0);
        board[6][0] = new Knight(true, 6, 0);
        board[7][0] = new Rook(true, 7, 0);
        for (int r = 0; r < 8;r++) board[r][1] = new Pawn(true, r, 1);

        // วางหมากดำ
        board[0][7] = new Rook(false, 0, 7);
        board[1][7] = new Knight(false, 1, 7);
        board[2][7] = new Bishop(false, 2, 7);
        board[3][7] = new Queen(false, 3, 7);
        board[4][7] = new King(false, 4, 7);
        board[5][7] = new Bishop(false, 5, 7);
        board[6][7] = new Knight(false, 6, 7);
        board[7][7] = new Rook(false, 7, 7);
        for (int r = 0; r < 8; r++) board[r][6] = new Pawn(false, r, 6);
    }

    // เลือกหมาก
    public Piece getPiece(int r, int c) {
        return board[r][c];
    }

    // เดินหมาก
    public void movePiece(int startRow, int startCol, int endRow, int endCol) {
        board[endRow][endCol] = board[startRow][startCol];
        board[endRow][endCol].setHasMoved();
        board[startRow][startCol] = null;
    }

    public Piece[][] getBoard(){
        return board;
    }

    public Board getCopy() {
        Board newBoard = new Board();

        // ล้างกระดาน
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                newBoard.setPiece(r, c, null);
            }
        }

        // วางหมากโดยลอกจากกระดานเดิม
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = this.getPiece(r, c);
                if (p != null)
                    newBoard.setPiece(r, c, p);
            }
        }

        return newBoard;
    }

    // เช็กว่ามีหมากตัวอื่นขวางทางที่จะเดินหรือเปล่า
    public boolean isPathClaer(int oldRow, int oldCol, int newRow, int newCol) {
        // หาทิศทาง
        int dr = Integer.compare(newRow, oldRow);
        int dc = Integer.compare(newCol, oldCol);

        int currRow = oldRow + dr;
        int currCol = oldCol + dc;

        while (currRow != newRow || currCol != newCol) {
            // เจอหมากขวางทาง เดินไม่ได้
            if (board[currRow][currCol] != null) {
                return false; 
            }

            currRow += dr;
            currCol += dc;
        }

        // ไม่มีหมากขวางทาง
        return true;
    }
    public void setPiece(int r, int c, Piece p) {
        board[r][c] = p;
    }
}
