package org.example.libmgmt.ui.components.header;

import javafx.scene.layout.Region;

/**
 * An interface for controller ribbons in header.
 */
public interface HeaderController {
  /**
   * Set function for ribbon elements.
   */
  void setFunction();

  /**
   * Style the ribbon.
   */
  void style();

  /**
   * Get ribbon.
   *
   * @return Ribbon layout.
   */
  Region getLayout();
}
