package org.chess.pieces;

import org.chess.GameBoard;
import org.chess.strategies.MoveStrategy;
import javafx.scene.image.ImageView;
import java.util.List;

public class ChessPiece {
    private final PieceColor color;
    private final PieceType type;
    private final MoveStrategy moveStrategy;
    private final PieceRenderer renderer;
    private boolean hasMoved = false;

    public ChessPiece(PieceColor color, PieceType type, MoveStrategy moveStrategy, PieceRenderer renderer) {
        this.color = color;
        this.type = type;
        this.moveStrategy = moveStrategy;
        this.renderer = renderer;
    }

    public ImageView render() {
        return renderer.render();
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public List<int[]> legalMoves(GameBoard board, int row, int col, ChessPiece piece) {
        return moveStrategy.moveList(board, new int[]{row, col}, piece);
    }
}
