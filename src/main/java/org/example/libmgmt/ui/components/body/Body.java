package org.example.libmgmt.ui.components.body;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.libmgmt.ui.components.body.contentSection.Content;

/**
 * The body part of a page, which is the main region to display content.
 */
public class Body {
  private final BodyType bodyType;
  private final HBox titleGroup;
  private final HBox subsectionList;
  private final GridPane searchPanel;
  private final Content content;
  private final VBox container;

  /**
   * Constructor.
   *
   * @param bodyType Sets body type.
   * @param titleGroup Sets title.
   * @param subsectionList Sets subsection list.
   * @param searchPanel Sets search panel.
   * @param content Sets section content.
   * @param container Body wrapper.
   */
  public Body(BodyType bodyType, HBox titleGroup, HBox subsectionList,
              GridPane searchPanel, Content content, VBox container) {
    this.bodyType = bodyType;
    this.titleGroup = titleGroup;
    this.subsectionList = subsectionList;
    this.searchPanel = searchPanel;
    this.content = content;
    this.container = container;
  }

  public BodyType getBodyType() {
    return bodyType;
  }

  public HBox getTitleGroup() {
    return titleGroup;
  }

  public HBox getSubsectionList() {
    return subsectionList;
  }

  public GridPane getSearchPanel() {
    return searchPanel;
  }

  public Content getContent() {
    return content;
  }

  /** Get body content. */
  public VBox getContainer() {
    return container;
  }
}