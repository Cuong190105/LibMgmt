package org.example.libmgmt.ui.components.header;

import java.io.InputStream;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.style.StyleAccountAction;

/**
 * A set of buttons on the top right side to handle account related actions.
 */
public class AccountAction {
  private Button account;
  private Button notification;
  private HBox container;

  /**
   * Constructor.
   */
  public AccountAction() {
    account = new Button();
    notification = new Button();
    container = new HBox(notification, account);
    loadMedia();
    setFunction();
    style();
  }

  private void loadMedia() {
    final int NUMBER_OF_BUTTONS = 2;
    final String[] iconName = {"notification"};
    InputStream[] iconStream = new InputStream[NUMBER_OF_BUTTONS];
    for (int i = 0; i < NUMBER_OF_BUTTONS - 1; i++) {
      iconStream[i] = LibMgmt.class.getResourceAsStream("img/accountAction/" + iconName[i] + ".png");
      if (iconStream[i] != null) {
        ImageView img = new ImageView(new Image(iconStream[i]));
        img.setPreserveRatio(true);
        img.setFitHeight(25);
        switch(i) {
          case 0 -> {
            notification.setGraphic(img);
          }
        }
      }
    }

    account.setBackground(new Background(new BackgroundImage(
        UserControl.getUser().getAvatar(),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER, new BackgroundSize(50, 50, false, false, false, true)
    )));
  }

  private void setFunction() {
    account.setOnMouseClicked(_ -> {
      UIHandler.showAccountAction();
    });

    notification.setOnMouseClicked(_ -> {
      UIHandler.showNotificationPanel();
    });
  }

  private void style() {
    StyleAccountAction.styleButton(notification, false);
    StyleAccountAction.styleButton(account, true);

    container.setAlignment(Pos.CENTER);
    container.setSpacing(25);
  }

  public HBox getLayout() {
    return container;
  }
}
