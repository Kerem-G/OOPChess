package org.chess;

import java.util.ArrayList;
import java.util.List;

public class KingStrategy implements MoveStrategy {
    @Override
    public List<int[]> moveList(GameBoard board, int[] position, ChessPiece piece) {
        List<int[]> moves = new ArrayList<>();

        int row = position[0];
        int col = position[1];

        int[][] offsets = new int[][]{
                {-1, -1}, {-1, 0}, {-1, 1},
                { 0, -1},          { 0, 1},
                { 1, -1}, { 1, 0}, { 1, 1}
        };

        for (int[] offset : offsets) {
            int targetRow = row + offset[0];
            int targetCol = col + offset[1];
            if (isInBounds(targetRow, targetCol)) {
                ChessPiece target = board.getPiece(targetRow, targetCol);
                if (target == null || target.getColor() != piece.getColor()) {
                    moves.add(new int[]{targetRow, targetCol});
                }
            }
        }

        return moves;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < GameBoard.BOARD_SIZE && col >= 0 && col < GameBoard.BOARD_SIZE;
    }
}
