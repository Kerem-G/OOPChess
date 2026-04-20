package org.chess.pieces;

import org.chess.strategies.*;

import java.util.EnumMap;
import java.util.Map;

public class ChessPieceFactory {
    private final Map<PieceType, MoveStrategy> strategies = new EnumMap<>(PieceType.class);

    public ChessPieceFactory() {
        strategies.put(PieceType.ROOK, new RookStrategy());
        strategies.put(PieceType.QUEEN, new QueenStrategy());
        strategies.put(PieceType.BISHOP, new BishopStrategy());
        strategies.put(PieceType.KING, new KingStrategy());
        strategies.put(PieceType.KNIGHT, new KnightStrategy());
        strategies.put(PieceType.PAWN, new PawnStrategy());
    }

    public ChessPiece createPiece(PieceColor color, PieceType type) {
        return new ChessPiece(color, type, strategies.get(type), new ImagePieceRenderer(color, type));
    }
}
