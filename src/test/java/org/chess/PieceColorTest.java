package org.chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PieceColorTest {

    @Test
    void testWhiteOppositeIsBlack() {
        assertEquals(PieceColor.BLACK, PieceColor.WHITE.opposite());
    }

    @Test
    void testBlackOppositeIsWhite() {
        assertEquals(PieceColor.WHITE, PieceColor.BLACK.opposite());
    }
}
