package org.chess;

import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;

public class GameBoard {
    public static final int BOARD_SIZE = 8;

    private static final int BLACK_HOME_ROW = 0;
    private static final int BLACK_PAWN_ROW = 1;
    private static final int WHITE_PAWN_ROW = 6;
    private static final int WHITE_HOME_ROW = 7;
    private static final int MIDDLE_ROW_START = 2;
    private static final int MIDDLE_ROW_END = 6;

    private static final int ROOK_LEFT_COL = 0;
    private static final int KNIGHT_LEFT_COL = 1;
    private static final int BISHOP_LEFT_COL = 2;
    private static final int QUEEN_COL = 3;
    private static final int KING_COL = 4;
    private static final int BISHOP_RIGHT_COL = 5;
    private static final int KNIGHT_RIGHT_COL = 6;
    private static final int ROOK_RIGHT_COL = 7;

    private final ChessPiece[][] piecesOnBoard = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

    public ChessPiece getPiece(int row, int col) {
        return piecesOnBoard[row][col];
    }

    public void setPiece(int row, int col, ChessPiece piece) {
        piecesOnBoard[row][col] = piece;
    }

    public boolean isEmpty(int row, int col) {
        return getPiece(row, col) == null;
    }

    public String textAtPosition(int row, int col) {
        return getPiece(row, col) == null ? "" : getPiece(row, col).displayText();
    }

    public void movePiece(int rowFrom, int colFrom, int rowTo, int colTo) {
        ChessPiece movingPiece = getPiece(rowFrom, colFrom);
        setPiece(rowTo, colTo, movingPiece);
        setPiece(rowFrom, colFrom, null);
    }

    public void setupInitialPosition(ChessPieceFactory factory) {
        setupHomeRank(PieceColor.BLACK, BLACK_HOME_ROW, factory);
        setupPawns(PieceColor.BLACK, BLACK_PAWN_ROW, factory);

        for (int row = MIDDLE_ROW_START; row < MIDDLE_ROW_END; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                piecesOnBoard[row][col] = null;
            }
        }

        setupPawns(PieceColor.WHITE, WHITE_PAWN_ROW, factory);
        setupHomeRank(PieceColor.WHITE, WHITE_HOME_ROW, factory);
    }

    public void clearBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                piecesOnBoard[row][col] = null;
            }
        }
    }

    public int[] findKing(PieceColor color) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                ChessPiece piece = getPiece(row, col);
                if (piece != null && piece.getType() == PieceType.KING && piece.getColor() == color) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private void setupPawns(PieceColor color, int row, ChessPieceFactory factory) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            setPiece(row, col, factory.createPiece(color, PieceType.PAWN));
        }
    }

    private void setupHomeRank(PieceColor color, int row, ChessPieceFactory factory) {
        setPiece(row, ROOK_LEFT_COL, factory.createPiece(color, PieceType.ROOK));
        setPiece(row, KNIGHT_LEFT_COL, factory.createPiece(color, PieceType.KNIGHT));
        setPiece(row, BISHOP_LEFT_COL, factory.createPiece(color, PieceType.BISHOP));
        setPiece(row, QUEEN_COL, factory.createPiece(color, PieceType.QUEEN));
        setPiece(row, KING_COL, factory.createPiece(color, PieceType.KING));
        setPiece(row, BISHOP_RIGHT_COL, factory.createPiece(color, PieceType.BISHOP));
        setPiece(row, KNIGHT_RIGHT_COL, factory.createPiece(color, PieceType.KNIGHT));
        setPiece(row, ROOK_RIGHT_COL, factory.createPiece(color, PieceType.ROOK));
    }
}
