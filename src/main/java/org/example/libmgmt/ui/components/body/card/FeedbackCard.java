package org.example.libmgmt.ui.components.body.card;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.example.libmgmt.DB.Feedback;
import org.example.libmgmt.ui.style.Style;

public class FeedbackCard {
  private static final Background BG_IMPORTANT = new Background(new BackgroundFill(
      Style.YELLOW, Style.SMALL_CORNER, Insets.EMPTY));
  private static final Background BG_STANDARD = new Background(new BackgroundFill(
      Style.LIGHTGREEN, Style.SMALL_CORNER, Insets.EMPTY));
  private Feedback fb;
  private final Label feedbackNumber;
  private final Text from;
  private final Text feedbackDate;
  private final Text feedbackContent;
  private final Button markAsImportant;
  private final VBox container;

  /**
   * Constructor.
   *
   * @param fb Displayed feedback.
   */
  public FeedbackCard(Feedback fb) {
    this.fb = fb;
    feedbackNumber = new Label(Integer.toString(fb.getNumber()));
    feedbackDate = new Text(fb.getDateString());
    from = new Text("Người gửi: " + fb.getSender());
    feedbackContent = new Text(fb.getContent());
    markAsImportant = new Button(fb.isImportant() ? "Bỏ đánh dấu" : "Đánh dấu là quan trọng");
    container = new VBox(feedbackNumber, feedbackDate, from, feedbackContent, markAsImportant);
    setFunction();
    style();
  }

  private void setFunction() {
    markAsImportant.setOnMouseClicked(_ -> {
      if (fb.isImportant()) {
        container.setBackground(BG_STANDARD);
        markAsImportant.setText("Đánh dấu là quan trọng");
      } else {
        container.setBackground(BG_IMPORTANT);
        markAsImportant.setText("Bỏ đánh dấu");
      }
      fb.updateImportant(!fb.isImportant());
    });
  }

  private void style() {
    Style.styleTitle(feedbackNumber, 20);
    Style.styleText(from, 12);
    Style.styleText(feedbackDate, 12);
    Style.styleText(feedbackContent, 16);
    feedbackContent.wrappingWidthProperty().bind(container.widthProperty().subtract(50));
    Style.styleRoundedSolidButton(markAsImportant, Style.DARKGREEN, 200, 50, 16);
    VBox.setMargin(markAsImportant, new Insets(20, 0, 0, 0));
    if (fb.isImportant()) {
      container.setBackground(BG_IMPORTANT);
    } else {
      container.setBackground(BG_STANDARD);
    }
    container.setPadding(new Insets(20));
  }

  public VBox getCard() {
    return container;
  }
}