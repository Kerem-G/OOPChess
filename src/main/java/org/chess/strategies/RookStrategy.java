package org.chess.strategies;

import org.chess.pieces.ChessPiece;
import org.chess.GameBoard;

import java.util.List;

public class RookStrategy extends SlidingMoveStrategy {
    @Override
    public List<int[]> moveList(GameBoard board, int[] position, ChessPiece piece) {
        int[][] directions = new int[][]{
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };
        return slide(board, position, piece, directions);
    }
}
