package org.example.libmgmt.ui.components.header;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleAccountAction;

import java.io.InputStream;

public class AccountAction {
    private Button account;
    private Button notification;
    private HBox container;

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

        Image avatar = new Image(UserControl.getUser().getAvatar());
        account.setBackground(new Background(new BackgroundImage(
            avatar, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
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
