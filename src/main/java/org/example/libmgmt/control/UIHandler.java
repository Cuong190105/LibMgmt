
package org.example.libmgmt.control;

import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.director.PageDirector;
import org.example.libmgmt.ui.page.Page;

/**
 * Provides a global access to modify scene.
 */
public class UIHandler {
  private static UIHandler ui;
  private final PageDirector pageDirector;
  private final PageBuilder pageBuilder;
  private Scene scene;
  private Page page;

  private UIHandler() {
    this.pageDirector = new PageDirector();
    this.pageBuilder = new PageBuilder();
  }

  /**
   * Sets up scene.
   *
   * @param stage Window target to add scene.
   */
  public static void start(Stage stage) {
    if (ui == null) {
      ui = new UIHandler();
      ui.pageDirector.createStartupPage(ui.pageBuilder);
      ui.page = ui.pageBuilder.build();
      ui.scene = new Scene(ui.page.getContainer(), 800, 800);
      setVpartition(ui.page.getContainer(), 1);
      stage.setScene(ui.scene);
      ui.scene.getStylesheets()
          .add(Objects.requireNonNull(LibMgmt.class.getResource("control.css"))
          .toExternalForm());
      stage.show();
      Timeline t = new Timeline(new KeyFrame(
          Duration.millis(1000),
          e -> Animation.transitionToLogin(ui.pageDirector, ui.pageBuilder, ui.scene)
      ));
      t.play();
    }
  }
  /**
   * Changes current scene to sign up page.
   */
  public static void switchToSignUp() {
    Animation.transitionToSignup(ui.pageDirector, ui.pageBuilder, ui.scene);
    System.gc();
  }

  /**
   * Displays a popup.
   *
   * @param popup Custom popup displayed.
   */
  public static void addPopup(Popup popup) {
    ui.page.addPopUp(popup);
  }

  /**
   * Changes current scene to login page.
   */
  public static void switchToLogin() {
    Animation.transitionToLogin(ui.pageDirector, ui.pageBuilder, ui.scene);
    System.gc();
  }

  /**
   * Binds a region to a portion of window height.
   *
   * @param region Region need binding.
   * @param portion A portion of window height that the region is going to take.
   */
  public static void setVpartition(Region region, double portion) {
    region.prefHeightProperty().bind(ui.scene.heightProperty().multiply(portion));
  }

  /**
   * Changes current scene to main page.
   */
  public static void gotoMain() {
    ui.pageDirector.createMainPage(ui.pageBuilder);
    ui.scene.setRoot(ui.pageBuilder.build().getContainer());
    System.gc();
  }

  /**
   * Switch between sections in main page.
   *
   * @param bodyType Section to move to.
   */
  public static void switchToSection(BodyType bodyType) {
    ui.pageDirector.createSectionPanel(ui.pageBuilder, bodyType);
    ui.scene.setRoot(ui.pageBuilder.build().getContainer());
    System.gc();
  }

  /**
   * Open a page to display document info.
   *
   * @param doc Document needed displaying info.
   */
  public static void openDocDetail(Document doc) {
    ui.pageDirector.createDocumentDetailPage(ui.pageBuilder, doc);
    ui.scene.setRoot(ui.pageBuilder.build().getContainer());
    System.gc();
  }

  /**
   * Open a page to display user info.
   *
   * @param member User needed displaying info,
   */
  public static void openMemberDetails(User member) {
    ui.pageDirector.createMemberDetailPage(ui.pageBuilder, member);
    ui.scene.setRoot(ui.pageBuilder.build().getContainer());
    System.gc();
  }

  /**
   * Displaying account action panel.
   */
  public static void showAccountAction() {

  }

  /**
   * Displaying notification panel.
   */
  public static void showNotificationPanel() {
  }

  /**
   * Open a page to display checkout info.
   * @param user Borrower
   * @param doc Checkout document.
   */
  public static void openCheckoutPage(User user, Document doc) {
    ui.pageDirector.createCheckoutPage(ui.pageBuilder, user, doc);
    ui.scene.setRoot(ui.pageBuilder.build().getContainer());
    System.gc();
  }
}