package pieces;
import main.Board;

import java.awt.image.BufferedImage;

public class Bishop extends Piece{
    public Bishop(Board board, int col, int row, boolean isWhite){
        super(board);

        this.row = row;
        this.col = col;
        this.isWhite = isWhite;

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.name = "Bishop";

        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row){
        return Math.abs(col - this.col) == Math.abs(row - this.row);
    }

    public boolean moveCollidesWithPiece(int col, int row){
        // Up Left
        if (this.col > col && this.row > row){
            for (int i = 1; i < Math.abs(this.col - col); i++){
                if (board.getPiece(this.col - i, this.row - i) != null){
                    return true;
                }
            }
        }
        // Bottom Left
        if (this.col > col && this.row < row){
            for (int i = 1; i < Math.abs(this.col - col); i++){
                if (board.getPiece(this.col - i, this.row + i) != null){
                    return true;
                }
            }
        }
        // Up Right
        if (this.col < col && this.row > row){
            for (int i = 1; i < Math.abs(this.col - col); i++){
                if (board.getPiece(this.col + i, this.row - i) != null){
                    return true;
                }
            }
        }
        // Bottom Right
        if (this.col < col && this.row < row){
            for (int i = 1; i < Math.abs(this.col - col); i++){
                if (board.getPiece(this.col + i, this.row + i) != null){
                    return true;
                }
            }
        }
        return false;
    }
}
