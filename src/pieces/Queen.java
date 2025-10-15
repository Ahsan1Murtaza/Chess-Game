package pieces;
import main.Board;

import java.awt.image.BufferedImage;

public class Queen extends Piece{
    public Queen(Board board, int col, int row, boolean isWhite){
        super(board);

        this.row = row;
        this.col = col;
        this.isWhite = isWhite;

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.name = "Queen";

        this.sprite = sheet.getSubimage(1 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row){
        return (this.col == col || this.row == row) || Math.abs(col - this.col) == Math.abs(row - this.row);
    }

    public boolean moveCollidesWithPiece(int col, int row){
        if (this.col == col || this.row == row){
            // It works like Rook
            // Left
            if (this.col > col){
                for (int c = this.col - 1; c > col; c--){
                    if (board.getPiece(c, this.row) != null){
                        return true;
                    }
                }
            }
            // Right
            if (this.col < col){
                for (int c = this.col + 1; c < col; c++){
                    if (board.getPiece(c, this.row) != null){
                        return true;
                    }
                }
            }
            // Up
            if (this.row > row){
                for (int r = this.row - 1; r > row; r--){
                    if (board.getPiece(this.col, r) != null){
                        return true;
                    }
                }
            }
            // Down
            if (this.row < row){
                for (int r = this.row + 1; r < row; r++){
                    if (board.getPiece(this.col, r) != null){
                        return true;
                    }
                }
            }
        }
        else {
            // It works like Bishop
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
        }
        return false;
    }
}
