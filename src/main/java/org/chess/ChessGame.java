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

        if (piece == null) {
            return false;
        }

        if (piece.getColor() != currentTurn) {
            return false;
        }

        List<int[]> legalMoves = piece.legalMoves(board, rowFrom, colFrom, piece);
        if (!containsSquare(legalMoves, rowTo, colTo)) {
            return false;
        }

        // To-Do: Add command and observer patterns here

        board.movePiece(rowFrom, colFrom, rowTo, colTo);
        currentTurn = currentTurn.opposite();
        return true;
    }

    private boolean containsSquare(List<int[]> squares, int row, int col) {
        for (int[] square : squares) {
            if (square != null && square.length == 2 && square[0] == row && square[1] == col) {
                return true;
            }
        }
        return false;
    }

    public List<int[]> legalMoves(int row, int col){
        ChessPiece piece = board.getPiece(row, col);
        if (piece == null || piece.getColor() != currentTurn) {
            return List.of();
        }
        return piece.legalMoves(board, row, col, piece);
    }
}
