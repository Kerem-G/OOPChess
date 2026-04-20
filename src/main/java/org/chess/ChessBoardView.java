package org.chess;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.chess.pieces.ChessPiece;

import java.util.Arrays;
import java.util.List;

public class ChessBoardView extends BorderPane {
    private final GridPane boardGrid = new GridPane();
    private final StackPane[][] squares = new StackPane[8][8];
    private final ChessGame game;
    private int[] selectedPosition;
    private List<int[]> legalTargets = List.of();


    public ChessBoardView(ChessGame game) {
        buildBoardGrid();

        this.game = game;
        setCenter(boardGrid);

        refreshBoard();
    }

    private void buildBoardGrid() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane square = new StackPane();
                square.getChildren().clear();

                square.setMinSize(72, 72);
                square.setPrefSize(72, 72);

                int currentRow = row;
                int currentCol = col;
                square.setOnMouseClicked(event -> onSquareClicked(currentRow, currentCol));

                squares[row][col] = square;
                boardGrid.add(square, col, row);
            }
        }
    }

    private void onSquareClicked(int row, int col) {
        GameBoard board = game.getBoard();
        ChessPiece clickedPiece = board.getPiece(row, col);

        if (selectedPosition == null) {
            if (clickedPiece != null && clickedPiece.getColor() == game.getCurrentTurn()) {
                selectedPosition = new int[]{row, col};
                legalTargets = game.legalMoves(row, col);
            }
            refreshBoard();
            return;
        }

        if (Arrays.equals(selectedPosition, new int[]{row, col})) {
            selectedPosition = null;
            legalTargets = List.of();
            refreshBoard();
            return;
        }

        game.doMove(selectedPosition[0], selectedPosition[1], row, col);
        selectedPosition = null;
        legalTargets = List.of();
        refreshBoard();
    }

    private boolean isLegalTarget(int row, int col) {
        for (int[] target : legalTargets) {
            if (target != null && target.length >= 2 && target[0] == row && target[1] == col) {
                return true;
            }
        }
        return false;
    }

    private void refreshBoard() {
        GameBoard board = game.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane square = squares[row][col];
                square.getStyleClass().clear();
                square.getChildren().clear();

                if ((row + col) % 2 == 0) {
                    square.getStyleClass().add("board-square-beige");
                } else {
                    square.getStyleClass().add("board-square-olive");
                }

                if (Arrays.equals(selectedPosition, new int[]{row, col})) {
                    square.getStyleClass().add("board-square-selected");
                } else if (isLegalTarget(row, col)) {
                    square.getStyleClass().add("board-square-legal");
                }

                ChessPiece piece = board.getPiece(row, col);
                if (piece != null) {
                    square.getChildren().add(piece.render());
                }
            }
        }
    }
}
