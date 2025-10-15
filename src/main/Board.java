package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import java.util.stream.Collectors;


public class Board extends JPanel {
    public JLabel turnLabel;

    public JLabel whiteTimerLabel;
    public JLabel blackTimerLabel;
    public int tileSize = 70;
    int rows = 8;
    int cols = 8;

    ArrayList<Piece> pieceList = new ArrayList<>();

    public Piece selectedPiece;

    Input input = new Input(this);

    public CheckScanner checkScanner = new CheckScanner(this);

    public int enPassantTile = -1;

    private boolean isWhiteToMove = true;
    private boolean isGameOver = false;

    private static final int TURN_TIME = 300; // 5 minutes in seconds
    private static int whiteTime = TURN_TIME;
    private static int blackTime = TURN_TIME;
    private static Timer timer;



    // Constructor
    public Board(JLabel turnLabel, JLabel whiteTimerLabel, JLabel blackTimerLabel){
        this.turnLabel = turnLabel;
        this.whiteTimerLabel = whiteTimerLabel;
        this.blackTimerLabel = blackTimerLabel;
        this.setPreferredSize(new Dimension(tileSize * cols, tileSize * rows));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPieces();
        startTimer();
    }

    public Piece getPiece(int col, int row){
        for (Piece piece : pieceList){
            if (piece.col == col && piece.row == row){
                return piece;
            }
        }
        return null;
    }


    public boolean isValidMove(Move move){

        if (isGameOver){
            return false;
        }
        if (move.piece.isWhite != isWhiteToMove){
            return false;
        }
        if (sameTeam(move.piece, move.capture)){
            return false;
        }
        if (!move.piece.isValidMovement(move.newCol, move.newRow)){
            return false;
        }
        if (move.piece.moveCollidesWithPiece(move.newCol, move.newRow)){
            return false;
        }
        if (checkScanner.isKingChecked(move)){
            turnLabel.setText(isWhiteToMove ? "White in Check" : "Black in Check");
            return false;
        }

        return true;
    }

    public void capture(Piece piece){
        pieceList.remove(piece);
    }

    public boolean sameTeam(Piece p1, Piece p2){
        if (p1 == null || p2 == null){
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }


    public void promotePawn(Move move){
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void makeMove(Move move){

        if (move.piece.name.equals("Pawn")){
            movePawn(move);
        }
        else if (move.piece.name.equals("King")) {
            moveKing(move);
        }

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newCol * tileSize;
        move.piece.yPos = move.newRow * tileSize;
        move.piece.isFirstMove = false;
        capture(move.capture);

        whiteTime = isWhiteToMove ? whiteTime + 5 : whiteTime;
        updateTimerLabel(whiteTimerLabel, whiteTime);
        blackTime = isWhiteToMove ? blackTime : blackTime + 5;
        updateTimerLabel(blackTimerLabel, blackTime);

        isWhiteToMove = !isWhiteToMove;

        turnLabel.setText(isWhiteToMove ? "White's turn" : "Black's turn");
        updateGameState();


    }

    private void moveKing(Move move){
        if (Math.abs(move.piece.col - move.newCol) == 2){
            Piece rook;
            if (move.piece.col < move.newCol){
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            }
            else {
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.xPos = rook.col * tileSize;
        }
    }

    private void movePawn(Move move){
        // enPassant
        int colorIndex = move.piece.isWhite ? 1 : -1;

        if (getTileNum(move.newCol, move.newRow) == enPassantTile) {
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
//            System.out.println("Pawn Captured due to Enpassant");
        }
        if (Math.abs(move.piece.row - move.newRow) == 2){
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
//            System.out.println("Enpassant Tile Found");
        }
        else {
            enPassantTile = -1;
        }
        // Promotions
        colorIndex = move.piece.isWhite ? 0 : 7;
        if (move.newRow == colorIndex){
            promotePawn(move);
        }
    }

    public int getTileNum(int col, int row){
        return rows * row + col;
    }


    Piece findKing(boolean isWhite){
        for (Piece piece : pieceList){
            if (isWhite == piece.isWhite && piece.name.equals("King")){
                return piece;
            }
        }
        return null;
    }


    public void addPieces(){
        // Knight
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        // Rook
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));
        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));
        // Bishop
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        // Queen
        pieceList.add(new Queen(this, 3, 0, false));
        pieceList.add(new Queen(this, 3, 7, true));
        // King
        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new King(this, 4, 7, true));
        // Pawn
        for (int i = 0; i < 8; i++){
            pieceList.add(new Pawn(this, i, 1, false));
            pieceList.add(new Pawn(this, i, 6, true));
        }
    }

    private void updateGameState(){

        Piece king = findKing(isWhiteToMove);
        if (checkScanner.isGameOver(king)){
            if (checkScanner.isKingChecked(new Move(this, king, king.col, king.row))){
                turnLabel.setText("CheckMate " + (isWhiteToMove ? "Black Wins" : "White Wins"));
//                System.out.println(isWhiteToMove ? "Black Wins" : "White Wins");
                isGameOver = true;
                timer.stop();
            }
            else {

                turnLabel.setText("StaleMate");
//                System.out.println("Stalemate");
                isGameOver = true;
                timer.stop();
            }
        } else if (insufficientMaterial(true) && insufficientMaterial(false)) {
            turnLabel.setText("Draw");
//            System.out.println("Insufficient Material");
            isGameOver = true;
            timer.stop();
        }
    }

    private boolean insufficientMaterial(boolean isWhite){
        ArrayList<String> names = pieceList.stream().filter(p -> p.isWhite == isWhite).map(p -> p.name).collect(Collectors.toCollection(ArrayList::new));
        if (names.contains("Queen") || names.contains("Rook") || names.contains("Pawn")){
            return false;
        }
        return names.size() < 3;
    }


    private static void updateTimerLabel(JLabel timerLabel, int timeLeft) {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        timerLabel.setText(minutes + ":" + String.format("%02d", seconds));
    }





    private void startTimer() {
        timer = new Timer(1000, e -> {
            if (isWhiteToMove) {
                whiteTime--;
                updateTimerLabel(whiteTimerLabel, whiteTime);
                if (whiteTime == 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "White's time is up! Black wins!");
                    isGameOver = true;
                }
            } else {
                blackTime--;
                updateTimerLabel(blackTimerLabel, blackTime);
                if (blackTime == 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Black's time is up! White wins!");
                    isGameOver = true;
                }
            }
        });
        timer.start();
    }








    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        // paint Board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++){
                g2d.setColor((c + r) %2 == 0 ? new Color(118, 150, 86) : new Color(238, 238, 210));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }

        // Paint Highlight
        if (selectedPiece != null){
            for (int r = 0; r < rows; r++){
                for (int c = 0; c < cols; c++){
                    if (isValidMove(new Move(this, selectedPiece, c, r))){
                        g2d.setColor(new Color(199, 199, 62));
                        g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                    }
                }
            }
        }

        // Paint Pieces
        for (Piece piece : pieceList){
            piece.paint(g2d);
        }
    }


}
