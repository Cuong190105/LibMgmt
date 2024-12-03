package org.example.libmgmt.ui.components.header;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.libmgmt.ui.style.Style;

public abstract class QuickPanel {
  protected VBox container;

  public QuickPanel() {
    container = new VBox();
    style();
  }

  private void style() {
    container.setSpacing(5);
    container.setBackground(new Background(new BackgroundFill(
        Style.WHITE, Style.BIG_CORNER, Insets.EMPTY
    )));
    container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    container.setPadding(new Insets(10));
  }

  public VBox getContent() {
    return container;
  }
}
