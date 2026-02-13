package Main;

import Piece.Piece;
import java.awt.*;

public class GameControl {

    private Board board;
    private BoardGUI boardGUI;
    
    private Player black;
    private Player white;

    private ItemslotGUI leftGui;
    private ItemslotGUI rightGui;

    private int selectedRow;
    private int selectedCol;

    private boolean currentTurn = true; // true = White, false = Black
    private boolean isPieceSelected = false;

    public GameControl(Board board) {
        this.board = board;
    }

    public void setBordGUI(BoardGUI BoardGUI){
        boardGUI = BoardGUI;
    }
    public void setPlayer(Player w, Player b){
        white = w;
        black = b;
    }
    public void setItemscore(ItemslotGUI k,ItemslotGUI l){
        leftGui = k;
        rightGui = l;
    }

    public void processClick(int r, int c) {

        // ถ้าหมากยังไม่ถูกเลือก
        if (!isPieceSelected) {
            Piece p = board.getPiece(r, c);
            if (p != null && p.isWhite == currentTurn) {
                selectedRow = r;
                selectedCol = c;
                isPieceSelected = true;
                
                highlightPossibleMoves(selectedRow, selectedCol);

                //-----DEBUG-----
                    if (Main.DEBUG) System.out.println("Processing Click at: " + r + "," + c);
                    if (p != null) System.out.println("Found piece: " + p.getClass().getSimpleName());
                    else System.out.println("Empty square");
                //---------------

            }
        }
        // หมากถูกเลือกแล้ว
        else {
            Piece new_p = board.getPiece(r, c);
            // เลือกหมากตัวใหม่
            if (new_p != null && new_p.isWhite == currentTurn) {
                selectedRow = r;
                selectedCol = c;

                boardGUI.resetColors();
                highlightPossibleMoves(selectedRow, selectedCol);

                //-----DEBUG-----
                    if (Main.DEBUG) System.out.println("Processing Click at: " + r + "," + c);
                    if (new_p != null) System.out.println("Found piece: " + new_p.getClass().getSimpleName());
                    else System.out.println("Empty square");
                //---------------
            }
            else {
                // เดิน หรือ กินฝ่ายตรงข้าม
                Piece selectedPiece = board.getPiece(selectedRow, selectedCol);
                
                if (selectedPiece.canMove(selectedRow, selectedCol, r, c, board)) {

                    board.movePiece(selectedRow, selectedCol, r, c);

                    //-----DEBUG-----
                        System.out.printf("Move %s from (%d, %d) to (%d %d)\n", selectedPiece.getClass().getSimpleName(), selectedRow, selectedCol, r, c);
                    //---------------
                    if (currentTurn == true) {
                        white.addScore(1);
                        leftGui.changeScore(white.getScore());
                    }else{
                        black.addScore(1);
                        rightGui.changeScore(black.getScore());
                    }
                    
                    isPieceSelected = false;
                    currentTurn = !currentTurn;

                    boardGUI.resetColors();
                    boardGUI.setPiece(board.getBoard());
                }
            }
            

        }

    }

    private void highlightPossibleMoves(int r, int c) {
        Piece p = board.getPiece(r, c);
        
        // วนลูปเช็คทุกช่องบนกระดาน 8x8
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                
                // ถามหมากตัวนั้นว่า "เดินไปช่อง (i, j) ได้ไหม?"
                // (ต้องมั่นใจว่า canMove เช็คเรื่อง isPathClear และ ห้ามกินพวกเดียวกันแล้ว)
                if (p.canMove(r, c, i, j, board)) {
                    
                    Piece target = board.getPiece(i, j);
                    
                    // ถ้าช่องปลายทางว่าง -> สีเขียว
                    if (target == null) {
                        boardGUI.highlightButton(i, j, new Color(109, 160, 225));
                    } 
                    // ถ้าช่องปลายทางมีตัว (และ canMove ผ่าน แสดงว่าเป็นศัตรู) -> สีแดง
                    else if (target.isWhite != p.isWhite) {
                        boardGUI.highlightButton(i, j, new Color(91, 97, 178));
                    }
                }
            }
        }
        // ไฮไลท์ตัวที่ถูกเลือกด้วย (สีเหลือง) จะได้รู้ว่าเลือกตัวไหนอยู่
        //boardGUI.highlightButton(r, c, new Color(254, 225, 175));
    }
}
