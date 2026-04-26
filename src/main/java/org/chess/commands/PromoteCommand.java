package org.chess.commands;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;

public class PromoteCommand implements ChessCommand {
    private final GameBoard board;
    private final int rowFrom;
    private final int colFrom;
    private final int rowTo;
    private final int colTo;
    private final PieceColor color;
    private final ChessPieceFactory pieceFactory;
    private ChessPiece originalPawn;
    private ChessPiece capturedPiece;
    private final PieceType promotionType;

    public PromoteCommand(GameBoard board, int rowFrom, int colFrom, int rowTo, int colTo, PieceColor color, PieceType promotionType, ChessPieceFactory pieceFactory) {
        this.board = board;
        this.rowFrom = rowFrom;
        this.colFrom = colFrom;
        this.rowTo = rowTo;
        this.colTo = colTo;
        this.color = color;
        this.promotionType = promotionType;
        this.pieceFactory = pieceFactory;
    }

    @Override
    public void execute() {
        originalPawn = board.getPiece(rowFrom, colFrom);
        capturedPiece = board.getPiece(rowTo, colTo);
        board.setPiece(rowFrom, colFrom, null);
        board.setPiece(rowTo, colTo, pieceFactory.createPiece(color, promotionType));
    }

    @Override
    public void undo() {
        board.setPiece(rowFrom, colFrom, originalPawn);
        board.setPiece(rowTo, colTo, capturedPiece);
    }
}