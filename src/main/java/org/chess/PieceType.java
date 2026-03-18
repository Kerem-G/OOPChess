package org.chess;

public enum PieceType {
    PAWN('P'),
    ROOK('R'),
    KNIGHT('N'),
    BISHOP('B'),
    QUEEN('Q'),
    KING('K');

    private final char type;

    PieceType(char type) {
        this.type = type;
    }

    public char getLetter() {
        return type;
    }
}