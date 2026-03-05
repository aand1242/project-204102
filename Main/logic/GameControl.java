package Main.logic;

import Main.Main;
import Main.UI.BoardGUI;
import Main.UI.ItemslotGUI;
import Main.UI.Revive;
import Main.UI.Tranfrom;
import Piece.Bishop;
import Piece.King;
import Piece.Knight;
import Piece.Pawn;
import Piece.Piece;
import Piece.Queen;
import Piece.Rook;
import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameControl {
    private JPanel WLscreen;

    private Tranfrom tranfrom;

    private Board board;
    private BoardGUI boardGUI;

    private Player black;
    private Player white;

    private ItemslotGUI leftGui;
    private ItemslotGUI rightGui;

    private Revive whiteRevive;
    private Revive blackRevive;

    private int selectedRow;
    private int selectedCol;

    private boolean currentTurn = true; // true = White, false = Black
    private boolean isPieceSelected = false;

    private boolean isUseItem = false;

    private boolean isSniperActive = false;
    private boolean isReviveActive = false;
    private Piece revivePiece;
    private int sniperTargetRow = -1;
    private int sniperTargetCol = -1;

    public GameControl(Board board) {
        this.board = board;
    }

    public void setBordGUI(BoardGUI BoardGUI) {
        boardGUI = BoardGUI;
    }

    public void setPlayer(Player w, Player b) {
        white = w;
        black = b;
    }

    public void setItemscore(ItemslotGUI k, ItemslotGUI l) {
        leftGui = k;
        rightGui = l;
    }

    public void setTranfrom(Tranfrom x) {
        tranfrom = x;
    }

    public void setReviveUI(Revive w, Revive b) {
        whiteRevive = w;
        blackRevive = b;
    }
    public void setWLscreen(JPanel screen){
        WLscreen = screen;
        WLscreen.setLayout(null);
    }

    public void processClick(int r, int c) {
        if (isReviveActive) {
            board.setPiece(r, c, revivePiece);
            boardGUI.setPieceGUI(board.getBoard());
            isReviveActive = false;
            if (currentTurn) {
                white.addScore(-10);
                leftGui.changeScore(white.getScore());
            } else {
                black.addScore(-10);
                rightGui.changeScore(black.getScore());
            }
            endTurn(null);
            return;
        }
        // ถ้า Rook มีการใช้ item SNIPER

        if (isSniperActive) {
            if (r == sniperTargetRow && c == sniperTargetCol) {
                Piece targetPiece = board.getPiece(r, c);
                board.setPiece(r, c, null); // ลบหมากศัตรูออก

                System.out.println("Sniper Shot Fired!");
                isSniperActive = false; // ปิดสถานะ
                endTurn(targetPiece);
                return;
            } else {
                // ถ้าคลิกที่อื่น ให้ยกเลิกการใช้ SNIPER
                isSniperActive = false;
                boardGUI.resetColors();
            }
        }

        // ถ้าหมากยังไม่ถูกเลือก
        if (!isPieceSelected) {
            Piece p = board.getPiece(r, c);
            if (p != null && p.isWhite() == currentTurn) {
                selectedRow = r;
                selectedCol = c;
                isPieceSelected = true;

                highlightPossibleMoves(selectedRow, selectedCol);

                //-----DEBUG-----
                if (Main.DEBUG) {
                    System.out.println("Processing Click at: " + r + "," + c);
                    if (p != null) {
                        System.out.println("Found piece: " + p.getClass().getSimpleName());
                    } else {
                        System.out.println("Empty square");
                    }
                }
                //---------------

            }
        } else {
            // หมากถูกเลือกแล้ว
            Piece targetPiece = board.getPiece(r, c);

            // เลือกหมากตัวใหม่
            if (targetPiece != null && targetPiece.isWhite() == currentTurn) {
                selectedRow = r;
                selectedCol = c;

                boardGUI.resetColors();
                highlightPossibleMoves(selectedRow, selectedCol);

                //-----DEBUG-----
                if (Main.DEBUG) {
                    System.out.println("Processing Click at: " + r + "," + c);
                }
                if (targetPiece != null) {
                    System.out.println("Found piece: " + targetPiece.getClass().getSimpleName());
                } else {
                    System.out.println("Empty square");
                }
                //---------------

            } else {
                // เดิน หรือ กินฝ่ายตรงข้าม
                Piece selectedPiece = board.getPiece(selectedRow, selectedCol);

                if (selectedPiece.canMove(selectedRow, selectedCol, r, c, board) && isMoveSafe(selectedRow, selectedCol, r, c)) {

                    // ---------- ถ้าศัตรูมี Shield ----------
                    if (targetPiece != null && targetPiece.isWhite() != currentTurn && targetPiece.hasShield()) {
                        System.out.println("Shield Blocked the attack!");

                        targetPiece.setShield(false);
                        selectedPiece.setSuperMove(false);
                        endTurn(null);
                        return;
                    }
                    // ---------- CASTLING ----------
                    if (selectedPiece instanceof King && Math.abs(selectedRow - r) == 2) {
                        int homeCol = selectedPiece.isWhite() ? 0 : 7;
                        if (r < 4 && board.isPathClaer(selectedRow, selectedCol, 0, homeCol)) {
                            System.out.println(homeCol);
                            System.out.println(board.isPathClaer(selectedRow, selectedCol, 0, homeCol));
                            board.movePiece(selectedRow, selectedCol, r, c);
                            board.movePiece(0, selectedCol, r + 1, c);
                        } else if (r > 4 && board.isPathClaer(selectedRow, selectedCol, 7, homeCol)) {
                            board.movePiece(selectedRow, selectedCol, r, c);
                            board.movePiece(7, selectedCol, r - 1, c);
                        }

                    } else {
                        // ---------- SIMPLE MOVE or ATTACK ----------
                        board.movePiece(selectedRow, selectedCol, r, c);
                    }

                    boolean isWhitePromotion = selectedPiece.isWhite() && c == 7;
                    boolean isBlackPromotion = !selectedPiece.isWhite() && c == 0;

                    if (selectedPiece instanceof Pawn && (isWhitePromotion || isBlackPromotion)) {
                        // System.out.println("here");
                        tranfrom.preparePromotion(r, c, currentTurn, this);
                        tranfrom.setVisible(true);
                        return;
                    } else {
                        // System.out.println("no promo here");
                        // เดินปกติเสร็จแล้วเรียก endTurn()
                        selectedPiece.setSuperMove(false);
                        endTurn(targetPiece);
                    }

                    //-----DEBUG-----
                    System.out.printf("Move %s from (%d, %d) to (%d, %d)\n", selectedPiece.getClass().getSimpleName(), selectedRow, selectedCol, r, c);
                    //---------------

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
                        boardGUI.highlightButton(i, j, Color.GREEN);
                    } // ถ้าช่องปลายทางมีตัว (และ canMove ผ่าน แสดงว่าเป็นศัตรู) -> สีแดง
                    else if (target.isWhite() != p.isWhite()) {
                        boardGUI.highlightButton(i, j, Color.ORANGE);
                    }
                }
            }
        }
        // ไฮไลท์ตัวที่ถูกเลือกด้วย (สีเหลือง) จะได้รู้ว่าเลือกตัวไหนอยู่
        //boardGUI.highlightButton(r, c, new Color(254, 225, 175));

        // ไฮไลท์ตัวที่มีเกราะ
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece checkP = board.getPiece(i, j);
                if (checkP != null && checkP.hasShield()) {
                    boardGUI.highlightButton(i, j, new Color(135, 206, 250, 150));
                }
            }
        }
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
        boolean myColor = (movingPiece != null) ? movingPiece.isWhite() : currentTurn;

        int kingRow = -1, kingCol = -1;

        // วนลูปหา King ในกระดานจำลอง
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = memBoard.getPiece(r, c);
                if (p != null && p.getClass() == King.class && p.isWhite() == myColor) {
                    kingRow = r;
                    kingCol = c;
                    break;
                }
            }
        }

        // ถ้าหา King ไม่เจอ (ไม่ควรเกิดขึ้น)
        if (kingRow == -1) {
            return false;
        }

        // เช็คศัตรูทุกตัวในกระดานจำลอง
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece enemy = memBoard.getPiece(r, c);
                // ถ้าเป็นศัตรู
                if (enemy != null && enemy.isWhite() != myColor) {
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
        Piece targetPiece = board.getPiece(r, c); // ในกรณีที่เบี้ยกินหมากศัตรูแล้วมีการ Promotion
        Piece newPiece;
        switch (pieceType) {
            case "Rook":
                newPiece = new Rook(isWhite, r, c);
                break;
            case "Knight":
                newPiece = new Knight(isWhite, r, c);
                break;
            case "Bishop":
                newPiece = new Bishop(isWhite, r, c);
                break;
            default:
                newPiece = new Queen(isWhite, r, c);
                break;
        }

        board.setPiece(r, c, newPiece);

        // NOT COMPLETE
        //selectedPiece.setSuperMove(false);
        endTurn(targetPiece);
    }

    private void endTurn(Piece targetPiece) {
        int score = 1;

        if (targetPiece != null) {
            score += targetPiece.getScore();
        }

        if (currentTurn == true) { // White just moved
            white.addScore(score);
            leftGui.changeScore(white.getScore());
        } else {                   // Black just moved
            black.addScore(score);
            rightGui.changeScore(black.getScore());
        }

        isPieceSelected = false;

        // สลับเทิร์นไปยังผู้เล่นคนถัดไป
        currentTurn = !currentTurn;

        boardGUI.resetColors();
        boardGUI.setPieceGUI(board.getBoard());

        // ----------------------------------------------------
        // ส่วนที่เพิ่มใหม่: ตรวจสอบสถานะเกม (Checkmate / Stalemate)
        // ----------------------------------------------------
        boolean hasMoves = hasLegalMoves(currentTurn);
        boolean inCheck = isKingInCheck(currentTurn);

        System.out.println("hasLegalMove" + hasMoves);
        if (!hasMoves) {
            if (inCheck) {
                // รุกฆาต (Checkmate)
                WLscreen.setVisible(true);
                JLabel text = new JLabel();
                text.setBounds(256,240,800,200);
                String winner = currentTurn ? "Black" : "White";
                text.setText(winner + " WIN !");
                text.setFont(new Font("Jacquard 24", Font.PLAIN, 125));
                WLscreen.add(text);
                System.out.println("CHECKMATE! " + winner + " wins!");
                // TODO: คุณสามารถเรียกหน้าต่าง GUI แสดงผู้ชนะที่นี่
            } else {
                WLscreen.setVisible(true);
                JLabel text = new JLabel();
                text.setBounds(256,240,800,200);
                text.setText("Draw !");
                text.setFont(new Font("Jacquard 24", Font.PLAIN, 125));
                WLscreen.add(text);
                // เสมอ (Stalemate)
                System.out.println("STALEMATE! It's a draw!");
                // TODO: คุณสามารถเรียกหน้าต่าง GUI แสดงผลเสมอที่นี่
            }
        } else if (inCheck) {
            // โดนรุกอยู่ แต่ยังหนีได้ (Check)
            for (int i =0 ; i < 8;i++){
                for(int j = 0 ; j<8 ;j++){
                    if (board.getPiece(i, j)instanceof King && board.getPiece(i,j).isWhite() == currentTurn){
                        // System.out.print(i + " "+j);
                        boardGUI.highlightButton(i,j,Color.RED);
                    }
                }
            }
            System.out.println("CHECK!");
            // TODO: อาจจะเปลี่ยนสี BoardGUI ให้รู้ว่าโดนรุกอยู่
        }
        // ----------------------------------------------------
    }

    // ตรวจสอบว่า King ของสีที่กำหนดกำลังถูกรุก (In Check) หรือไม่
    public boolean isKingInCheck(boolean isWhiteColor) {
        int kingRow = -1, kingCol = -1;

        // 1. หาตำแหน่ง King ของเราบนกระดานปัจจุบัน
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board.getPiece(r, c);
                if (p != null && p instanceof King && p.isWhite() == isWhiteColor) {
                    kingRow = r;
                    kingCol = c;
                    break;
                }
            }
        }

        // หากหา King ไม่เจอ (ไม่ควรเกิดขึ้นในเกมปกติ) ให้ถือว่าไม่โดนรุก
        if (kingRow == -1) {
            return false;
        }

        // 2. ตรวจสอบว่ามีหมากศัตรูตัวไหนเดินมาทับตำแหน่ง King ของเราได้ไหม
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece enemy = board.getPiece(r, c);
                // ถ้าเป็นหมากศัตรู และศัตรูสามารถเดินมากิน King ได้
                if (enemy != null && enemy.isWhite() != isWhiteColor) {
                    if (enemy.canMove(r, c, kingRow, kingCol, board)) {
                        return true; // โดนรุกอยู่
                    }
                }
            }
        }
        return false; // ปลอดภัย
    }

    // ตรวจสอบว่าผู้เล่นสีที่กำหนด มีตาเดินที่ถูกต้องและปลอดภัยเหลืออยู่หรือไม่
    public boolean hasLegalMoves(boolean isWhiteColor) {
        // วนลูปหาหมากทุกตัวของเราบนกระดาน
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board.getPiece(r, c);

                // ถ้าเจอหมากของเรา
                if (p != null && p.isWhite() == isWhiteColor) {

                    // ลองจำลองเดินไปทุกๆ ช่องบนกระดาน (8x8)
                    for (int targetR = 0; targetR < 8; targetR++) {
                        for (int targetC = 0; targetC < 8; targetC++) {

                            // --------------------------------------------------
                            // จุดที่ต้องเพิ่ม: ข้ามช่องที่เป็นหมากฝ่ายเดียวกัน
                            // --------------------------------------------------
                            Piece targetSquare = board.getPiece(targetR, targetC);
                            if (targetSquare != null && targetSquare.isWhite() == isWhiteColor) {
                                continue; // ข้ามการจำลองตานี้ไปเลย เพราะกินพวกเดียวกันไม่ได้
                            }

                            // ถ้าหมากสามารถเดินตามกฎได้ (canMove) และ เดินไปแล้ว King ไม่ตาย (isMoveSafe)
                            if (p.canMove(r, c, targetR, targetC, board) && isMoveSafe(r, c, targetR, targetC)) {
                                return true; // แปลว่ายังมีอย่างน้อย 1 ตาเดินที่เล่นได้
                            }
                        }
                    }
                }
            }
        }
        return false; // หมดตาเดิน (Checkmate หรือ Stalemate)
    }
