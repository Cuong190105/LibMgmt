package org.example.libmgmt.ui.components;

import java.util.concurrent.Callable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.libmgmt.ui.page.Page;
import org.example.libmgmt.ui.style.Style;

/**
 * A popup.
 */
public class Popup {
  private Page page;
  private final Label title;
  private final Text body;
  private TextField response;
  private final HBox btnList;
  private VBox container;
  private StackPane wrapper;

  /**
   * Creates a popup.
   *
   * @param title Set title.
   * @param body Set body text.
   */
  public Popup(String title, String body) {
    this.title = new Label(title);
    this.body = new Text(body);
    btnList = new HBox();
    btnList.setSpacing(10);
    container = new VBox(this.title, this.body, this.btnList);
    wrapper = new StackPane(container);
    style();
  }

  private void style() {
    Style.styleTitle(title, 32);
    Style.styleWrapText(body, 400, 20);
    btnList.setSpacing(20);
    container.setBackground(new Background(new BackgroundFill(
        Style.WHITE, Style.BIG_CORNER, Insets.EMPTY
    )));
    container.setAlignment(Pos.CENTER);
    btnList.setAlignment(Pos.CENTER);
    body.setTextAlignment(TextAlignment.CENTER);
    VBox.setMargin(title, new Insets(25, 25, 50, 25));
    VBox.setMargin(btnList, new Insets(20));
    Style.styleShadowBorder(container);
    container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    AnchorPane.setTopAnchor(wrapper, 0.0);
    AnchorPane.setLeftAnchor(wrapper, 0.0);
    AnchorPane.setRightAnchor(wrapper, 0.0);
    AnchorPane.setBottomAnchor(wrapper, 0.0);
  }

  /**
   * Add input textfield.
   *
   * @param prompt textfield prompt.
   */
  public void addRespondField(String prompt) {
    response = new TextField();
    response.setPromptText(prompt);
  }

  /**
   * Add OK button.
   */
  public void addOkBtn() {
    Button okBtn = new Button("OK");
    Style.styleRoundedSolidButton(okBtn, Style.LIGHTGREEN, 250, 50, 16);
    HBox.setHgrow(okBtn, Priority.ALWAYS);
    btnList.getChildren().add(okBtn);
    okBtn.setOnMouseClicked(_ -> {
      page.closePopUp(this);
    });
  }

  /**
   * Add cancel button.
   */
  public void addCancelBtn() {
    Button cancelBtn = new Button("HỦY");
    Style.styleRoundedSolidButton(cancelBtn, 250, 50, 16);
    HBox.setHgrow(cancelBtn, Priority.ALWAYS);
    btnList.getChildren().add(cancelBtn);
    cancelBtn.setOnMouseClicked(_ -> {
      page.closePopUp(this);
    });
  }

  public void addCustomBtn(String text, Color color, Callable<Void> func) {
    Button btn = new Button(text);
    Style.styleRoundedSolidButton(btn, color,250, 50, 16);
    HBox.setHgrow(btn, Priority.ALWAYS);
    btnList.getChildren().add(btn);
    btn.setOnMouseClicked(_ -> {
      try {
        func.call();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      page.closePopUp(this);
    });
  }

  public StackPane getPopup() {
    return wrapper;
  }

  public void linkToPage(Page page) {
    this.page = page;
  }
}
