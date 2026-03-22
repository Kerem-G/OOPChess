package org.chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessGameTest {

    @Test
    void testInitialTurnIsWhite() {
        ChessGame game = new ChessGame();
        assertEquals(PieceColor.WHITE, game.getCurrentTurn());
    }

    @Test
    void testValidMoveReturnsTrue() {
        ChessGame game = new ChessGame();
        int fromRow = 6, fromCol = 0;
        int toRow = 5, toCol = 0;
        assertTrue(game.doMove(fromRow, fromCol, toRow, toCol));
    }

    @Test
    void testMoveOnEmptySquareReturnsFalse() {
        ChessGame game = new ChessGame();
        int fromRow = 4, fromCol = 4;
        int toRow = 3, toCol = 4;
        assertFalse(game.doMove(fromRow, fromCol, toRow, toCol));
    }

    @Test
    void testMoveWrongColorReturnsFalse() {
        ChessGame game = new ChessGame();
        int fromRow = 1, fromCol = 0;
        int toRow = 2, toCol = 0;
        assertFalse(game.doMove(fromRow, fromCol, toRow, toCol));
    }

    @Test
    void testTurnSwitchesToBlackAfterWhiteMove() {
        ChessGame game = new ChessGame();
        int fromRow = 6, fromCol = 0;
        int toRow = 5, toCol = 0;
        game.doMove(fromRow, fromCol, toRow, toCol);
        assertEquals(PieceColor.BLACK, game.getCurrentTurn());
    }

    @Test
    void testLegalMovesReturnsMoveForCurrentPlayersPiece() {
        ChessGame game = new ChessGame();
        int row = 6, col = 0;
        assertFalse(game.legalMoves(row, col).isEmpty());
    }

    @Test
    void testLegalMovesReturnsEmptyForOpponentPiece() {
        ChessGame game = new ChessGame();
        int row = 1, col = 0;
        assertTrue(game.legalMoves(row, col).isEmpty());
    }

    @Test
    void testLegalMovesReturnsEmptyForEmptySquare() {
        ChessGame game = new ChessGame();
        int row = 4, col = 4;
        assertTrue(game.legalMoves(row, col).isEmpty());
    }

    @Test
    void testGetBoardReturnsBoard() {
        ChessGame game = new ChessGame();
        assertNotNull(game.getBoard());
    }

    @Test
    void testMoveToIllegalDestinationReturnsFalse() {
        ChessGame game = new ChessGame();
        int fromRow = 6, fromCol = 0;
        int toRow = 3, toCol = 0;
        assertFalse(game.doMove(fromRow, fromCol, toRow, toCol));
    }
}
