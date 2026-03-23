package org.chess;

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

    @Test
    void testDisplayTextWhiteIsUppercase() {
        ChessPiece piece = factory.createPiece(PieceColor.WHITE, PieceType.QUEEN);
        assertEquals("Q", piece.displayText());
    }

    @Test
    void testDisplayTextBlackIsLowercase() {
        ChessPiece piece = factory.createPiece(PieceColor.BLACK, PieceType.QUEEN);
        assertEquals("q", piece.displayText());
    }
}
