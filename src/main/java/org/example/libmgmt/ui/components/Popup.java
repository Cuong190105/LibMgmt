package org.example.libmgmt.ui.components;

import java.util.concurrent.Callable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.libmgmt.ui.page.Page;
import org.example.libmgmt.ui.style.Style;

/**
 * A popup
 */
public class Popup {
  private Page page;
  private final Label title;
  private final Text body;
  private TextField response;
  private final HBox btnList;

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
    Style.styleRoundedButton(okBtn, 250, 50, 16);
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
    Style.styleRoundedButton(cancelBtn, 250, 50, 16);
    HBox.setHgrow(cancelBtn, Priority.ALWAYS);
    btnList.getChildren().add(cancelBtn);
    cancelBtn.setOnMouseClicked(_ -> {
      page.closePopUp(this);
    });
  }

  public void addCustomBtn(String text, Color color, Callable<Void> func) {
    Button btn = new Button(text);
    Style.styleRoundedButton(btn, 250, 50, 16);
    btn.getBackground().getFills().get(0).getFill();
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

  public VBox getPopup() {
    VBox container = new VBox(title, body, btnList);
    container.setPrefSize(400, 300);
    return container;
  }

  public void linkToPage(Page page) {
    this.page = page;
  }
}
