package org.chess;

public class GameBoard {
    private final ChessPiece[][] piecesOnBoard = new ChessPiece[8][8];

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
        setupHomeRank(PieceColor.BLACK, 0, factory);
        setupPawns(PieceColor.BLACK, 1, factory);

        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                piecesOnBoard[row][col] = null;
            }
        }

        setupPawns(PieceColor.WHITE, 6, factory);
        setupHomeRank(PieceColor.WHITE, 7, factory);
    }

    private void setupPawns(PieceColor color, int row, ChessPieceFactory factory){
        for (int col = 0; col < 8; col++) {
            setPiece(row, col, factory.createPiece(color, PieceType.PAWN));
        }
    }

    private void setupHomeRank(PieceColor color, int row, ChessPieceFactory factory) {
        setPiece(row, 0, factory.createPiece(color, PieceType.ROOK));
        setPiece(row, 1, factory.createPiece(color, PieceType.KNIGHT));
        setPiece(row, 2, factory.createPiece(color, PieceType.BISHOP));
        setPiece(row, 3, factory.createPiece(color, PieceType.QUEEN));
        setPiece(row, 4, factory.createPiece(color, PieceType.KING));
        setPiece(row, 5, factory.createPiece(color, PieceType.BISHOP));
        setPiece(row, 6, factory.createPiece(color, PieceType.KNIGHT));
        setPiece(row, 7, factory.createPiece(color, PieceType.ROOK));
    }
}
