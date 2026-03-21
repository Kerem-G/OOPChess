package org.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


public class ChessApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ChessGame game = new ChessGame();
        ChessBoardView board = new ChessBoardView(game);

        Scene scene = new Scene(board);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/chess/styles.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}