package org.example.libmgmt.ui.components.body.contentSection;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class LibrarianDashboard extends Content implements UpdatableContent{
  private ScrollPane wrapper;
  private VBox container;

  public LibrarianDashboard() {
    super(true);
    container = new VBox();
    wrapper = new ScrollPane(container);
  }

  @Override
  public ScrollPane getContent() {
    return wrapper;
  }

  @Override
  public void update() {
    return;
  }
}
