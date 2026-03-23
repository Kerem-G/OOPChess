package org.chess;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy implements MoveStrategy {
    private static final int WHITE_START_ROW = 6;
    private static final int BLACK_START_ROW = 1;

    public List<int[]> moveList(GameBoard board, int[] position, ChessPiece piece) {
        List<int[]> moves = new ArrayList<>();

        int row = position[0];
        int col = position[1];
        int direction = piece.getColor() == PieceColor.WHITE ? -1 : 1;
        int startRow = piece.getColor() == PieceColor.WHITE ? WHITE_START_ROW : BLACK_START_ROW;

        int oneStepRow = row + direction;
        if (isInBounds(oneStepRow, col) && board.isEmpty(oneStepRow, col)) {
            moves.add(new int[]{oneStepRow, col});

            int twoStepRow = row + (2 * direction);
            if (row == startRow && isInBounds(twoStepRow, col) && board.isEmpty(twoStepRow, col)) {
                moves.add(new int[]{twoStepRow, col});
            }
        }

        int captureRow = row + direction;
        int leftCaptureCol = col - 1;
        if (isInBounds(captureRow, leftCaptureCol)) {
            ChessPiece leftPiece = board.getPiece(captureRow, leftCaptureCol);
            if (leftPiece != null && leftPiece.getColor() != piece.getColor()) {
                moves.add(new int[]{captureRow, leftCaptureCol});
            }
        }

        int rightCaptureCol = col + 1;
        if (isInBounds(captureRow, rightCaptureCol)) {
            ChessPiece rightPiece = board.getPiece(captureRow, rightCaptureCol);
            if (rightPiece != null && rightPiece.getColor() != piece.getColor()) {
                moves.add(new int[]{captureRow, rightCaptureCol});
            }
        }

        return moves;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < GameBoard.BOARD_SIZE && col >= 0 && col < GameBoard.BOARD_SIZE;
    }
}
