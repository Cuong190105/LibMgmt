package org.example.libmgmt.ui.style;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class StyleCard {
    public static final Font titleFont = Font.font("Inter", FontWeight.BOLD, 20);
    public static final Font contentFont = Font.font("Inter", 12);
    public static final Background bg = new Background(new BackgroundFill(Style.LIGHTGREEN,
            Style.SMALL_CORNER, Insets.EMPTY));
    public static void styleCard(Region region, double w, double h) {
        region.setBackground(bg);
        region.setMinSize(w, h);
        region.setMaxSize(w, h);
        region.setOnMouseEntered(_ -> {
            region.setTranslateY(-5);
            DropShadow s = new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 5, 5, 5, 5);
            region.setEffect(s);
        });
        region.setOnMouseExited(_ -> {
            region.setTranslateY(5);
            region.setEffect(null);
        });
    }

    public static void styleTitle(Label title, double w) {
        title.setFont(titleFont);
        title.setWrapText(true);
        title.setMaxWidth(w);
    }

    public static void styleContent(Text content, double w) {
        Style.styleWrapText(content, w, 12);
    }

    public static void styleImg(ImageView img , double w) {
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setFitWidth(w);
    }
}
