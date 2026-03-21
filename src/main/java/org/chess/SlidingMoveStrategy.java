package org.chess;

import java.util.ArrayList;
import java.util.List;

public class SlidingMoveStrategy implements MoveStrategy {
    public List<int[]> slide(GameBoard board, int[] from, ChessPiece piece, int[][] directions) {
        List<int[]> moves = new ArrayList<>();

        for (int[] direction : directions) {
            int row = from[0] + direction[0];
            int col = from[1] + direction[1];
            while (row >= 0 && row < 8 & col >= 0 && col < 8) {
                ChessPiece blockingPiece = board.getPiece(row, col);
                if (blockingPiece == null) {
                    moves.add(new int[]{row, col});
                } else {
                    if (blockingPiece.getColor() != piece.getColor()) {
                        moves.add(new int[]{row, col});
                    }
                    break;
                }
                row += direction[0];
                col += direction[1];
            }
        }

        return moves;
    }

    // To-do: Implementing here for dev speed -- will make class abstract and move this function into subclasses
    // Example of queen
    public List<int[]> moveList(GameBoard board, int[] position, ChessPiece piece) {
        int[][] directions = new int[][]{
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };
        return slide(board, position, piece, directions);
    }
}
