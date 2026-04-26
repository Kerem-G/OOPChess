package org.chess.strategies;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightStrategyTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testKnightCanMoveInLShape() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece knight = factory.createPiece(PieceColor.WHITE, PieceType.KNIGHT);
        board.setPiece(row, col, knight);

        var moves = knight.legalMoves(board, row, col, knight);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 2 && m[1] == col - 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 2 && m[1] == col + 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 2 && m[1] == col - 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 2 && m[1] == col + 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 1 && m[1] == col - 2));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 1 && m[1] == col + 2));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col - 2));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col + 2));
    }

    @Test
    void testKnightCanJumpOverPieces() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece knight = factory.createPiece(PieceColor.WHITE, PieceType.KNIGHT);
        board.setPiece(row, col, knight);
        board.setPiece(row, col + 1, factory.createPiece(PieceColor.BLACK, PieceType.PAWN));
        board.setPiece(row + 1, col, factory.createPiece(PieceColor.BLACK, PieceType.PAWN));

        var moves = knight.legalMoves(board, row, col, knight);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 2 && m[1] == col + 1));
    }

    @Test
    void testKnightCannotLandOnFriendlyPiece() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        int friendlyRow = row - 2, friendlyCol = col - 1;
        ChessPiece knight = factory.createPiece(PieceColor.WHITE, PieceType.KNIGHT);
        board.setPiece(row, col, knight);
        board.setPiece(friendlyRow, friendlyCol, factory.createPiece(PieceColor.WHITE, PieceType.PAWN));

        var moves = knight.legalMoves(board, row, col, knight);

        assertFalse(moves.stream().anyMatch(m -> m[0] == friendlyRow && m[1] == friendlyCol));
    }

    @Test
    void testKnightCanCaptureEnemyPiece() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        int enemyRow = row - 2, enemyCol = col - 1;
        ChessPiece knight = factory.createPiece(PieceColor.WHITE, PieceType.KNIGHT);
        board.setPiece(row, col, knight);
        board.setPiece(enemyRow, enemyCol, factory.createPiece(PieceColor.BLACK, PieceType.PAWN));

        var moves = knight.legalMoves(board, row, col, knight);

        assertTrue(moves.stream().anyMatch(m -> m[0] == enemyRow && m[1] == enemyCol));
    }
}
