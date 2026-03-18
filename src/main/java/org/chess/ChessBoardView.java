package org.chess;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ChessBoardView extends BorderPane {
    private final GridPane boardGrid = new GridPane();
    private final StackPane[][] squares = new StackPane[8][8];
    private final Label[][] pieceLabels = new Label[8][8];
    private final ChessGame game;


    public ChessBoardView(ChessGame game) {
        buildBoardGrid();

        this.game = game;
        setCenter(boardGrid);

        refreshBoard();
    }

    private void buildBoardGrid() {
        // Note: Avoiding dealing with square styles here to prevent repeat in refreshBoard which will need to deal with UI changes for available moves
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Label pieceLabel = new Label("T");
                StackPane square = new StackPane(pieceLabel);

                square.setMinSize(72, 72);
                square.setPrefSize(72, 72);

                square.setOnMouseClicked(event -> System.out.println("mouse clicked--useful for later"));

                squares[row][col] = square;
                pieceLabels[row][col] = pieceLabel;
                boardGrid.add(square, col, row);
            }
        }
    }

    private void refreshBoard() {
        GameBoard board = game.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane square = squares[row][col];
                square.getStyleClass().clear();

                if ((row + col) % 2 == 0) {
                    square.getStyleClass().add("board-square-beige");
                } else {
                    square.getStyleClass().add("board-square-olive");
                }

                pieceLabels[row][col].setText(board.textAtPosition(row, col));
            }
        }
    }
}
