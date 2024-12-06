package org.example.libmgmt.ui.builder;

import org.example.libmgmt.ui.components.header.Header;
import org.example.libmgmt.ui.components.header.HeaderController;
import org.example.libmgmt.ui.page.PageType;

/**
 * An interface for building header.
 */
public interface HeaderBuilderInterface {
  /**
   * Declares page type.
   *
   * @param pageType Page type.
   */
  void setType(PageType pageType);

  /**
   * Set control bar (navigation/functional tool bar).
   *
   * @param headerController Set controller.
   */
  void setControl(HeaderController headerController);

  /**
   * Build header.
   *
   * @return Header.
   */
  Header build();
}