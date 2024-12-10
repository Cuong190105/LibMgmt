package org.example.libmgmt.ui.page;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.components.body.contentSection.UpdatableContent;
import org.example.libmgmt.ui.components.header.Header;
import org.example.libmgmt.ui.components.header.QuickPanel;

/**
 * A page.
 */
public class Page {
  private final PageType pageType;
  private final Header header;
  private final Body body;
  private final BorderPane container;
  private AnchorPane wrapper;
  private boolean overlayOn;

  /**
   * Constructor.
   *
   * @param pageType Page type
   * @param header Header;
   * @param body Body;
   * @param container Container;
   */
  public Page(PageType pageType, Header header, Body body, BorderPane container, AnchorPane wrapper) {
    this.pageType = pageType;
    this.header = header;
    this.body = body;
    this.container = container;
    this.overlayOn = false;
    this.wrapper = wrapper;
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

  /**
   * Adds a popup to thís page.
   *
   * @param p Popup
   */
  public void addPopUp(Popup p) {
    wrapper.getChildren().add(p.getPopup());
    p.linkToPage(this);
  }

  /**
   * Close a displayed popup.
   *
   * @param p Target.
   */
  public void closePopUp(Popup p) {
    wrapper.getChildren().remove(p.getPopup());
  }

  /**
   * Adds a quick panel(Notification, Account settings).
   *
   * @param qp Panel.
   */
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
    wrapper.setOnMouseClicked(e -> {
      removeQuickPanel();
    });
  }

  /**
   * Remove a panel.
   */
  public void removeQuickPanel() {
    if (wrapper.getChildren().size() > 1) {
      wrapper.getChildren().removeLast();
      wrapper.setOnMouseClicked(e -> {});
      overlayOn = false;
    }
  }

  public AnchorPane getContainer() {
    return wrapper;
  }

  public void restoreHeader() {
    container.setTop(header.getContainer());
  }

  public void updateContent() {
    if (body.getContent().isUpdatable()) {
      ((UpdatableContent) body.getContent()).update();
    }
  }
}
