package org.chess;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.chess.pieces.ChessPiece;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.chess.pieces.ImagePieceRenderer;
import org.chess.pieces.PieceColor;
import org.chess.pieces.PieceType;

import java.util.Optional;
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
        setTop(buildButtonBar());

        refreshBoard();
    }

    private HBox buildButtonBar() {
        HBox bar = new HBox(8);
        bar.getChildren().addAll(buildUndoButton(), buildRedoButton());
        return bar;
    }

    private Button buildUndoButton() {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/org/chess/icons/undo.png")));
        icon.setFitWidth(24);
        icon.setFitHeight(24);
        icon.setPreserveRatio(true);

        Button undoButton = new Button();
        undoButton.setGraphic(icon);
        undoButton.setTooltip(new Tooltip("Undo"));
        undoButton.setOnAction(event -> {
            game.undoMove();
            selectedPosition = null;
            legalTargets = List.of();
            refreshBoard();
        });
        return undoButton;
    }

    private Button buildRedoButton() {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/org/chess/icons/undo.png")));
        icon.setFitWidth(24);
        icon.setFitHeight(24);
        icon.setPreserveRatio(true);
        icon.setScaleX(-1);

        Button redoButton = new Button();
        redoButton.setGraphic(icon);
        redoButton.setTooltip(new Tooltip("Redo"));
        redoButton.setOnAction(event -> {
            game.redoMove();
            selectedPosition = null;
            legalTargets = List.of();
            refreshBoard();
        });
        return redoButton;
    }

    private void buildBoardGrid() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane square = new StackPane();

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

        int rowFrom = selectedPosition[0];
        int colFrom = selectedPosition[1];

        if (isLegalTarget(row, col) && game.isPromotion(rowFrom, colFrom, row)) {
            PieceType chosen = askForPromotionChoice(game.getCurrentTurn());
            game.doMove(rowFrom, colFrom, row, col, chosen);
        } else {
            game.doMove(rowFrom, colFrom, row, col);
        }

        selectedPosition = null;
        legalTargets = List.of();
        refreshBoard();
    }

    private PieceType askForPromotionChoice(PieceColor color) {
        ButtonType queenBtn = new ButtonType("Queen");
        ButtonType rookBtn = new ButtonType("Rook");
        ButtonType bishopBtn = new ButtonType("Bishop");
        ButtonType knightBtn = new ButtonType("Knight");

        Alert alert = new Alert(Alert.AlertType.NONE, "", queenBtn, rookBtn, bishopBtn, knightBtn);
        alert.setTitle("Pawn Promotion");
        alert.setHeaderText("Promote pawn to:");

        DialogPane pane = alert.getDialogPane();
        setButtonGraphic(pane, queenBtn, color, PieceType.QUEEN);
        setButtonGraphic(pane, rookBtn, color, PieceType.ROOK);
        setButtonGraphic(pane, bishopBtn, color, PieceType.BISHOP);
        setButtonGraphic(pane, knightBtn, color, PieceType.KNIGHT);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() == queenBtn) return PieceType.QUEEN;
        if (result.get() == rookBtn) return PieceType.ROOK;
        if (result.get() == bishopBtn) return PieceType.BISHOP;
        return PieceType.KNIGHT;
    }

    private void setButtonGraphic(DialogPane pane, ButtonType buttonType, PieceColor color, PieceType type) {
        Button button = (Button) pane.lookupButton(buttonType);
        ImageView icon = new ImagePieceRenderer(color, type).render();
        icon.setFitWidth(32);
        icon.setFitHeight(32);
        button.setGraphic(icon);
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
