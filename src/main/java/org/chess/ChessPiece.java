package org.chess;

import java.util.List;
import java.util.Objects;

public class ChessPiece {
    private final PieceColor color;
    private final PieceType type;

    public ChessPiece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    // To-Do: Implement Strategy Pattern
    public List<Objects> legalMoves(ChessBoardView board) {
        return List.of();
    }

    // To-Do: Update this to color--or better yet, image
    public String displayText() {
        char letter = type.getLetter();
        return color == PieceColor.WHITE ? String.valueOf(letter) : String.valueOf(Character.toLowerCase(letter));
    }
}
