package org.example.libmgmt.ui.components.body;

import java.util.concurrent.Callable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.style.Style;

/**
 * Add a page switch to the bottom of any paginated gallery view.
 */
public class ResultPageSwitch {
  private final Button increase;
  private final Button decrease;
  private final Label pageIndicator;
  private final HBox container;

  /**
   * Constructs a page switch.
   *
   * @param disableIncrease Disable increase (right arrow) button.
   * @param disableDecrease Disable decrease (left arrow) button.
   * @param indicator Page indicator message at the middle,
   *        which shows the current page number, or other custom info.
   * @param incFn Sets behavior for increase button on clicked.
   * @param decFn Set behavior for decrease button on clicked.
   */
  public ResultPageSwitch(boolean disableIncrease, boolean disableDecrease,
                          String indicator, Callable<Void> incFn, Callable<Void> decFn) {
    increase = new Button();
    increase.setTooltip(new Tooltip("Trang tiếp"));
    decrease = new Button();
    decrease.setTooltip(new Tooltip("Trang trước"));
    pageIndicator = new Label(indicator);
    increase.setDisable(disableIncrease);
    decrease.setDisable(disableDecrease);
    container = new HBox(decrease, pageIndicator, increase);
    setFunction(incFn, decFn);
    loadMedia();
    style();
  }

  private void loadMedia() {
    ImageView left = new ImageView(new Image(
        LibMgmt.class.getResourceAsStream("img/leftArrow.png")));
    ImageView right = new ImageView(new Image(
        LibMgmt.class.getResourceAsStream("img/rightArrow.png")));
    Style.styleImg(left, 15);
    Style.styleImg(right, 15);
    decrease.setGraphic(left);
    increase.setGraphic(right);
  }

  private void style() {
    Background bg = new Background(new BackgroundFill(
        Style.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY
    ));
    Style.styleCircleButton(increase, 30, bg);
    Style.styleCircleButton(decrease, 30, bg);
    pageIndicator.setFont(Font.font("Inter", 20));
    container.setSpacing(50);
    container.setPadding(new Insets(20));
    container.setAlignment(Pos.CENTER);
    container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  private void setFunction(Callable<Void> incFn, Callable<Void> decFn) {
    decrease.setOnMouseClicked(_ -> {
      try {
        decFn.call();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    increase.setOnMouseClicked(_ -> {
      try {
        incFn.call();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  public HBox getSwitch() {
    return container;
  }
}
