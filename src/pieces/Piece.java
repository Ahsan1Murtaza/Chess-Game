package pieces;

import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {

    public int row, col;
    public int xPos, yPos;
    public boolean isWhite;
    public String name;
    public int value;

    BufferedImage sheet;
    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("pieces.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    protected int sheetScale = sheet.getWidth() / 6;
    public boolean isFirstMove = true;

    Board board;
    Image sprite;

    public Piece(Board board){
        this.board = board;
    }

    public void paint(Graphics2D g2d){
        g2d.drawImage(sprite, xPos, yPos, null);
    }

    public boolean isValidMovement(int col, int row){
        return true;
    }

    public boolean moveCollidesWithPiece(int col, int row){
        return false;
    }
}
