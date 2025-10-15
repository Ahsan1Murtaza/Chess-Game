package main;

import pieces.Piece;

public class Move {
    int oldCol, oldRow;
    int newCol, newRow;

    Piece piece;
    Piece capture;

    public Move(Board board, Piece piece, int newCol, int newRow){
        this.newCol = newCol;
        this.newRow = newRow;
        this.oldCol = piece.col;
        this.oldRow = piece.row;
        this.piece = piece;
        this.capture = board.getPiece(newCol, newRow);
    }
}
