package org.example.libmgmt.ui.components;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Header {
    private ImageView logo;
    private NavBar navBar;
    private AccountAction accountAction;
    private BorderPane container;

    public Header(ImageView logo, NavBar navBar, AccountAction accountAction, BorderPane container) {
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

    public AccountAction getAccountAction() {
        return accountAction;
    }

    public BorderPane getContainer() {
        return container;
    }
}
