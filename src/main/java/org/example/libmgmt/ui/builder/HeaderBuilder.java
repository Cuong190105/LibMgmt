package org.example.libmgmt.ui.builder;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.components.Header;
import org.example.libmgmt.ui.components.NavBar;
import org.example.libmgmt.ui.page.PageType;

import java.io.InputStream;

public class HeaderBuilder implements HeaderBuilderInterface, GeneralBuilder {
    PageType pageType;
    ImageView logo;
    NavBar navBar;
    StackPane accountControl;
    BorderPane container;

    public HeaderBuilder() {
        pageType = null;
        navBar = null;
        accountControl = null;
        logo = null;
        container = new BorderPane();
    }

    public void enableBorder() {
        container.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    @Override
    public void reset() {
        pageType = null;
        navBar = null;
        accountControl = null;
        container = new BorderPane();
        enableBorder();
    }

    @Override
    public void setType(PageType pageType) {
        this.pageType = pageType;
    }

    @Override
    public void setLogo() {
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
        this.accountControl = new StackPane();
    }

    @Override
    public void style() {
        switch (pageType) {
            case STARTUP_PAGE -> {
                if (logo != null) {
                    logo.setFitHeight(300);
                    BorderPane.setAlignment(logo, Pos.CENTER);
                    container.setCenter(logo);
                }
            }
            case LOGIN_PAGE -> {
                logo.setFitHeight(100);
                if (logo != null) {
                    BorderPane.setAlignment(logo, Pos.CENTER);
                    container.setCenter(logo);
                }
            }
            case MAIN_PAGE -> {
                if (logo != null) {
                    BorderPane.setAlignment(logo, Pos.CENTER_LEFT);
                    container.setLeft(logo);
                }
                BorderPane.setAlignment(navBar.getLayout(), Pos.CENTER);
                BorderPane.setAlignment(accountControl, Pos.CENTER_RIGHT);
                container.setCenter(navBar.getLayout());
                container.setRight(accountControl);
            }
        }
    }

    @Override
    public Header build() {
        return new Header(logo, navBar, accountControl, container);
    }
}
