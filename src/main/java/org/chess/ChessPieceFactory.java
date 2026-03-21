package org.chess;

import java.util.EnumMap;
import java.util.Map;

public class ChessPieceFactory {
    private final Map<PieceType, MoveStrategy> strategies = new EnumMap<>(PieceType.class);

    // To-do: update strategy methods for when strategies are implemented
    public ChessPieceFactory() {
        strategies.put(PieceType.KING, new SlidingMoveStrategy());
        strategies.put(PieceType.QUEEN, new SlidingMoveStrategy());
        strategies.put(PieceType.ROOK, new SlidingMoveStrategy());
        strategies.put(PieceType.BISHOP, new SlidingMoveStrategy());
        strategies.put(PieceType.KNIGHT, new SlidingMoveStrategy());
        strategies.put(PieceType.PAWN, new PawnStrategy());
    }

    public ChessPiece createPiece(PieceColor color, PieceType type) {
        return new ChessPiece(color, type, strategies.get(type));
    }
}
