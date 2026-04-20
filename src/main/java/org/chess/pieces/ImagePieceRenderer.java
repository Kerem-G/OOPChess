package org.chess.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImagePieceRenderer implements PieceRenderer {
    private final Image image;

    public ImagePieceRenderer(PieceColor color, PieceType type) {
        String filename = color.name().toUpperCase() + "_" + type.name().toUpperCase() + ".png";
        String path = "/org/pieces/used/" + filename;
        this.image = new Image(getClass().getResourceAsStream(path));
    }

    @Override
    public ImageView render() {
        ImageView iv = new ImageView(image);
        iv.setFitWidth(60);
        iv.setFitHeight(60);
        iv.setPreserveRatio(true);
        return iv;
    }
}