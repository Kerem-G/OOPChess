package org.chess.commands;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CastleCommandTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testKingsideCastleMovesKingAndRook() {
        GameBoard board = new GameBoard();
        ChessPiece king = factory.createPiece(PieceColor.WHITE, PieceType.KING);
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(7, 4, king);
        board.setPiece(7, 7, rook);

        CastleCommand command = new CastleCommand(board, 7, 4, 7, 6);
        command.execute();

        assertSame(king, board.getPiece(7, 6));
        assertSame(rook, board.getPiece(7, 5));
        assertNull(board.getPiece(7, 4));
        assertNull(board.getPiece(7, 7));
    }

    @Test
    void testQueensideCastleMovesKingAndRook() {
        GameBoard board = new GameBoard();
        ChessPiece king = factory.createPiece(PieceColor.WHITE, PieceType.KING);
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(7, 4, king);
        board.setPiece(7, 0, rook);

        CastleCommand command = new CastleCommand(board, 7, 4, 7, 2);
        command.execute();

        assertSame(king, board.getPiece(7, 2));
        assertSame(rook, board.getPiece(7, 3));
        assertNull(board.getPiece(7, 4));
        assertNull(board.getPiece(7, 0));
    }

    @Test
    void testUndoRestoresBothPieces() {
        GameBoard board = new GameBoard();
        ChessPiece king = factory.createPiece(PieceColor.WHITE, PieceType.KING);
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(7, 4, king);
        board.setPiece(7, 7, rook);

        CastleCommand command = new CastleCommand(board, 7, 4, 7, 6);
        command.execute();
        command.undo();

        assertSame(king, board.getPiece(7, 4));
        assertSame(rook, board.getPiece(7, 7));
        assertNull(board.getPiece(7, 6));
        assertNull(board.getPiece(7, 5));
    }

    @Test
    void testExecuteSetsHasMovedOnBothPieces() {
        GameBoard board = new GameBoard();
        ChessPiece king = factory.createPiece(PieceColor.WHITE, PieceType.KING);
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(7, 4, king);
        board.setPiece(7, 7, rook);

        CastleCommand command = new CastleCommand(board, 7, 4, 7, 6);
        command.execute();

        assertTrue(king.hasMoved());
        assertTrue(rook.hasMoved());
    }

    @Test
    void testUndoRestoresPriorHasMovedState() {
        GameBoard board = new GameBoard();
        ChessPiece king = factory.createPiece(PieceColor.WHITE, PieceType.KING);
        ChessPiece rook = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        king.setHasMoved(true);
        rook.setHasMoved(true);
        board.setPiece(7, 4, king);
        board.setPiece(7, 7, rook);

        CastleCommand command = new CastleCommand(board, 7, 4, 7, 6);
        command.execute();
        command.undo();

        assertTrue(king.hasMoved());
        assertTrue(rook.hasMoved());
    }
}
