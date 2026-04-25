package org.chess.strategies;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnStrategyTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testWhitePawnCanMoveOneStepForward() {
        GameBoard board = new GameBoard();
        int row = 6, col = 0;
        int oneStepRow = 5;
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        board.setPiece(row, col, pawn);

        var moves = pawn.legalMoves(board, row, col, pawn);

        assertTrue(moves.stream().anyMatch(m -> m[0] == oneStepRow && m[1] == col));
    }

    @Test
    void testWhitePawnCanMoveTwoStepsFromStartRow() {
        GameBoard board = new GameBoard();
        int row = 6, col = 0;
        int twoStepRow = 4;
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        board.setPiece(row, col, pawn);

        var moves = pawn.legalMoves(board, row, col, pawn);

        assertTrue(moves.stream().anyMatch(m -> m[0] == twoStepRow && m[1] == col));
    }

    @Test
    void testWhitePawnCannotMoveTwoStepsFromNonStartRow() {
        GameBoard board = new GameBoard();
        int row = 5, col = 0;
        int twoStepRow = 3;
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        board.setPiece(row, col, pawn);

        var moves = pawn.legalMoves(board, row, col, pawn);

        assertFalse(moves.stream().anyMatch(m -> m[0] == twoStepRow && m[1] == col));
    }

    @Test
    void testWhitePawnBlockedCannotMoveForward() {
        GameBoard board = new GameBoard();
        int row = 6, col = 0;
        int blockerRow = 5;
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        board.setPiece(row, col, pawn);
        board.setPiece(blockerRow, col, factory.createPiece(PieceColor.BLACK, PieceType.PAWN));

        var moves = pawn.legalMoves(board, row, col, pawn);

        assertTrue(moves.isEmpty());
    }

    @Test
    void testWhitePawnBlockedOnOneStepCannotMoveTwoSteps() {
        GameBoard board = new GameBoard();
        int row = 6, col = 0;
        int blockerRow = 5;
        int twoStepRow = 4;
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        board.setPiece(row, col, pawn);
        board.setPiece(blockerRow, col, factory.createPiece(PieceColor.BLACK, PieceType.PAWN));

        var moves = pawn.legalMoves(board, row, col, pawn);

        assertFalse(moves.stream().anyMatch(m -> m[0] == twoStepRow && m[1] == col));
    }

    @Test
    void testWhitePawnCanCaptureLeft() {
        GameBoard board = new GameBoard();
        int row = 6, col = 3;
        int captureRow = 5, leftCaptureCol = 2;
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        board.setPiece(row, col, pawn);
        board.setPiece(captureRow, leftCaptureCol, factory.createPiece(PieceColor.BLACK, PieceType.PAWN));

        var moves = pawn.legalMoves(board, row, col, pawn);

        assertTrue(moves.stream().anyMatch(m -> m[0] == captureRow && m[1] == leftCaptureCol));
    }

    @Test
    void testWhitePawnCanCaptureRight() {
        GameBoard board = new GameBoard();
        int row = 6, col = 3;
        int captureRow = 5, rightCaptureCol = 4;
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        board.setPiece(row, col, pawn);
        board.setPiece(captureRow, rightCaptureCol, factory.createPiece(PieceColor.BLACK, PieceType.PAWN));

        var moves = pawn.legalMoves(board, row, col, pawn);

        assertTrue(moves.stream().anyMatch(m -> m[0] == captureRow && m[1] == rightCaptureCol));
    }

    @Test
    void testWhitePawnCannotCaptureEmptyDiagonal() {
        GameBoard board = new GameBoard();
        int row = 6, col = 3;
        int captureRow = 5, leftCaptureCol = 2, rightCaptureCol = 4;
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        board.setPiece(row, col, pawn);

        var moves = pawn.legalMoves(board, row, col, pawn);

        assertFalse(moves.stream().anyMatch(m -> m[0] == captureRow && m[1] == leftCaptureCol));
        assertFalse(moves.stream().anyMatch(m -> m[0] == captureRow && m[1] == rightCaptureCol));
    }

    @Test
    void testBlackPawnCanMoveOneStepForward() {
        GameBoard board = new GameBoard();
        int row = 1, col = 0;
        int oneStepRow = 2;
        ChessPiece pawn = factory.createPiece(PieceColor.BLACK, PieceType.PAWN);
        board.setPiece(row, col, pawn);

        var moves = pawn.legalMoves(board, row, col, pawn);

        assertTrue(moves.stream().anyMatch(m -> m[0] == oneStepRow && m[1] == col));
    }

    @Test
    void testBlackPawnCanMoveTwoStepsFromStartRow() {
        GameBoard board = new GameBoard();
        int row = 1, col = 0;
        int twoStepRow = 3;
        ChessPiece pawn = factory.createPiece(PieceColor.BLACK, PieceType.PAWN);
        board.setPiece(row, col, pawn);

        var moves = pawn.legalMoves(board, row, col, pawn);

        assertTrue(moves.stream().anyMatch(m -> m[0] == twoStepRow && m[1] == col));
    }
}
