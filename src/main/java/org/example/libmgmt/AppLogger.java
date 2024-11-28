package org.example.libmgmt;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A global logger for the app.
 */
public class AppLogger {
  private static FileHandler logFile;
  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  /**
   * Sets up logger on startup.
   */
  public static void setupLogger() {
    if (logFile != null) {
      System.out.println("Log file not null");
      return;
    }
    try {
      logFile = new FileHandler("./activities.log");
      SimpleFormatter formatter = new SimpleFormatter();
      logFile.setFormatter(formatter);
      LOGGER.addHandler(logFile);
    } catch (IOException e) {
      Stage warning = new Stage();
      warning.setTitle("Log not created");
      warning.initModality(Modality.NONE);
      Text msg = new Text("Failed to create log file. "
          + "Any important information will not be recorded during this session.");
      Button ok = new Button("OK");
      ok.setOnMouseClicked(_ -> warning.close());
      VBox dialog = new VBox(msg, ok);
      Scene scene = new Scene(dialog);
      warning.setScene(scene);
      warning.show();
    }
  }

  /**
   * Creates an informative log for any important status.
   *
   * @param msg Custon message that need logging.
   */
  public static void log(String msg) {
    LOGGER.log(Level.INFO, msg);
  }

  /**
   * Creates a log for any exception.
   *
   * @param e Exception need logging.
   */
  public static void log(Throwable e) {
    LOGGER.log(Level.SEVERE, e.toString(), e);
  }
}