//
//
//
//
//
// -------------------- ITEM LOGIC --------------------

    public void itemUsed(String item) {
        Player currentPlayer;
        ItemslotGUI currentGui;

        if (currentTurn) { // White
            currentPlayer = white;
            currentGui = leftGui;
        } else { // Black
            currentPlayer = black;
            currentGui = rightGui;
        }

        int score = currentPlayer.getScore();

        // Item Price
        int price = 0;
        if (!item.equals("RECALL") && isPieceSelected) {

            Piece p = board.getPiece(selectedRow, selectedCol);

            switch (item) {
                case "SNIPER":
                    price = 5;
                    break;
                case "UNICORN":
                    price = 4;
                    break;
                case "MOVE+":
                    price = 2;
                    break;
                case "SHIELD":
                    price = 3;
                    break;
            }

            if (score < price) {
                System.out.println("Not enough score! Need " + price + " points.");
                return; // คะแนนไม่พอ
            }

            boolean itemSuccess = false;

            switch (item) {
                // Sniper (Rook)
                case "SNIPER":
                    if (p instanceof Rook) {
                        System.out.println(item + " is used");

                        int dir = (p.isWhite()) ? 1 : -1;

                        int targetRow = selectedRow;
                        int targetCol = -1;
                        Piece targetPiece = null;

                        // Find Target
                        for (int c = selectedCol + dir; -1 < c && c < 8; c += dir) {
                            Piece target = board.getPiece(targetRow, c);

                            if (target != null) {
                                if (target.isWhite() == p.isWhite()) {
                                    break;
                                }

                                if (!(target instanceof King) && !(target instanceof Queen)) {
                                    targetPiece = target;
                                    targetCol = c;
                                } else {
                                    break;
                                }
                            }
                        }

                        if (targetPiece != null) {
                            isSniperActive = true;
                            sniperTargetRow = targetRow;
                            sniperTargetCol = targetCol;

                            boardGUI.resetColors(); // Clear normal path color
                            boardGUI.highlightButton(targetRow, targetCol, Color.RED); // Highlight Target
                        }
                        itemSuccess = true;
                    }
                    break;

                // Unicorn (Knight)
                case "UNICORN":
                    if (p instanceof Knight) {
                        System.out.println(item + " is used");

                        p.setSuperMove(true);

                        boardGUI.resetColors();
                        highlightPossibleMoves(selectedRow, selectedCol);
                        itemSuccess = true;
                    }
                    break;

                // Move+ (Pawn)
                case "MOVE+":
                    if (p instanceof Pawn) {
                        System.out.println(item + " is used");

                        p.setSuperMove(true);

                        boardGUI.resetColors();
                        highlightPossibleMoves(selectedRow, selectedCol);
                        itemSuccess = true;
                    }
                    break;

                // Shield (All Pieces)
                case "SHIELD":
                    if (p instanceof Piece) {
                        System.out.println(item + " is used");

                        p.setShield(true);
                        itemSuccess = true;
                    }
                    break;
            }

            if (itemSuccess) {
                currentPlayer.addScore(-price); // หักคะแนน
                currentGui.changeScore(currentPlayer.getScore()); // อัปเดต GUI คะแนน
                System.out.println("Item " + item + " used. Remaining score: " + currentPlayer.getScore());
            } else {
                System.out.println("Cannot use " + item + " with this piece.");
            }

        } else if (item.equals("RECALL")) {
            price = 10;
            if (score >= price) {
                if (currentTurn) {
                    whiteRevive.setVisible(true);
                    whiteRevive.setGamecontrol(this);
                } else {
                    blackRevive.setVisible(false);
                    blackRevive.setGamecontrol(this);
                }
            }

        } else {
            //-----DEBUG-----
            if (Main.DEBUG) {
                System.out.println("Select piece first");
            }
            //---------------
        }
    }

    public void setReviePiece(String k, boolean currentTurn) {
        int bw_c = currentTurn ? 0 : 7;
        if (k.equals("Pawn")) {
            bw_c = bw_c + (currentTurn ? 1 : -1);
        }

        switch (k) {
            case "Rook":
                if (board.getPiece(0, bw_c) == null || board.getPiece(7, bw_c) == null) {
                    int[] row = {0, 7};
                    for (int i : row) {
                        if (board.getPiece(i, bw_c) == null) {
                            boardGUI.highlightButton(i, bw_c, Color.green);
                        }
                    }
                    revivePiece = new Rook(currentTurn);
                    isReviveActive = true;
                }
                break;
            case "Pawn":
                boolean haveSpace = false;
                for (int i = 0; i < 8; i++) {
                    if (board.getPiece(i, bw_c) == null) {
                        haveSpace = true;
                        boardGUI.highlightButton(i, bw_c, Color.green);
                    }
                }
                if (haveSpace) {
                    revivePiece = new Pawn(currentTurn);
                    isReviveActive = true;
                }
                break;
            case "King":
                if (board.getPiece(4, bw_c) == null) {
                    boardGUI.highlightButton(5, bw_c, Color.green);
                    revivePiece = new King(currentTurn);
                    isReviveActive = true;
                }
                break;
            case "Queen":
                if (board.getPiece(3, bw_c) == null) {
                    boardGUI.highlightButton(4, bw_c, Color.green);
                    revivePiece = new Queen(currentTurn);
                    isReviveActive = true;
                }
                break;
            case "Knight":
                if (board.getPiece(1, bw_c) == null || board.getPiece(6, bw_c) == null) {
                    int[] row = {1, 6};
                    for (int i : row) {
                        if (board.getPiece(i, bw_c) == null) {
                            boardGUI.highlightButton(i, bw_c, Color.green);
                        }
                    }
                    revivePiece = new Knight(currentTurn);
                    isReviveActive = true;
                }
                break;
            case "Bishop":
                if (board.getPiece(2, bw_c) == null || board.getPiece(5, bw_c) == null) {
                    int[] row = {2, 5};
                    for (int i : row) {
                        if (board.getPiece(i, bw_c) == null) {
                            boardGUI.highlightButton(i, bw_c, Color.green);
                        }
                    }
                    revivePiece = new Bishop(currentTurn);
                    isReviveActive = true;
                }
                break;
        }
    }
}
