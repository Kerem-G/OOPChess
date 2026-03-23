package org.chess;

import java.util.ArrayList;
import java.util.List;

public abstract class SlidingMoveStrategy implements MoveStrategy {
    public List<int[]> slide(GameBoard board, int[] from, ChessPiece piece, int[][] directions) {
        List<int[]> moves = new ArrayList<>();

        for (int[] direction : directions) {
            int row = from[0] + direction[0];
            int col = from[1] + direction[1];
            while (row >= 0 && row < GameBoard.BOARD_SIZE & col >= 0 && col < GameBoard.BOARD_SIZE) {
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

    @Override
    public abstract List<int[]> moveList(GameBoard board, int[] position, ChessPiece piece);
}
