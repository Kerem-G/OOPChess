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
    private boolean previousHasMoved;

    public MoveCommand(GameBoard board, int rowFrom, int colFrom, int rowTo, int colTo) {
        this.board = board;
        this.rowFrom = rowFrom;
        this.colFrom = colFrom;
        this.rowTo = rowTo;
        this.colTo = colTo;
    }

    @Override
    public void execute() {
        ChessPiece movingPiece = board.getPiece(rowFrom, colFrom);
        capturedPiece = board.getPiece(rowTo, colTo);
        previousHasMoved = movingPiece.hasMoved();
        board.movePiece(rowFrom, colFrom, rowTo, colTo);
        movingPiece.setHasMoved(true);
    }

    @Override
    public void undo() {
        ChessPiece movingPiece = board.getPiece(rowTo, colTo);
        board.movePiece(rowTo, colTo, rowFrom, colFrom);
        board.setPiece(rowTo, colTo, capturedPiece);
        movingPiece.setHasMoved(previousHasMoved);
    }
}
