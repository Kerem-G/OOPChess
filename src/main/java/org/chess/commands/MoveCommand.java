package org.chess.commands;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;

public class MoveCommand implements ChessCommand{
    private final GameBoard board;
    private final int rowFrom;
    private final int colFrom;
    private final int rowTo;
    private final int colTo;
    private ChessPiece capturedPiece;

    public MoveCommand(GameBoard board, int rowFrom, int colFrom, int rowTo, int colTo) {
        this.board = board;
        this.rowFrom = rowFrom;
        this.colFrom = colFrom;
        this.rowTo = rowTo;
        this.colTo = colTo;
    }

    @Override
    public void execute() {
        capturedPiece = board.getPiece(rowTo, colTo);
        board.movePiece(rowFrom, colFrom, rowTo, colTo);
    }
    @Override
    public void undo() {
        board.movePiece(rowTo, colTo, rowFrom, colFrom);
        board.setPiece(rowTo, colTo, capturedPiece);
    }
}
