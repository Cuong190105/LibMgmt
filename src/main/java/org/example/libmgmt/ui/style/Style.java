package org.example.libmgmt.ui.style;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Style {
    public static final Insets TEXTBOX_INSETS = new Insets(0, 10, 0, 15);
    public static final String FONT = "Inter";
    public static final CornerRadii BIG_CORNER = new CornerRadii(25);
    public static final CornerRadii SMALL_CORNER = new CornerRadii(15);
    public static final CornerRadii TINY_CORNER = new CornerRadii(3);
    public static final Color DARKGREEN = Color.rgb(114, 191, 120);
    public static final Color LIGHTGREEN = Color.rgb(211, 238, 152);
    public static final Color GRAY = Color.rgb(192, 192, 192);
    public static final Color WHITE = Color.WHITE;
    public static final Border debugBorder = new Border(new BorderStroke(
            Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT
    ));
    public static final Border thicc = new Border(new BorderStroke(
            Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5)
    ));
    public static final Border warningBorder = new Border(new BorderStroke(
            Color.RED, BorderStrokeStyle.SOLID, TINY_CORNER, BorderWidths.DEFAULT
    ));
    public static final Background DARKOVERLAY = new Background(new BackgroundFill(
            Color.rgb(0, 0, 0, 0.1), Style.SMALL_CORNER, Insets.EMPTY
    ));
    public static final ColorAdjust DARKEN = new ColorAdjust();
    public static final DropShadow shadowBorder = new DropShadow(5, Color.BLACK);

    public static void styleTextField(TextField tf, double w, double h,
                                      double size, String prompt) {
        tf.setPrefWidth(w);
        tf.setMinHeight(h);
        tf.setFont(Font.font(FONT, size));
        tf.setPadding(TEXTBOX_INSETS);
        if (prompt != null) {
            tf.setPromptText(prompt);
            tf.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        }
    }

    public static void styleTitle(Label lb, double size) {
        lb.setFont(Font.font(FONT, FontWeight.BOLD, size));
    }

    public static void styleCircleButton(Button btn, double size, Background bg) {
        btn.setMaxSize(size, size);
        btn.setMinSize(size, size);
        btn.setBackground(bg);
        btn.setShape(new Circle(size/2));
    }

    public static void styleRoundedButton(Button btn, double w, double h) {
        btn.setPrefSize(w, h);
        btn.setBackground(Background.EMPTY);
        btn.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE,
                SMALL_CORNER, new BorderWidths(0))));
    }

    public static void styleHoverEffect(Region region) {
        DARKEN.setBrightness(-0.1);
        region.setOnMouseEntered(_ -> {
            if (region.getBackground() == Background.EMPTY) {
                region.setBackground(DARKOVERLAY);
            }
            region.setEffect(DARKEN);
        });
        region.setOnMouseExited(_ -> {
            if (region.getBackground() == DARKOVERLAY) {
                region.setBackground(Background.EMPTY);
            }
            region.setEffect(null);
        });
    }

    public static void setFieldWarningBorder(Region region) {
        region.setBorder(warningBorder);
    }

    public static void styleShadowBorder(Region region) {
        region.setEffect(shadowBorder);
    }

    public static void setDebugBorder(Region region) {
        region.setBorder(debugBorder);
    }

    public static void setThiccDebugBorder(Region region) {
        region.setBorder(thicc);
    }

    public static void styleComboBox(ComboBox cb, double w, double h,
                                     double textsize, String prompt) {
        cb.setBackground(Background.EMPTY);
        cb.setPromptText(prompt);
        cb.setStyle("-fx-prompt-text-fill: rgba(0, 0, 0, 0.3);");
        cb.setMaxWidth(w);
        cb.setMinHeight(h);
        cb.setBorder(new Border(new BorderStroke(GRAY, BorderStrokeStyle.SOLID,
                TINY_CORNER, BorderWidths.DEFAULT)));
        cb.setStyle("-fx-font: " + textsize + " Inter;");
    }

    public static void styleWrapText(Text text, double w, double size) {
        text.setFont(Font.font(FONT, size));
        text.setWrappingWidth(w);
    }
}
