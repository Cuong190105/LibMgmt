package org.example.libmgmt.ui.components.header;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.example.libmgmt.ui.style.Style;

public class Header {
  private ImageView logo;
  private HeaderController headerController;
  private AccountAction accountAction;
  private BorderPane container;

  public Header(ImageView logo, HeaderController navBar, AccountAction accountAction, BorderPane container) {
    this.logo = logo;
    this.headerController = navBar;
    this.accountAction = accountAction;
    this.container = container;
  }

  public ImageView getLogo() {
    return logo;
  }

  public HeaderController getHeaderController() {
    return headerController;
  }

  public AccountAction getAccountAction() {
    return accountAction;
  }

  public BorderPane getContainer() {
    return container;
  }
}
