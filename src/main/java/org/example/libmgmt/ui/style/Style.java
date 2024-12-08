package org.example.libmgmt.ui.style;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.libmgmt.LibMgmt;

import java.util.Objects;

/**
 * General styling method for common elements.
 */
public class Style {
  public static final Insets TEXTBOX_INSETS = new Insets(0, 10, 0, 15);
  public static final String FONT = "Inter";
  public static final CornerRadii BIG_CORNER = new CornerRadii(25);
  public static final CornerRadii SMALL_CORNER = new CornerRadii(15);
  public static final CornerRadii TINY_CORNER = new CornerRadii(5);
  public static final Color DARKGREEN = Color.rgb(114, 191, 120);
  public static final Color LIGHTGREEN = Color.rgb(211, 238, 152);
  public static final Color YELLOW = Color.rgb(254, 255, 159);
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
  public static final Background DARKOVERLAY_TINY = new Background(new BackgroundFill(
      Color.rgb(0, 0, 0, 0.1), Style.TINY_CORNER, Insets.EMPTY
  ));
  public static final ColorAdjust DARKEN = new ColorAdjust();
  public static final DropShadow shadowBorder = new DropShadow(5, Color.BLACK);
  public static final Color RED = Color.rgb(244, 77, 97);

  /**
   * Styles a text field.
   *
   * @param tf Target.
   * @param w Max width.
   * @param h Min height.
   * @param size Font size.
   * @param prompt Prompt/Placeholder text.
   */
  public static void styleTextField(TextField tf, double w, double h,
                                    double size, boolean disablePadding, String prompt) {
    tf.setMaxWidth(w);
    tf.setPrefWidth(w);
    tf.setMinHeight(h);
    tf.setFont(Font.font(FONT, size));
    if (!disablePadding) {
      tf.setPadding(TEXTBOX_INSETS);
    }
    if (prompt != null) {
      tf.setPromptText(prompt);
      tf.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
    }
  }

  /**
   * Styles a label as a section title.
   *
   * @param lb Target.
   * @param size Font size.
   */
  public static void styleTitle(Label lb, double size) {
    lb.setFont(Font.font(FONT, FontWeight.BOLD, size));
  }

  /**
   * Styles a circle shaped button.
   *
   * @param btn Target.
   * @param size Button size.
   */
  public static void styleCircleButton(Button btn, double size) {
    btn.setMaxSize(size, size);
    btn.setMinSize(size, size);
    btn.setShape(new Circle(size / 2));
  }

  /**
   * Styles a circle shaped button.
   *
   * @param btn Target.
   * @param size Button size.
   * @param bg Custom background.
   */
  public static void styleCircleButton(Button btn, double size, Background bg) {
    styleCircleButton(btn, size);
    btn.setBackground(bg);
  }

  /**
   * Styles a rounded rectangle button.
   *
   * @param btn Target.
   * @param w Button width.
   * @param h Button height.
   */
  public static void styleRoundedSolidButton(Button btn, double w, double h, double textSize) {
    if (w >= 50) {
      btn.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE,
          SMALL_CORNER, new BorderWidths(0))));
    } else {
      btn.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE,
          TINY_CORNER, new BorderWidths(0))));
    }
    btn.setPrefSize(w, h);
    btn.setFont(Font.font(FONT, textSize));
    btn.setBackground(Background.EMPTY);
    styleHoverEffect(btn);
  }

  /**
   * Styles a rounded rectangle button.
   *
   * @param btn Target.
   * @param c Button color.
   * @param w Button width.
   * @param h Button height.
   * @param textSize Font size.
   */
  public static void styleRoundedSolidButton(Button btn, Color c,
                                             double w, double h, double textSize) {
    styleRoundedSolidButton(btn, w, h, textSize);
    btn.setBackground(new Background(new BackgroundFill(
        c, SMALL_CORNER, Insets.EMPTY
    )));
  }

  public static void styleRoundedBorderButton(Button btn, Color c,
                                              double w, double h, double textSize) {
    styleRoundedSolidButton(btn, WHITE, w, h, textSize);
    btn.setBorder(new Border(new BorderStroke(
        c, BorderStrokeStyle.SOLID, SMALL_CORNER, new BorderWidths(5)
    )));
  }

  /**
   * Sets a hover effect for a region, which will darken that region on mouse hovered.   * @param region
   */
  public static void styleHoverEffect(Region region) {
    DARKEN.setBrightness(-0.1);
    region.setOnMouseEntered(_ -> {
      if (region.getBackground() == Background.EMPTY) {
        if (region.getWidth() < 50) {
          region.setBackground(DARKOVERLAY_TINY);
        } else {
          region.setBackground(DARKOVERLAY);
        }
      }
      region.setEffect(DARKEN);
    });
    region.setOnMouseExited(_ -> {
      if (region.getBackground() == DARKOVERLAY || region.getBackground() == DARKOVERLAY_TINY) {
        region.setBackground(Background.EMPTY);
      }
      region.setEffect(null);
    });
  }

  /**
   * Sets a warning border for invalid input textfield.
   *
   * @param region Target.
   */
  public static void setFieldWarningBorder(Region region) {
    region.setBorder(warningBorder);
  }

  /**
   * Cast shadows below a region to create float effect.
   *
   * @param region Target.
   */
  public static void styleShadowBorder(Region region) {
    region.setEffect(shadowBorder);
  }

  /**
   * Sets a border for a region for debugging.
   *
   * @param region Target.
   */
  public static void setDebugBorder(Region region) {
    region.setBorder(debugBorder);
  }

  /**
   * Styles text.
   *
   * @param text Target.
   * @param size Font size.
   */
  public static void styleText(Text text, double size) {
    text.setFont(Font.font(FONT, size));
  }

  /**
   * Styles text that needs wrapping within a width.
   *
   * @param text Target.
   * @param w Wrapping width.
   * @param size Font size.
   */
  public static void styleWrapText(Text text, double w, double size) {
    styleText(text, size);
    text.setWrappingWidth(w);
  }

  /**
   * Styles a TextArea.
   *
   * @param text Target.
   * @param w Target width.
   * @param size Font size.
   * @param prompt Prompt/placeholder text.
   */
  public static void styleTextArea(TextArea text, double w, double h, double size, String prompt) {
    text.getStylesheets().add(Objects.requireNonNull(
        LibMgmt.class.getResource("textarea.css")).toExternalForm());
    text.setFont(Font.font(FONT, size));
    text.setWrapText(true);
    text.setPrefHeight(h);
    text.setPrefWidth(w);
    text.setPromptText(prompt);
  }

  /**
   * Quickly styles an ImageView. May be deprecated.
   *
   * @param img Target.
   * @param w Fit width.
   */
  public static void  styleImg(ImageView img, double w) {
    img.setPreserveRatio(true);
    img.setFitWidth(w);
    img.setFitHeight(w);
    img.setSmooth(true);
  }
}