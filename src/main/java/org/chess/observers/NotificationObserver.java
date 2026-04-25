package org.chess.observers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.chess.ChessGame;
import org.chess.pieces.PieceColor;

import java.util.function.Consumer;

public class NotificationObserver implements GameObserver {
    private final ChessGame game;
    private final Consumer<String> setStatus;

    public NotificationObserver(ChessGame game, Consumer<String> setStatus) {
        this.game = game;
        this.setStatus = setStatus;
    }

    @Override
    public void onEvent(String event) {
        if (event.equals("CHECK")) {
            String inCheck = game.getCurrentTurn() == PieceColor.WHITE ? "White" : "Black";
            setStatus.accept(inCheck + " is in check");
        } else if (event.equals("MOVE") || event.equals("UNDO") || event.equals("REDO")) {
            setStatus.accept("");
        } else if (event.equals("CHECKMATE")) {
            String winner = game.getCurrentTurn().opposite() == PieceColor.WHITE ? "White" : "Black";
            showAlert(winner + " wins by checkmate!");
        } else if (event.equals("STALEMATE")) {
            showAlert("Stalemate — draw.");
        }
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "", ButtonType.OK);
            alert.setTitle("Game Over");
            alert.setHeaderText(message);
            alert.showAndWait();
        });
    }
}
