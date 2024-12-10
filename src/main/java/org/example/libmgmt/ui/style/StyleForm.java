package org.example.libmgmt.ui.style;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Styling methods for forms/input fields.
 */
public class StyleForm {
  /**
   * Styles warning text.
   *
   * @param warning Target.
   */
  public static void styleWarning(Text warning) {
    warning.setFill(Color.RED);
    Style.styleWrapText(warning, 500, 16);
  }

  /**
   * Styles combo box.
   *
   * @param cb Target.
   * @param w Max width.
   * @param h Min height.
   * @param textsize Font size.
   * @param prompt Prompt/Placeholder text.
   */
  public static void styleComboBox(ComboBox cb, double w, double h,
                                   double textsize, String prompt) {
    cb.setBackground(Background.EMPTY);
    cb.setPromptText(prompt);
    cb.setMaxWidth(w);
    cb.setMinHeight(h);
    toggleNormalBorder(cb);
    cb.setStyle("-fx-font: " + textsize + " Inter;");
  }

  /**
   * Removes warning border of a combo box.
   *
   * @param cb Target
   */
  public static void toggleNormalBorder(ComboBox cb) {
    cb.setBorder(new Border(new BorderStroke(Style.GRAY, BorderStrokeStyle.SOLID,
        Style.TINY_CORNER, BorderWidths.DEFAULT)));
  }
}
