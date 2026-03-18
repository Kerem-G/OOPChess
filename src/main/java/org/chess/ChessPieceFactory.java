package org.chess;

public class ChessPieceFactory {
    // To-Do: Add Strategy Here
    public ChessPiece createPiece(PieceColor color, PieceType type) {
        return new ChessPiece(color, type);
    }
}
