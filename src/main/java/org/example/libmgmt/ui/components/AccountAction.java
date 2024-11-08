package org.example.libmgmt.ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.style.StyleAccountAction;

import java.io.InputStream;

public class AccountAction {
    private Button account;
    private Button notification;
    private HBox container;
    //    private Button add;

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

//        iconStream[NUMBER_OF_BUTTONS - 1] = getUserAvatar();
        iconStream[NUMBER_OF_BUTTONS - 1] = LibMgmt.class.getResourceAsStream("img/accountAction/user0123456.png");
        ImageView img = new ImageView(new Image(iconStream[NUMBER_OF_BUTTONS - 1]));
        img.setPreserveRatio(true);
        img.setFitHeight(50);
        account.setGraphic(img);
    }

    private void setFunction() {

    }

    private void style() {
        StyleAccountAction.styleButton(notification);
        StyleAccountAction.styleButton(account);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(25);
//        Style.setDebugBorder(container);
    }

    public HBox getLayout() {
        return container;
    }
}
