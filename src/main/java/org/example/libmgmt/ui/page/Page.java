package org.example.libmgmt.ui.page;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.components.header.Header;
import org.example.libmgmt.ui.components.header.QuickPanel;
import org.example.libmgmt.ui.components.body.Body;

public class Page {
  private final PageType pageType;
  private final Header header;
  private final Body body;
  private final BorderPane container;
  private AnchorPane wrapper;
  private boolean overlayOn;

  public Page(PageType pageType, Header header, Body body, BorderPane container) {
    this.pageType = pageType;
    this.header = header;
    this.body = body;
    this.container = container;
    this.overlayOn = false;
    wrapper = new AnchorPane(container);
    AnchorPane.setTopAnchor(container, 0.0);
    AnchorPane.setLeftAnchor(container, 0.0);
    AnchorPane.setRightAnchor(container, 0.0);
    AnchorPane.setBottomAnchor(container, 0.0);
  }

  public PageType getPageType() {
    return pageType;
  }

  public Header getHeader() {
    return header;
  }

  public Body getBody() {
    return body;
  }

  public void addPopUp(Popup p) {
    wrapper.getChildren().add(p.getPopup());
    p.linkToPage(this);
  }

  public void closePopUp(Popup p) {
    wrapper.getChildren().remove(p);
  }

  public void addQuickPanel(QuickPanel qp) {
    if (overlayOn) {
      removeQuickPanel();
      return;
    }
    Region panel = qp.getContent();
    wrapper.getChildren().add(panel);
    AnchorPane.setTopAnchor(panel, 75.0);
    AnchorPane.setRightAnchor(panel, 0.0);
    if (wrapper.getChildren().size() > 1) {
      overlayOn = true;
    }
    wrapper.setOnMouseClicked(_ -> {
      removeQuickPanel();
    });
  }

  public void removeQuickPanel() {
    if (wrapper.getChildren().size() > 1) {
      wrapper.getChildren().removeLast();
      wrapper.setOnMouseClicked(_ -> {});
      overlayOn = false;
    }
  }

  public AnchorPane getContainer() {
    return wrapper;
  }
}
