package org.example.libmgmt.ui.components;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Header {
    private ImageView logo;
    private NavBar navBar;
    private StackPane accountAction;
    private BorderPane container;

    public Header(ImageView logo, NavBar navBar, StackPane accountAction, BorderPane container) {
        this.logo = logo;
        this.navBar = navBar;
        this.accountAction = accountAction;
        this.container = container;
    }

    public ImageView getLogo() {
        return logo;
    }

    public NavBar getNavBar() {
        return navBar;
    }

    public StackPane getAccountAction() {
        return accountAction;
    }

    public BorderPane getContainer() {
        return container;
    }
}
