package org.chess;

import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    private final ChessPieceFactory factory = new ChessPieceFactory();

    @Test
    void testSetAndGetPiece() {
        GameBoard board = new GameBoard();
        int row = 0, col = 0;
        ChessPiece piece = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(row, col, piece);
        assertEquals(piece, board.getPiece(row, col));
    }

    @Test
    void testIsEmptyWhenNoPiece() {
        GameBoard board = new GameBoard();
        int row = 3, col = 3;
        assertTrue(board.isEmpty(row, col));
    }

    @Test
    void testIsNotEmptyWhenPiecePresent() {
        GameBoard board = new GameBoard();
        int row = 3, col = 3;
        board.setPiece(row, col, factory.createPiece(PieceColor.WHITE, PieceType.ROOK));
        assertFalse(board.isEmpty(row, col));
    }

    @Test
    void testTextAtPositionEmptySquare() {
        GameBoard board = new GameBoard();
        int row = 3, col = 3;
        assertEquals("", board.textAtPosition(row, col));
    }

    @Test
    void testTextAtPositionWithPiece() {
        GameBoard board = new GameBoard();
        int row = 3, col = 3;
        board.setPiece(row, col, factory.createPiece(PieceColor.WHITE, PieceType.QUEEN));
        assertEquals("Q", board.textAtPosition(row, col));
    }

    @Test
    void testMovePiecePieceAtDestination() {
        GameBoard board = new GameBoard();
        int fromRow = 0, fromCol = 0;
        int toRow = 4, toCol = 4;
        ChessPiece piece = factory.createPiece(PieceColor.WHITE, PieceType.ROOK);
        board.setPiece(fromRow, fromCol, piece);
        board.movePiece(fromRow, fromCol, toRow, toCol);
        assertEquals(piece, board.getPiece(toRow, toCol));
    }

    @Test
    void testMovePieceClearsSource() {
        GameBoard board = new GameBoard();
        int fromRow = 0, fromCol = 0;
        int toRow = 4, toCol = 4;
        board.setPiece(fromRow, fromCol, factory.createPiece(PieceColor.WHITE, PieceType.ROOK));
        board.movePiece(fromRow, fromCol, toRow, toCol);
        assertTrue(board.isEmpty(fromRow, fromCol));
    }

    @Test
    void testSetupInitialPositionWhitePawnsOnRow6() {
        GameBoard board = new GameBoard();
        board.setupInitialPosition(factory);
        int whitePawnRow = 6;
        int boardSize = 8;
        for (int col = 0; col < boardSize; col++) {
            ChessPiece piece = board.getPiece(whitePawnRow, col);
            assertEquals(PieceType.PAWN, piece.getType());
            assertEquals(PieceColor.WHITE, piece.getColor());
        }
    }

    @Test
    void testSetupInitialPositionBlackPawnsOnRow1() {
        GameBoard board = new GameBoard();
        board.setupInitialPosition(factory);
        int blackPawnRow = 1;
        int boardSize = 8;
        for (int col = 0; col < boardSize; col++) {
            ChessPiece piece = board.getPiece(blackPawnRow, col);
            assertEquals(PieceType.PAWN, piece.getType());
            assertEquals(PieceColor.BLACK, piece.getColor());
        }
    }

    @Test
    void testSetupInitialPositionWhiteKing() {
        GameBoard board = new GameBoard();
        board.setupInitialPosition(factory);
        int row = 7, col = 4;
        ChessPiece king = board.getPiece(row, col);
        assertEquals(PieceType.KING, king.getType());
        assertEquals(PieceColor.WHITE, king.getColor());
    }

    @Test
    void testSetupInitialPositionBlackKing() {
        GameBoard board = new GameBoard();
        board.setupInitialPosition(factory);
        int row = 0, col = 4;
        ChessPiece king = board.getPiece(row, col);
        assertEquals(PieceType.KING, king.getType());
        assertEquals(PieceColor.BLACK, king.getColor());
    }

    @Test
    void testClearBoardRemovesAllPieces() {
        GameBoard board = new GameBoard();
        int boardSize = 8;
        board.setupInitialPosition(factory);
        board.clearBoard();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                assertTrue(board.isEmpty(row, col));
            }
        }
    }

    @Test
    void testFindKingReturnsKingPosition() {
        GameBoard board = new GameBoard();
        int row = 4, col = 4;
        board.setPiece(row, col, factory.createPiece(PieceColor.WHITE, PieceType.KING));

        int[] position = board.findKing(PieceColor.WHITE);

        assertArrayEquals(new int[]{row, col}, position);
    }

    @Test
    void testFindKingReturnsNullWhenNoKing() {
        GameBoard board = new GameBoard();

        assertNull(board.findKing(PieceColor.WHITE));
    }

    @Test
    void testSetupInitialPositionMiddleRowsEmpty() {
        GameBoard board = new GameBoard();
        board.setupInitialPosition(factory);
        int emptyRowStart = 2, emptyRowEnd = 6;
        int boardSize = 8;
        for (int row = emptyRowStart; row < emptyRowEnd; row++) {
            for (int col = 0; col < boardSize; col++) {
                assertTrue(board.isEmpty(row, col));
            }
        }
    }
}
