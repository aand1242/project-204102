package Main;

import Piece.Bishop;
import Piece.King;
import Piece.Knight;
import Piece.Pawn;
import Piece.Piece;
import Piece.Queen;
import Piece.Rook;

import java.awt.*;


public class GameControl {
    private String promo ;
    private Tranfrom tranfrom;
 
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
    public void setTranfrom(Tranfrom x){
        tranfrom = x;
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
                
                if (selectedPiece.canMove(selectedRow, selectedCol, r, c, board)&& isMoveSafe(selectedRow, selectedCol, r, c)) {
                    // กรณีเข้าป้อม !!
                    if (selectedPiece instanceof King && Math.abs(selectedRow - r) == 2){
                        if (r < 4 && board.isPathClaer(selectedRow, selectedCol, r-1, c)){
                            board.movePiece(selectedRow, selectedCol, r, c);
                            board.movePiece(0, selectedCol, r+1, c);
                        }else if (r > 4 ) {
                            board.movePiece(selectedRow, selectedCol, r, c);
                            board.movePiece(7, selectedCol, r-1, c);
                        }

                    }else{
                        board.movePiece(selectedRow, selectedCol, r, c);
                    }
                    
                    boolean isWhitePromotion = selectedPiece.isWhite() && selectedCol == 7;
                    boolean isBlackPromotion = !selectedPiece.isWhite() && selectedCol == 0;

                    if (selectedPiece instanceof Pawn && (isWhitePromotion || isBlackPromotion)) {
                        
                        tranfrom.preparePromotion(selectedRow, selectedCol, currentTurn, this);
                        tranfrom.setVisible(true);
                        return; 
                    } else {
                        // เดินปกติเสร็จแล้วเรียก endTurn()
                        endTurn();
                    }

                    //-----DEBUG-----
                        System.out.printf("Move %s from (%d, %d) to (%d, %d)\n", selectedPiece.getClass().getSimpleName(), selectedRow, selectedCol, r, c);
                    //---------------
                    
                }
            }
            

        }

    }

    public void tranfrom(String s){
        promo = s;
    }

    private void highlightPossibleMoves(int r, int c) {
        Piece p = board.getPiece(r, c);
        
        // วนลูปเช็คทุกช่องบนกระดาน 8x8
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                
                // ถามหมากตัวนั้นว่า "เดินไปช่อง (i, j) ได้ไหม?"
                // (ต้องมั่นใจว่า canMove เช็คเรื่อง isPathClear และ ห้ามกินพวกเดียวกันแล้ว)
                if (p.canMove(r, c, i, j, board) ) {
                    
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

    public boolean isMoveSafe(int startRow, int startCol, int targetRow, int targetCol) {
        
        // 1. สร้างกระดานจำลอง
        Board memBoard = board.getCopy();

        // 2. จำลองการเดิน (Simulate Move)
        // ย้ายจากจุดเดิม ไปจุดใหม่
        Piece movingPiece = memBoard.getPiece(startRow, startCol);
        memBoard.setPiece(targetRow, targetCol, movingPiece); // วางที่ใหม่
        memBoard.setPiece(startRow, startCol, null);          // ลบที่เก่า
        
        // (ถ้า Piece มีตัวแปร row/col ข้างใน ต้องอัปเดตตรงนี้ด้วย ไม่งั้น p.getRow() จะได้ค่าเดิม)
        // if(movingPiece != null) { movingPiece.setRow(targetRow); movingPiece.setCol(targetCol); }

        // ---------------------------------------------
        // 3. เริ่มเช็คว่า King ปลอดภัยไหม
        // ---------------------------------------------

        // หา King ของฝ่ายเรา (ดูจาก turn หรือสีของตัวที่เดิน)
        // ใช้ movingPiece.isWhite ในการเช็คสี เพราะบางที currentTurn อาจสลับไปแล้วถ้าเรียกผิดจังหวะ
        boolean myColor = (movingPiece != null) ? movingPiece.isWhite : currentTurn; 
        
        int kingRow = -1, kingCol = -1;

        // วนลูปหา King ในกระดานจำลอง
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = memBoard.getPiece(r, c);
                if (p != null && p.getClass() == King.class && p.isWhite == myColor) {
                    kingRow = r;
                    kingCol = c;
                    break;
                }
            }
        }
        
        // ถ้าหา King ไม่เจอ (ไม่ควรเกิดขึ้น)
        if (kingRow == -1) return false;

        // เช็คศัตรูทุกตัวในกระดานจำลอง
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece enemy = memBoard.getPiece(r, c);
                // ถ้าเป็นศัตรู
                if (enemy != null && enemy.isWhite != myColor) {
                    // ถามศัตรูว่า "ในกระดานจำลองนี้ นายกิน King ฉันถึงไหม?"
                    if (enemy.canMove(r, c, kingRow, kingCol, memBoard)) {
                        return false; // ไม่ปลอดภัย (โดนรุก)
                    }
                }
            }
        }

        return true; // ปลอดภัย
    }
    public void executePromotion(String pieceType, int r, int c, boolean isWhite) {
        Piece newPiece;
        switch (pieceType) {
            case "Rook":   newPiece = new Rook(isWhite, r, c); break;
            case "Knight": newPiece = new Knight(isWhite, r, c); break;
            case "Bishop": newPiece = new Bishop(isWhite, r, c); break;
            default:       newPiece = new Queen(isWhite, r, c); break;
        }

        board.setPiece(r, c, newPiece);
        
        // ใช้ endTurn() ที่เราคุยกันก่อนหน้ามาใส่ตรงนี้ได้เลย
        endTurn(); 
    }

    private void endTurn() {
        // ย้าย logic สลับเทิร์นและอัปเดต GUI มาไว้ที่นี่
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
        // ... อัปเดตคะแนน ...
    }
}