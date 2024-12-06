package org.example.libmgmt.control;

import java.util.Objects;
import java.util.Stack;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.components.body.contentSection.AccountInfoPanel;
import org.example.libmgmt.ui.components.header.AccountPanel;
import org.example.libmgmt.ui.director.PageDirector;
import org.example.libmgmt.ui.page.Page;

/**
 * Provides a global access to modify scene.
 */
public class UIHandler {
  private static UIHandler ui;
  private final PageDirector pageDirector;
  private final PageBuilder pageBuilder;
  private final Stage stage;
  private final Stack<Page> pageStack;
  private Scene scene;

  private UIHandler(Stage stage) {
    this.pageStack = new Stack<>();
    this.pageDirector = new PageDirector();
    this.pageBuilder = new PageBuilder();
    this.stage = stage;
  }

  /**
   * Sets up scene.
   *
   * @param stage Window target to add scene.
   */
  public static void start(Stage stage) {
    if (ui == null) {
      ui = new UIHandler(stage);
      ui.pageDirector.createStartupPage(ui.pageBuilder);
      ui.pageStack.push(ui.pageBuilder.build());
      ui.scene = new Scene(ui.pageStack.peek().getContainer(), 800, 800);
      setVpartition(ui.pageStack.peek().getContainer(), 1);
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
    openDocumentReader(new Document());
  }

  /**
   * Changes current scene to sign up page.
   */
  public static void switchToSignUp() {
    Animation.transitionToSignup(ui.pageDirector, ui.pageBuilder, ui.scene);
  }

  /**
   * Displays a popup.
   *
   * @param popup Custom popup displayed.
   */
  public static void addPopup(Popup popup) {
    ui.pageStack.peek().addPopUp(popup);
  }

  /**
   * Changes current scene to login page.
   */
  public static void switchToLogin() {
    Animation.transitionToLogin(ui.pageDirector, ui.pageBuilder, ui.scene);
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
    ui.pageStack.clear();
    ui.pageDirector.createMainPage(ui.pageBuilder);
    ui.pageStack.push(ui.pageBuilder.build());
    ui.scene.setRoot(ui.pageStack.peek().getContainer());
  }

  /**
   * Switch between sections in main page.
   *
   * @param bodyType Section to move to.
   */
  public static void switchToSection(BodyType bodyType) {
    ui.pageStack.clear();
    ui.pageDirector.createSectionPanel(ui.pageBuilder, bodyType);
    ui.pageStack.push(ui.pageBuilder.build());
    ui.scene.setRoot(ui.pageStack.peek().getContainer());
  }

  /**
   * Open a page to display document info.
   *
   * @param doc Document needed displaying info.
   */
  public static void openDocDetail(Document doc) {
    ui.pageDirector.createDocumentDetailPage(ui.pageBuilder, doc);
    ui.pageStack.push(ui.pageBuilder.build());
    ui.scene.setRoot(ui.pageStack.peek().getContainer());
  }

  /**
   * Open a page to display user info.
   *
   * @param member User needed displaying info,
   */
  public static void openMemberDetails(User member) {
    ui.pageDirector.createMemberDetailPage(ui.pageBuilder, member);
    ui.pageStack.push(ui.pageBuilder.build());
    ui.scene.setRoot(ui.pageStack.peek().getContainer());
  }

  /**
   * Displaying account action panel.
   */
  public static void showAccountAction() {
    ui.pageStack.peek().addQuickPanel(new AccountPanel());
  }

  /**
   * Displaying notification panel.
   */
  public static void showNotificationPanel() {
  }

  /**
   * Open a page to display checkout info.
   *
   * @param user Borrower
   * @param doc Checkout document.
   */
  public static void openCheckoutPage(User user, Document doc) {
    ui.pageDirector.createCheckoutPage(ui.pageBuilder, user, doc);
    ui.pageStack.push(ui.pageBuilder.build());
    ui.scene.setRoot(ui.pageStack.peek().getContainer());
  }

  public static Window getStage() {
    return ui.stage;
  }

  /**
   * Open document edit panel.
   *
   * @param doc Document needs editing. Set to null to create a new document.
   */
  public static void openDocumentEditPanel(Document doc) {
    ui.pageDirector.createDocumentEditPage(ui.pageBuilder, doc);
    ui.pageStack.push(ui.pageBuilder.build());
    ui.scene.setRoot(ui.pageStack.peek().getContainer());
  }

  /**
   * Open add document panel.
   */
  public static void openAddDocumentPanel() {
    ui.pageDirector.createAddDocumentPage(ui.pageBuilder);
    ui.pageStack.push(ui.pageBuilder.build());
    ui.scene.setRoot(ui.pageStack.peek().getContainer());
  }

  /**
   * Open account detail panel.
   */
  public static void openAccountDetails() {
  }

  /**
   * Open account quick panel.
   */
  public static void openAccountPanel() {
    ui.pageStack.peek().addQuickPanel(new AccountPanel());
  }

  public static void openDocumentReader(Document doc) {
    Stage reader = new Stage();
    reader.setTitle("Trình đọc PDF LibMa - " + doc.getTitle());
    reader.initModality(Modality.NONE);
    PageBuilder temp = new PageBuilder();
    ui.pageDirector.createPdfViewer(temp, doc);
    Scene readerScene = new Scene(temp.build().getContainer(), 800, 800);
    reader.setScene(readerScene);
    reader.setMinWidth(800);
    reader.setMinHeight(800);
    reader.show();
  }

  public static void backToLastPage() {
    ui.pageStack.pop();
    ui.scene.setRoot(ui.pageStack.peek().getContainer());
  }
}