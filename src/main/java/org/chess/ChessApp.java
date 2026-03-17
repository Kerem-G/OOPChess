package org.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


public class ChessApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ChessBoard board = new ChessBoard();
        Scene scene = new Scene(board);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/chess/styles.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}