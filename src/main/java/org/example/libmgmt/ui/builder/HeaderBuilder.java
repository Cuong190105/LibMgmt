package org.example.libmgmt.ui.builder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.components.AccountAction;
import org.example.libmgmt.ui.components.Header;
import org.example.libmgmt.ui.components.NavBar;
import org.example.libmgmt.ui.page.PageType;

import java.io.InputStream;

public class HeaderBuilder implements HeaderBuilderInterface, GeneralBuilder {
    private PageType pageType;
    private static ImageView logo;
    private NavBar navBar;
    private AccountAction accountAction;
    private BorderPane container;

    public HeaderBuilder() {
        pageType = null;
        navBar = null;
        accountAction = null;
        setLogo();
        container = new BorderPane();
    }

    public void enableBorder() {
        container.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    @Override
    public void reset() {
        pageType = null;
        navBar = null;
        accountAction = null;
        container = new BorderPane();
//        enableBorder();
    }

    @Override
    public void setType(PageType pageType) {
        this.pageType = pageType;
    }

    private void setLogo() {
        if (logo != null) {
            System.out.println("Logo loaded!");
            return;
        }
        InputStream logoImg = LibMgmt.class.getResourceAsStream("img/logo.png");
        if (logoImg != null) {
            logo = new ImageView(new Image(logoImg));
            logo.setPreserveRatio(true);
        } else {
            System.out.println("Logo not found!");
        }
    }

    @Override
    public void setControl() {
        this.navBar = new NavBar();
        this.accountAction = new AccountAction();
        this.container.setCenter(this.navBar.getLayout());
        this.container.setCenter(this.accountAction.getLayout());
    }

    @Override
    public void style() {
        switch (pageType) {
            case STARTUP_PAGE -> {
                if (logo != null) {
                    logo.setFitWidth(600);
                    BorderPane.setAlignment(logo, Pos.CENTER);
                    container.setCenter(logo);
                }
            }
            case LOGIN_PAGE -> {
                logo.setFitWidth(200);
                if (logo != null) {
                    BorderPane.setAlignment(logo, Pos.CENTER);
                    container.setCenter(logo);
                }
            }
            case MAIN_PAGE -> {
                if (logo != null) {
                    logo.setFitWidth(100);
                    BorderPane.setMargin(logo,new Insets(25, 25, 25, 0));
                    BorderPane.setAlignment(logo, Pos.CENTER_LEFT);
                    container.setLeft(logo);
                }
//                BorderPane.setAlignment(navBar.getLayout(), Pos.CENTER);
                BorderPane.setAlignment(accountAction.getLayout(), Pos.CENTER_RIGHT);
                container.setCenter(navBar.getLayout());
                container.setRight(accountAction.getLayout());

            }
        }
    }

    @Override
    public Header build() {
        return new Header(logo, navBar, accountAction, container);
    }
}
