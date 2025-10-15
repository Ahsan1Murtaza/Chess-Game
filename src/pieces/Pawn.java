package pieces;
import main.Board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece{
    public Pawn(Board board, int col, int row, boolean isWhite){
        super(board);

        this.row = row;
        this.col = col;
        this.isWhite = isWhite;

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.name = "Pawn";

        this.sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row){

        int colorIndex = isWhite ? 1 : -1;

        // Push Pawn 1
        if (this.col == col && row == this.row - colorIndex && board.getPiece(col, row) == null){
            return true;
        }

        // Push Pawn 2
        if (isFirstMove && this.col == col && row == this.row - colorIndex * 2 && board.getPiece(col, row) == null && board.getPiece(col, row + colorIndex) == null){
            return true;
        }

        // Capture Left
        if (col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row) != null){
            return true;
        }

        // Capture Right
        if (col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row) != null){
            return true;
        }

        // Enpassant Left
        if (board.getTileNum(col, row) == board.enPassantTile && col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null){
            return true;
        }

        // Enpassant Right
        if (board.getTileNum(col, row) == board.enPassantTile && col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null){
            return true;
        }
        return false;
    }
}
