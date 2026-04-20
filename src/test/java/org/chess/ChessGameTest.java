package org.chess;

import org.chess.observers.CheckObserver;
import org.chess.observers.EventBus;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessGameTest {

    private CheckObserver observer;

    @AfterEach
    void detach() {
        if (observer != null) {
            EventBus.getInstance().detach(observer);
        }
    }

    @Test
    void testInitialTurnIsWhite() {
        ChessGame game = new ChessGame();
        assertEquals(PieceColor.WHITE, game.getCurrentTurn());
    }

    @Test
    void testValidMoveReturnsTrue() {
        ChessGame game = new ChessGame();
        int fromRow = 6, fromCol = 0;
        int toRow = 5, toCol = 0;
        assertTrue(game.doMove(fromRow, fromCol, toRow, toCol));
    }

    @Test
    void testMoveOnEmptySquareReturnsFalse() {
        ChessGame game = new ChessGame();
        int fromRow = 4, fromCol = 4;
        int toRow = 3, toCol = 4;
        assertFalse(game.doMove(fromRow, fromCol, toRow, toCol));
    }

    @Test
    void testMoveWrongColorReturnsFalse() {
        ChessGame game = new ChessGame();
        int fromRow = 1, fromCol = 0;
        int toRow = 2, toCol = 0;
        assertFalse(game.doMove(fromRow, fromCol, toRow, toCol));
    }

    @Test
    void testTurnSwitchesToBlackAfterWhiteMove() {
        ChessGame game = new ChessGame();
        int fromRow = 6, fromCol = 0;
        int toRow = 5, toCol = 0;
        game.doMove(fromRow, fromCol, toRow, toCol);
        assertEquals(PieceColor.BLACK, game.getCurrentTurn());
    }

    @Test
    void testLegalMovesReturnsMoveForCurrentPlayersPiece() {
        ChessGame game = new ChessGame();
        int row = 6, col = 0;
        assertFalse(game.legalMoves(row, col).isEmpty());
    }

    @Test
    void testLegalMovesReturnsEmptyForOpponentPiece() {
        ChessGame game = new ChessGame();
        int row = 1, col = 0;
        assertTrue(game.legalMoves(row, col).isEmpty());
    }

    @Test
    void testLegalMovesReturnsEmptyForEmptySquare() {
        ChessGame game = new ChessGame();
        int row = 4, col = 4;
        assertTrue(game.legalMoves(row, col).isEmpty());
    }

    @Test
    void testGetBoardReturnsBoard() {
        ChessGame game = new ChessGame();
        assertNotNull(game.getBoard());
    }

    @Test
    void testMoveToIllegalDestinationReturnsFalse() {
        ChessGame game = new ChessGame();
        int fromRow = 6, fromCol = 0;
        int toRow = 3, toCol = 0;
        assertFalse(game.doMove(fromRow, fromCol, toRow, toCol));
    }

    @Test
    void testIsInCheckWhenKingUnderAttack() {
        ChessGame game = new ChessGame();
        GameBoard board = game.getBoard();
        ChessPieceFactory factory = new ChessPieceFactory();
        int kingRow = 4, kingCol = 4;
        int rookRow = 4, rookCol = 7;
        board.setPiece(kingRow, kingCol, factory.createPiece(PieceColor.WHITE, PieceType.KING));
        board.setPiece(rookRow, rookCol, factory.createPiece(PieceColor.BLACK, PieceType.ROOK));

        assertTrue(game.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testIsNotInCheckWhenKingSafe() {
        ChessGame game = new ChessGame();
        GameBoard board = game.getBoard();
        ChessPieceFactory factory = new ChessPieceFactory();
        int kingRow = 4, kingCol = 4;
        int rookRow = 5, rookCol = 7;
        board.setPiece(kingRow, kingCol, factory.createPiece(PieceColor.WHITE, PieceType.KING));
        board.setPiece(rookRow, rookCol, factory.createPiece(PieceColor.BLACK, PieceType.ROOK));

        assertFalse(game.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testIsCheckmate() {
        ChessGame game = new ChessGame();
        GameBoard board = game.getBoard();
        ChessPieceFactory factory = new ChessPieceFactory();
        board.clearBoard();

        // King in corner, rook covers back rank, queen covers all escape squares
        int kingRow = 0, kingCol = 0;
        int rookRow = 0, rookCol = 4;
        int queenRow = 1, queenCol = 2;
        board.setPiece(kingRow, kingCol, factory.createPiece(PieceColor.WHITE, PieceType.KING));
        board.setPiece(rookRow, rookCol, factory.createPiece(PieceColor.BLACK, PieceType.ROOK));
        board.setPiece(queenRow, queenCol, factory.createPiece(PieceColor.BLACK, PieceType.QUEEN));

        assertTrue(game.isCheckmate());
    }

    @Test
    void testIsStalemate() {
        ChessGame game = new ChessGame();
        GameBoard board = game.getBoard();
        ChessPieceFactory factory = new ChessPieceFactory();
        board.clearBoard();

        int kingRow = 0, kingCol = 0;
        int queenRow = 1, queenCol = 2;
        int rookRow = 2, rookCol = 1;
        board.setPiece(kingRow, kingCol, factory.createPiece(PieceColor.WHITE, PieceType.KING));
        board.setPiece(queenRow, queenCol, factory.createPiece(PieceColor.BLACK, PieceType.QUEEN));
        board.setPiece(rookRow, rookCol, factory.createPiece(PieceColor.BLACK, PieceType.ROOK));

        assertTrue(game.isStalemate());
    }

    @Test
    void testIsNotCheckmateWhenMovesAvailable() {
        ChessGame game = new ChessGame();
        assertFalse(game.isCheckmate());
    }

    @Test
    void testIsNotStalemateAtStart() {
        ChessGame game = new ChessGame();
        assertFalse(game.isStalemate());
    }

    @Test
    void testDoMovePostsMoveEvent() {
        ChessGame game = new ChessGame();
        observer = new CheckObserver();
        EventBus.getInstance().attach(observer);
        int fromRow = 6, fromCol = 0;
        int toRow = 5, toCol = 0;

        game.doMove(fromRow, fromCol, toRow, toCol);

        assertEquals("MOVE", observer.getLastEvent());
    }

    @Test
    void testDoMovePostsCheckEvent() {
        ChessGame game = new ChessGame();
        GameBoard board = game.getBoard();
        ChessPieceFactory factory = new ChessPieceFactory();
        board.clearBoard();
        observer = new CheckObserver();
        EventBus.getInstance().attach(observer);

        int rookRow = 4, rookCol = 4;
        int kingRow = 4, kingCol = 6;
        int toRow = 4, toCol = 5;
        board.setPiece(rookRow, rookCol, factory.createPiece(PieceColor.WHITE, PieceType.ROOK));
        board.setPiece(kingRow, kingCol, factory.createPiece(PieceColor.BLACK, PieceType.KING));

        game.doMove(rookRow, rookCol, toRow, toCol);

        assertEquals("CHECK", observer.getLastEvent());
    }

    @Test
    void testMoveIntoCheckIsIllegal() {
        ChessGame game = new ChessGame();
        GameBoard board = game.getBoard();
        ChessPieceFactory factory = new ChessPieceFactory();
        int kingRow = 4, kingCol = 4;
        int rookRow = 4, rookCol = 7;
        board.setPiece(kingRow, kingCol, factory.createPiece(PieceColor.WHITE, PieceType.KING));
        board.setPiece(rookRow, rookCol, factory.createPiece(PieceColor.BLACK, PieceType.ROOK));

        assertTrue(game.legalMoves(kingRow, kingCol).stream()
                .noneMatch(m -> m[0] == kingRow));
    }
}
