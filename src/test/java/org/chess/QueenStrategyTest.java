package org.chess;

import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenStrategyTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testQueenCanMoveStraight() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece queen = factory.createPiece(PieceColor.WHITE, PieceType.QUEEN);
        board.setPiece(row, col, queen);

        var moves = queen.legalMoves(board, row, col, queen);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row && m[1] == col + 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col));
    }

    @Test
    void testQueenCanMoveDiagonally() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        ChessPiece queen = factory.createPiece(PieceColor.WHITE, PieceType.QUEEN);
        board.setPiece(row, col, queen);

        var moves = queen.legalMoves(board, row, col, queen);

        assertTrue(moves.stream().anyMatch(m -> m[0] == row + 1 && m[1] == col + 1));
        assertTrue(moves.stream().anyMatch(m -> m[0] == row - 1 && m[1] == col - 1));
    }
}
