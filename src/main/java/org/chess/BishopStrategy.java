package org.chess;

import java.util.List;

public class BishopStrategy extends SlidingMoveStrategy {
    @Override
    public List<int[]> moveList(GameBoard board, int[] position, ChessPiece piece) {
        int[][] directions = new int[][]{
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };
        return slide(board, position, piece, directions);
    }
}
