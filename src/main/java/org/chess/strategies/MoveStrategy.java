package org.chess.strategies;

import org.chess.pieces.ChessPiece;
import org.chess.GameBoard;

import java.util.List;

public interface MoveStrategy {
    List<int[]> moveList(GameBoard board, int[] position, ChessPiece piece);
}
