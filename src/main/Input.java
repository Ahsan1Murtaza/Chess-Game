package main;

import pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input extends MouseAdapter {
    Board board;

    public Input(Board board){
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e){
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
//        System.out.println("The Row is " + row);
//        System.out.println("The Column is " + col);

        Piece pieceXY = board.getPiece(col, row);
        if (pieceXY != null){
            board.selectedPiece = pieceXY;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e){

        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        Move move = new Move(board, board.selectedPiece, col, row);
        if (board.isValidMove(move)){
            board.makeMove(move);
        }
        else {
            board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
            board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
        }

        board.selectedPiece = null;
        board.repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e){
        if (board.selectedPiece != null){
            board.selectedPiece.xPos = e.getX() - board.tileSize / 2;
            board.selectedPiece.yPos = e.getY() - board.tileSize / 2;
//            System.out.println("Mouse Dragged Xpos : " + board.selectedPiece.xPos);
//            System.out.println("Mouse Dragged Ypos : " + board.selectedPiece.yPos);

            board.repaint();
        }

    }
}
