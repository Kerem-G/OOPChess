package org.chess;

import java.util.List;

public interface MoveStrategy {
    List<int[]> moveList(GameBoard board, int[] position, ChessPiece piece);
}
