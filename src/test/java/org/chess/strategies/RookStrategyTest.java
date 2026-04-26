package org.chess.strategies;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RookStrategyTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testRookCanMoveHorizontally() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(row, col, rook);

        var moves = rook.legalMoves(board, row, col, rook);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == col + 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == col - 1));
    }

    @Test
    void testRookCanMoveVertically() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(row, col, rook);

        var moves = rook.legalMoves(board, row, col, rook);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 1 && m[1] == col));
    }

    @Test
    void testRookCannotMoveDiagonally() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(row, col, rook);

        var moves = rook.legalMoves(board, row, col, rook);

        assertFalse(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col + 1));
        assertFalse(moves.stream().anyMatch(m -> m[0] == row - 1 && m[1] == col - 1));
    }
}
