package org.example.libmgmt.ui.builder;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.components.body.contentSection.Content;

/**
 * Body builder interface.
 */
public interface BodyBuilderInterface {
  /**
   * Set body type.
   *
   * @param bodyType Body type.
   */
  void setType(BodyType bodyType);

  /**
   * Set body title.
   *
   * @param sectionTitle Title.
   */
  void setTitle(String sectionTitle, boolean isSubPage);

  /**
   * Set subsection list.
   *
   * @param subsectionList Subsection list.
   */
  void setSubsection(HBox subsectionList);

  /**
   * Set search panel.
   *
   * @param searchPanel Search panel.
   */
  void setSearchPanel(GridPane searchPanel);

  /**
   * Set body content.
   *
   * @param content Body content
   */
  void setContent(Content content);

  /**
   * Get completed body after build.
   *
   * @return Body.
   */
  Body build();
}
