package org.example.libmgmt.ui.builder;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.components.header.Header;
import org.example.libmgmt.ui.page.Page;
import org.example.libmgmt.ui.page.PageType;
import org.example.libmgmt.ui.style.Style;

/**
 * A page builder which construct each part of a page.
 */
public class PageBuilder implements PageBuilderInterface, GeneralBuilder {
  private PageType type;
  private Header header;
  private Body body;
  private BorderPane container;

  /**
   * Constructor.
   */
  public PageBuilder() {
    reset();
  }

  @Override
  public void reset() {
    type = null;
    header = null;
    body = null;
    container = new BorderPane();
  }

  @Override
  public void setType(PageType pageType) {
    this.type = pageType;
  }

  @Override
  public void setHeader(Header header) {
    this.header = header;
    container.setTop(header.getContainer());
  }

  @Override
  public void setBody(Body body) {
    this.body = body;
    container.setCenter(body.getContainer());
  }

  /**
   * Styles page layout.
   */
  public void style() {
    if (type != PageType.PDF_VIEWER) {
      final Color BG_YELLOW = Color.rgb(254, 255, 159);
      final Color BG_GREEN = Color.rgb(160, 214, 131);
      Stop[] stops = new Stop[] {new Stop(0, BG_YELLOW), new Stop(1, BG_GREEN)};
      LinearGradient linear = new LinearGradient(0, 0, 1, 1, true,
          CycleMethod.NO_CYCLE, stops);
      BackgroundFill bgFill = new BackgroundFill(linear, CornerRadii.EMPTY, Insets.EMPTY);
      Background BG = new Background(bgFill);
      container.setBackground(BG);
    }
    switch (type) {
      case STARTUP_PAGE -> {
        header.getContainer().prefHeightProperty().bind(container.heightProperty());
      }
      case LOGIN_PAGE -> {
        UIHandler.setVpartition(header.getContainer(), 0.2);
        BorderPane.setMargin(header.getContainer(), new Insets(0, 25, 0 ,25));
        BorderPane.setMargin(body.getContainer(), new Insets(0, 25, 25, 25));
      }
      case MAIN_PAGE -> {
        BorderPane.setMargin(header.getContainer(), new Insets(0, 25, 0 ,25));
        BorderPane.setMargin(body.getContainer(), new Insets(0, 25, 25, 25));
      }
      case PDF_VIEWER -> {
      }
    }
  }

  @Override
  public Page build() {
    return new Page(type, header, body, container);
  }

  public PageType getCurrentPageType() {
    return type;
  }
}
