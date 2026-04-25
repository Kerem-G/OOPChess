package org.chess.pieces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChessPieceTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testGetColor() {
        ChessPiece piece = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        assertEquals(PieceColor.WHITE, piece.getColor());
    }

    @Test
    void testGetType() {
        ChessPiece piece = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        assertEquals(PieceType.ROOK, piece.getType());
    }
}
