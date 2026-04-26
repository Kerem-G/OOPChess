package org.chess.strategies;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlidingMoveStrategyTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testSlidesMultipleSquaresInOneDirection() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece queen = factory.createPiece(PieceColor.WHITE, PieceType.QUEEN);
        board.setPiece(row, col, queen);

        var moves = queen.legalMoves(board, row, col, queen);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == col + 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == col + 2));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == col + 3));
    }

    @Test
    void testStopsAtFriendlyPieceAndCannotCapture() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        int blockerCol = 6;
        ChessPiece queen = factory.createPiece(PieceColor.WHITE, PieceType.QUEEN);
        ChessPiece blocker = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(row, col, queen);
        board.setPiece(row, blockerCol, blocker);

        var moves = queen.legalMoves(board, row, col, queen);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == col + 1));
        assertFalse(moves.stream().anyMatch(m -> m[0] == row && m[1] == blockerCol));
        assertFalse(moves.stream().anyMatch(m -> m[0] == row && m[1] == blockerCol + 1));
    }

    @Test
    void testCanCaptureEnemyPieceButNotSlideThrough() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        int enemyCol = 6;
        ChessPiece queen = factory.createPiece(PieceColor.WHITE, PieceType.QUEEN);
        ChessPiece enemy = factory.createPiece(PieceColor.BLACK, PieceType.ROOK);
        board.setPiece(row, col, queen);
        board.setPiece(row, enemyCol, enemy);

        var moves = queen.legalMoves(board, row, col, queen);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == enemyCol));
        assertFalse(moves.stream().anyMatch(m -> m[0] == row && m[1] == enemyCol + 1));
    }

    @Test
    void testSlidesInAllEightDirectionsOnEmptyBoard() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        int rowAbove = row - 1, rowBelow = row + 1;
        int colLeft = col - 1, colRight = col + 1;
        ChessPiece queen = factory.createPiece(PieceColor.WHITE, PieceType.QUEEN);
        board.setPiece(row, col, queen);

        var moves = queen.legalMoves(board, row, col, queen);

        assertTrue(moves.stream().anyMatch(m -> m[0] == rowAbove && m[1] == col));      // up
        assertTrue(moves.stream().anyMatch(m -> m[0] == rowBelow && m[1] == col));      // down
        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == colLeft));       // left
        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == colRight));      // right
        assertTrue(moves.stream().anyMatch(m -> m[0] == rowAbove && m[1] == colLeft));  // up-left diagonal
        assertTrue(moves.stream().anyMatch(m -> m[0] == rowAbove && m[1] == colRight)); // up-right diagonal
        assertTrue(moves.stream().anyMatch(m -> m[0] == rowBelow && m[1] == colLeft));  // down-left diagonal
        assertTrue(moves.stream().anyMatch(m -> m[0] == rowBelow && m[1] == colRight)); // down-right diagonal
    }
}
