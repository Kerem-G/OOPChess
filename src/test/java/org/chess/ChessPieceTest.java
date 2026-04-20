package org.chess;

import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
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
