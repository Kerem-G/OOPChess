package org.chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingStrategyTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testKingCanMoveOneStepInAnyDirection() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece king = factory.createPiece(PieceColor.WHITE, PieceType.KING);
        board.setPiece(row, col, king);

        var moves = king.legalMoves(board, row, col, king);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 1 && m[1] == col));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == col - 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == col + 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 1 && m[1] == col - 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 1 && m[1] == col + 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col - 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col + 1));
    }

    @Test
    void testKingCannotMoveTwoSteps() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece king = factory.createPiece(PieceColor.WHITE, PieceType.KING);
        board.setPiece(row, col, king);

        var moves = king.legalMoves(board, row, col, king);

        assertFalse(moves.stream().anyMatch(m -> m[0] == row && m[1] == col + 2));
        assertFalse(moves.stream().anyMatch(m -> m[0] == row + 2 && m[1] == col));
    }

    @Test
    void testKingCannotLandOnFriendlyPiece() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        int friendlyRow = row + 1, friendlyCol = col;
        ChessPiece king = factory.createPiece(PieceColor.WHITE, PieceType.KING);
        board.setPiece(row, col, king);
        board.setPiece(friendlyRow, friendlyCol, factory.createPiece(PieceColor.WHITE, PieceType.PAWN));

        var moves = king.legalMoves(board, row, col, king);

        assertFalse(moves.stream().anyMatch(m -> m[0] == friendlyRow && m[1] == friendlyCol));
    }

    @Test
    void testKingCanCaptureEnemyPiece() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        int enemyRow = row + 1, enemyCol = col;
        ChessPiece king = factory.createPiece(PieceColor.WHITE, PieceType.KING);
        board.setPiece(row, col, king);
        board.setPiece(enemyRow, enemyCol, factory.createPiece(PieceColor.BLACK, PieceType.PAWN));

        var moves = king.legalMoves(board, row, col, king);

        assertTrue(moves.stream().anyMatch(m -> m[0] == enemyRow && m[1] == enemyCol));
    }
}
