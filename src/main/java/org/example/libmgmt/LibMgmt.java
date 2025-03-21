package org.example.libmgmt;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.libmgmt.control.UIHandler;

/**
 * Application class.
 */
public class LibMgmt extends Application {
  /** Initialize the application. */
  @Override
  public void start(Stage stage) {
    stage.setTitle("LibMa 1.0");
    stage.setMinWidth(615);
    stage.setMinHeight(615);
    AppLogger.setupLogger();
    UIHandler.start(stage);
  }

  /** Launcher. */
  public static void main(String[] args) {
    launch();
  }
}