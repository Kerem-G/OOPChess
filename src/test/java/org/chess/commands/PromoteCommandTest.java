package org.chess.commands;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PromoteCommandTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testExecutePromotesPawnToQueen() {
        GameBoard board = new GameBoard();
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        board.setPiece(1, 4, pawn);

        PromoteCommand command = new PromoteCommand(board, 1, 4, 0, 4,
                PieceColor.WHITE, PieceType.QUEEN, factory);
        command.execute();

        assertNull(board.getPiece(1, 4));
        ChessPiece promoted = board.getPiece(0, 4);
        assertNotNull(promoted);
        assertEquals(PieceType.QUEEN, promoted.getType());
        assertEquals(PieceColor.WHITE, promoted.getColor());
    }

    @Test
    void testExecutePromotesToChosenType() {
        GameBoard board = new GameBoard();
        ChessPiece pawn = factory.createPiece(PieceColor.BLACK, PieceType.PAWN);
        board.setPiece(6, 4, pawn);

        PromoteCommand command = new PromoteCommand(board, 6, 4, 7, 4,
                PieceColor.BLACK, PieceType.KNIGHT, factory);
        command.execute();

        ChessPiece promoted = board.getPiece(7, 4);
        assertEquals(PieceType.KNIGHT, promoted.getType());
        assertEquals(PieceColor.BLACK, promoted.getColor());
    }

    @Test
    void testUndoRestoresOriginalPawn() {
        GameBoard board = new GameBoard();
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        board.setPiece(1, 4, pawn);

        PromoteCommand command = new PromoteCommand(board, 1, 4, 0, 4,
                PieceColor.WHITE, PieceType.QUEEN, factory);
        command.execute();
        command.undo();

        assertSame(pawn, board.getPiece(1, 4));
        assertNull(board.getPiece(0, 4));
    }

    @Test
    void testUndoAfterCaptureRestoresBothPieces() {
        GameBoard board = new GameBoard();
        ChessPiece pawn = factory.createPiece(PieceColor.WHITE, PieceType.PAWN);
        ChessPiece capturedRook = factory.createPiece(PieceColor.BLACK, PieceType.ROOK);
        board.setPiece(1, 3, pawn);
        board.setPiece(0, 4, capturedRook);

        PromoteCommand command = new PromoteCommand(board, 1, 3, 0, 4,
                PieceColor.WHITE, PieceType.QUEEN, factory);
        command.execute();
        command.undo();

        assertSame(pawn, board.getPiece(1, 3));
        assertSame(capturedRook, board.getPiece(0, 4));
    }
}
