package org.chess;

import java.util.List;
import java.util.Objects;

public class ChessPiece {
    private final PieceColor color;
    private final PieceType type;
    private final MoveStrategy moveStrategy;

    public ChessPiece(PieceColor color, PieceType type, MoveStrategy moveStrategy) {
        this.color = color;
        this.type = type;
        this.moveStrategy = moveStrategy;
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    // To-Do: Implement Strategy Pattern
    public List<int[]> legalMoves(GameBoard board, int row, int col, ChessPiece piece) {
        return moveStrategy.moveList(board, new int[]{row, col}, piece);
    }

    // To-Do: Update this to color--or better yet, image
    public String displayText() {
        char letter = type.getLetter();
        return color == PieceColor.WHITE ? String.valueOf(letter) : String.valueOf(Character.toLowerCase(letter));
    }
}
