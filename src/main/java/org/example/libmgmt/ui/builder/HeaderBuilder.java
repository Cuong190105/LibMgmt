package org.example.libmgmt.ui.builder;

import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.components.header.AccountAction;
import org.example.libmgmt.ui.components.header.Header;
import org.example.libmgmt.ui.components.header.HeaderController;
import org.example.libmgmt.ui.components.header.NavBar;
import org.example.libmgmt.ui.components.header.PDFController;
import org.example.libmgmt.ui.page.PageType;

/**
 * A builder to construct header parts.
 */
public class HeaderBuilder implements HeaderBuilderInterface, GeneralBuilder {
  private PageType pageType;
  private static ImageView logo;
  private HeaderController controller;
  private AccountAction accountAction;
  private BorderPane container;

  /**
   * Constructor. Also Preloads the logo image.
   */
  public HeaderBuilder() {
    reset();
    setLogo();
  }

  @Override
  public void reset() {
    pageType = null;
    controller = null;
    accountAction = null;
    container = new BorderPane();
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
  public void setControl(HeaderController headerController) {
    this.controller = headerController;
    container.setCenter(controller.getLayout());
    if (pageType == PageType.MAIN_PAGE) {
      this.accountAction = new AccountAction();
      container.setRight(accountAction.getLayout());
    }
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
          BorderPane.setMargin(logo, new Insets(25, 25, 25, 0));
          BorderPane.setAlignment(logo, Pos.CENTER_LEFT);
          container.setLeft(logo);
        }
        BorderPane.setAlignment(accountAction.getLayout(), Pos.CENTER_RIGHT);
      }
    }
  }

  @Override
  public Header build() {
    return new Header(logo, controller, accountAction, container);
  }
}
