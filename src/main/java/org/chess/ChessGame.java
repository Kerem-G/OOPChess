package org.chess;

import java.util.List;
import java.util.Map;

public class ChessGame {
    private final GameBoard board;
    private final ChessPieceFactory pieceFactory;
    private PieceColor currentTurn;

    public ChessGame() {
        this.board = new GameBoard();
        this.pieceFactory = new ChessPieceFactory();
        this.currentTurn = PieceColor.WHITE;
        board.setupInitialPosition(pieceFactory);
    }

    public GameBoard getBoard() {
        return board;
    }

    public PieceColor getCurrentTurn() {
        return currentTurn;
    }

    public boolean doMove(int rowFrom, int colFrom, int rowTo, int colTo) {
        ChessPiece piece = board.getPiece(rowFrom, colFrom);

        if (piece.getColor() != currentTurn) {
            return false;
        }

        // To-Do: Add logic to implement movement strategy here

        board.movePiece(rowFrom, colFrom, rowTo, colTo);
        return true;
    }
}
