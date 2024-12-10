package org.example.libmgmt.ui.components.body.contentSection;

import javafx.scene.Parent;
import javafx.scene.layout.Region;

/**
 * An interface for content section.
 */
public abstract class Content {
  private boolean needUpdate;
  /**
   * Get content.
   *
   * @return Content.
   */
  public Content(boolean needUpdate) {
    this.needUpdate = needUpdate;
  }
  public abstract Region getContent();
  public boolean isUpdatable() {
    return needUpdate;
  }
}
