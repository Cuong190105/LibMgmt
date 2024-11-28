package org.example.libmgmt.ui.components.body;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * The body part of a page, which is the main region to display content.
 */
public class Body {
  private final BodyType bodyType;
  private final Label sectionTitle;
  private final HBox subsectionList;
  private final GridPane searchPanel;
  private final Region content;
  private final VBox container;

  /**
   * Constructor.
   *
   * @param bodyType Sets body type.
   * @param sectionTitle Sets title.
   * @param subsectionList Sets subsection list.
   * @param searchPanel Sets search panel.
   * @param content Sets section content.
   * @param container Body wrapper.
   */
  public Body(BodyType bodyType, Label sectionTitle, HBox subsectionList,
              GridPane searchPanel, Region content, VBox container) {
    this.bodyType = bodyType;
    this.sectionTitle = sectionTitle;
    this.subsectionList = subsectionList;
    this.searchPanel = searchPanel;
    this.content = content;
    this.container = container;
  }

  public BodyType getBodyType() {
    return bodyType;
  }

  public Label getSectionTitle() {
    return sectionTitle;
  }

  public HBox getSubsectionList() {
    return subsectionList;
  }

  public GridPane getSearchPanel() {
    return searchPanel;
  }

  public Region getContent() {
    return content;
  }

  /** Get body content. */
  public VBox getContainer() {
    return container;
  }
}