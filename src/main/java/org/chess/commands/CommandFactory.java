package org.chess.commands;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceType;

public class CommandFactory {
    private static final int WHITE_PROMOTION_ROW = 0;
    private static final int BLACK_PROMOTION_ROW = 7;
    private static final int CASTLE_KING_DISTANCE = 2;

    private final ChessPieceFactory pieceFactory;

    public CommandFactory(ChessPieceFactory pieceFactory) {
        this.pieceFactory = pieceFactory;
    }

    public ChessCommand newCommand(GameBoard board, int rowFrom, int colFrom, int rowTo, int colTo) {
        return newCommand(board, rowFrom, colFrom, rowTo, colTo, PieceType.QUEEN);
    }

    public ChessCommand newCommand(GameBoard board, int rowFrom, int colFrom, int rowTo, int colTo,
                                   PieceType promotionChoice) {
        ChessPiece piece = board.getPiece(rowFrom, colFrom);
        if (isCastle(piece, colFrom, colTo)) {
            return new CastleCommand(board, rowFrom, colFrom, rowTo, colTo);
        }
        if (isPromotion(piece, rowTo)) {
            return new PromoteCommand(board, rowFrom, colFrom, rowTo, colTo,
                    piece.getColor(), promotionChoice, pieceFactory);
        }
        return new MoveCommand(board, rowFrom, colFrom, rowTo, colTo);
    }

    public boolean isPromotion(GameBoard board, int rowFrom, int colFrom, int rowTo) {
        return isPromotion(board.getPiece(rowFrom, colFrom), rowTo);
    }

    public boolean isCastle(GameBoard board, int rowFrom, int colFrom, int rowTo, int colTo) {
        return isCastle(board.getPiece(rowFrom, colFrom), colFrom, colTo);
    }

    private boolean isPromotion(ChessPiece piece, int rowTo) {
        if (piece == null || piece.getType() != PieceType.PAWN) {
            return false;
        }
        return rowTo == WHITE_PROMOTION_ROW || rowTo == BLACK_PROMOTION_ROW;
    }

    private boolean isCastle(ChessPiece piece, int colFrom, int colTo) {
        if (piece == null || piece.getType() != PieceType.KING) {
            return false;
        }
        return Math.abs(colTo - colFrom) == CASTLE_KING_DISTANCE;
    }
}