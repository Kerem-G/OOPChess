package org.chess.commands;

import org.chess.GameBoard;
import org.chess.pieces.ChessPiece;

public class CastleCommand implements ChessCommand {
    private static final int KINGSIDE_ROOK_FROM_COL = 7;
    private static final int KINGSIDE_ROOK_TO_COL = 5;
    private static final int QUEENSIDE_ROOK_FROM_COL = 0;
    private static final int QUEENSIDE_ROOK_TO_COL = 3;

    private final GameBoard board;
    private final int row;
    private final int kingFromCol;
    private final int kingToCol;
    private final int rookFromCol;
    private final int rookToCol;
    private boolean kingPreviousHasMoved;
    private boolean rookPreviousHasMoved;

    public CastleCommand(GameBoard board, int rowFrom, int colFrom, int rowTo, int colTo) {
        this.board = board;
        this.row = rowFrom;
        this.kingFromCol = colFrom;
        this.kingToCol = colTo;
        boolean kingside = colTo > colFrom;
        this.rookFromCol = kingside ? KINGSIDE_ROOK_FROM_COL : QUEENSIDE_ROOK_FROM_COL;
        this.rookToCol = kingside ? KINGSIDE_ROOK_TO_COL : QUEENSIDE_ROOK_TO_COL;
    }

    @Override
    public void execute() {
        ChessPiece king = board.getPiece(row, kingFromCol);
        ChessPiece rook = board.getPiece(row, rookFromCol);
        kingPreviousHasMoved = king.hasMoved();
        rookPreviousHasMoved = rook.hasMoved();
        board.movePiece(row, kingFromCol, row, kingToCol);
        board.movePiece(row, rookFromCol, row, rookToCol);
        king.setHasMoved(true);
        rook.setHasMoved(true);
    }

    @Override
    public void undo() {
        ChessPiece king = board.getPiece(row, kingToCol);
        ChessPiece rook = board.getPiece(row, rookToCol);
        board.movePiece(row, kingToCol, row, kingFromCol);
        board.movePiece(row, rookToCol, row, rookFromCol);
        king.setHasMoved(kingPreviousHasMoved);
        rook.setHasMoved(rookPreviousHasMoved);
    }
}