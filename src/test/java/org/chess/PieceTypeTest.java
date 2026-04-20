package org.chess;

import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PieceTypeTest {

    @Test
    void testPawnLetter() {
        assertEquals('P', PieceType.PAWN.getLetter());
    }

    @Test
    void testRookLetter() {
        assertEquals('R', PieceType.ROOK.getLetter());
    }

    @Test
    void testKnightLetter() {
        assertEquals('N', PieceType.KNIGHT.getLetter());
    }

    @Test
    void testBishopLetter() {
        assertEquals('B', PieceType.BISHOP.getLetter());
    }

    @Test
    void testQueenLetter() {
        assertEquals('Q', PieceType.QUEEN.getLetter());
    }

    @Test
    void testKingLetter() {
        assertEquals('K', PieceType.KING.getLetter());
    }
}
