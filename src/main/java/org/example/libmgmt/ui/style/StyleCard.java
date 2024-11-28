package org.example.libmgmt.ui.style;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Styling method for card objects.
 */
public class StyleCard {
  public static final Font titleFont = Font.font("Inter", FontWeight.BOLD, 20);
  public static final Background bg = new Background(new BackgroundFill(Style.LIGHTGREEN,
      Style.SMALL_CORNER, Insets.EMPTY));

  /**
   * Styles card container.
   *
   * @param region Target container.
   * @param w Width.
   * @param h Height,
   */
  public static void styleCard(Region region, double w, double h) {
    region.setBackground(bg);
    region.setMinSize(w, h);
    region.setMaxSize(w, h);
    region.setOnMouseEntered(_ -> {
      ColorAdjust darken = new ColorAdjust();
      darken.setBrightness(-0.1);
      region.setEffect(darken);
      TranslateTransition t = new TranslateTransition(Duration.millis(100), region);
      t.setToY(-10);
      t.play();
    });
    region.setOnMouseExited(_ -> {
      region.setEffect(null);
      TranslateTransition t = new TranslateTransition(Duration.millis(100), region);
      t.setToY(0);
      t.play();
    });
  }

  /**
   * Styles card's title.
   *
   * @param title Target.
   * @param w Width.
   */
  public static void styleTitle(Label title, double w) {
    title.setFont(titleFont);
    title.setWrapText(true);
    title.setMaxWidth(w);
  }

  /**
   * Styles card's content text.
   *
   * @param content Target
   * @param w Width.
   */
  public static void styleContent(Text content, double w) {
    Style.styleWrapText(content, w, 16);
  }
}
