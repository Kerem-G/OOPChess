package org.chess.commands;

import org.chess.GameBoard;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    private final ChessPieceFactory pieceFactory = new ChessPieceFactory();
    private final CommandFactory commandFactory = new CommandFactory(pieceFactory);

    @Test
    void testNewCommandReturnsMoveCommandForRegularMove() {
        GameBoard board = new GameBoard();
        board.setPiece(0, 0, pieceFactory.createPiece(PieceColor.WHITE, PieceType.ROOK));

        ChessCommand command = commandFactory.newCommand(board, 0, 0, 4, 0);

        assertInstanceOf(MoveCommand.class, command);
    }

    @Test
    void testNewCommandReturnsPromoteCommandForPawnReachingBackRank() {
        GameBoard board = new GameBoard();
        board.setPiece(1, 4, pieceFactory.createPiece(PieceColor.WHITE, PieceType.PAWN));

        ChessCommand command = commandFactory.newCommand(board, 1, 4, 0, 4);

        assertInstanceOf(PromoteCommand.class, command);
    }

    @Test
    void testNewCommandReturnsCastleCommandForKingMovingTwoSquares() {
        GameBoard board = new GameBoard();
        board.setPiece(7, 4, pieceFactory.createPiece(PieceColor.WHITE, PieceType.KING));

        ChessCommand command = commandFactory.newCommand(board, 7, 4, 7, 6);

        assertInstanceOf(CastleCommand.class, command);
    }

    @Test
    void testIsPromotionTrueForPawnReachingBackRank() {
        GameBoard board = new GameBoard();
        board.setPiece(1, 4, pieceFactory.createPiece(PieceColor.WHITE, PieceType.PAWN));

        assertTrue(commandFactory.isPromotion(board, 1, 4, 0));
    }

    @Test
    void testIsCastleTrueForKingMovingTwoSquares() {
        GameBoard board = new GameBoard();
        board.setPiece(7, 4, pieceFactory.createPiece(PieceColor.WHITE, PieceType.KING));

        assertTrue(commandFactory.isCastle(board, 7, 4, 7, 6));
    }
}
