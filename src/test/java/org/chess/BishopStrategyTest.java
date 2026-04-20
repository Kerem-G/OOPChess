package org.chess;

import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopStrategyTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testBishopCanMoveDiagonally() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece bishop = factory.createPiece(PieceColor.WHITE, PieceType.BISHOP);
        board.setPiece(row, col, bishop);

        var moves = bishop.legalMoves(board, row, col, bishop);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col + 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 1 && m[1] == col - 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col - 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 1 && m[1] == col + 1));
    }

    @Test
    void testBishopCannotMoveStraight() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece bishop = factory.createPiece(PieceColor.WHITE, PieceType.BISHOP);
        board.setPiece(row, col, bishop);

        var moves = bishop.legalMoves(board, row, col, bishop);

        assertFalse(moves.stream().anyMatch(m -> m[0] == row && m[1] == col + 1));
        assertFalse(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col));
    }
}
