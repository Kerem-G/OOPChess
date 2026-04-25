package org.chess.commands;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveCommandTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testExecuteMovesPieceToDestination() {
        GameBoard board = new GameBoard();
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(0, 0, rook);

        MoveCommand command = new MoveCommand(board, 0, 0, 4, 0);
        command.execute();

        assertEquals(rook, board.getPiece(4, 0));
        assertNull(board.getPiece(0, 0));
    }

    @Test
    void testUndoRestoresPieceToSource() {
        GameBoard board = new GameBoard();
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(0, 0, rook);

        MoveCommand command = new MoveCommand(board, 0, 0, 4, 0);
        command.execute();
        command.undo();

        assertEquals(rook, board.getPiece(0, 0));
        assertNull(board.getPiece(4, 0));
    }

    @Test
    void testUndoAfterCaptureRestoresCapturedPiece() {
        GameBoard board = new GameBoard();
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        ChessPiece pawn = factory.createPiece(PieceColor.BLACK, PieceType.PAWN);
        board.setPiece(0, 0, rook);
        board.setPiece(4, 0, pawn);

        MoveCommand command = new MoveCommand(board, 0, 0, 4, 0);
        command.execute();
        command.undo();

        assertEquals(rook, board.getPiece(0, 0));
        assertEquals(pawn, board.getPiece(4, 0));
    }

    @Test
    void testExecuteSetsHasMoved() {
        GameBoard board = new GameBoard();
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(0, 0, rook);

        MoveCommand command = new MoveCommand(board, 0, 0, 4, 0);
        command.execute();

        assertTrue(rook.hasMoved());
    }

    @Test
    void testUndoRestoresPriorHasMoved() {
        GameBoard board = new GameBoard();
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        rook.setHasMoved(true);
        board.setPiece(0, 0, rook);

        MoveCommand command = new MoveCommand(board, 0, 0, 4, 0);
        command.execute();
        command.undo();

        assertTrue(rook.hasMoved());
    }
}