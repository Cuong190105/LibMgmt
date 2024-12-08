package org.example.libmgmt.ui.components.body.contentSection;

import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.libmgmt.DB.Feedback;
import org.example.libmgmt.ui.components.body.LoadingRing;
import org.example.libmgmt.ui.components.body.ResultPageSwitch;
import org.example.libmgmt.ui.components.body.card.FeedbackCard;

/**
 * A panel to display user feedbacks.
 */
public class FeedbackPanel extends Content {
  private final Label error = new Label("Có lỗi xảy ra. Vui lòng thử lại sau.");
  private final Label noResult = new Label("Không có kết quả.");
  private Label displayResult;
  private ScrollPane wrapper;
  private VBox container;
  private int status;
  private int page;
  private int totalPage;
  private List<Feedback> feedbackList;

  /**
   * Constructor.
   */
  public FeedbackPanel() {
    super(false);
    container = new VBox();
    wrapper = new ScrollPane(container);
    setFunction();
    style();
  }

  /**
   * Load content.
   */
  public void loadFeedback(int status, List<Feedback> results) {
    page = 0;
    feedbackList = results;
    this.status = status;
    if (results != null) {
      totalPage = results.size() / 20 + (results.size() % 20 == 0 ? 0 : 1);
    }
  }

  public void showFeedback() {
    wrapper.setVvalue(0);
    container.getChildren().clear();
    if (feedbackList != null && !feedbackList.isEmpty()) {
      for (int i = page * 20; i < Math.min((page + 1) * 2, feedbackList.size()); i++) {
        container.getChildren().add(new FeedbackCard(feedbackList.get(i)).getCard());
      }
      ResultPageSwitch sw = new ResultPageSwitch(page == totalPage - 1, page == 0,
          "Trang " + (page + 1) + "/" + (totalPage),
          () -> {
            changePage(1);
            return null;
          },
          () -> {
            changePage(-1);
            return null;
          });
    }
    toggleLoadingRing(false);
  }

  private void changePage(int change) {
    toggleLoadingRing(true);
    page += change;
    showFeedback();
  }

  private void style() {
    container.setSpacing(20);
    container.prefWidthProperty().bind(wrapper.widthProperty().subtract(50));
    container.minHeightProperty().bind(wrapper.heightProperty().subtract(10));
    wrapper.setFitToWidth(true);
  }

  /**
   * Turns loading animation on or off.
   *
   * @param show Show/Hide ring.
   */
  public void toggleLoadingRing(boolean show) {
    FadeTransition fadeOut = new FadeTransition(Duration.millis(100), container);
    FadeTransition fadeIn = new FadeTransition(Duration.millis(100), container);
    fadeOut.setOnFinished(_ -> {
      wrapper.setContent(null);
      if (show) {
        Pane ring = new LoadingRing().getRing();
        StackPane tmp = new StackPane(ring);
        tmp.setPrefSize(wrapper.getWidth() - 50, wrapper.getHeight());
        wrapper.setContent(tmp);
      } else {
        if (status == -1) {
          container.getChildren().add(error);
        } else if (feedbackList.isEmpty()) {
          container.getChildren().add(noResult);
        }
        wrapper.setContent(container);
      }
    });
    fadeOut.setToValue(0);
    fadeIn.setToValue(1);
    SequentialTransition t = new SequentialTransition(fadeOut, fadeIn);
    t.play();
  }

  private void setFunction() {
    for (int i = 0; i < 20; i++) {
      FeedbackCard fc = new FeedbackCard(new Feedback());
      container.getChildren().add(fc.getCard());
    }
  }

  public ScrollPane getContent() {
    return wrapper;
  }
}
