package org.example.libmgmt.control;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.ui.director.PageDirector;
import org.example.libmgmt.ui.page.Page;

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

    public static void start(Stage stage) {
        if (ui == null) {
            ui = new UIHandler();
            ui.pageDirector.createStartupPage(ui.pageBuilder);
//            switchToSection(BodyType.MAIN_MEMBER);
            ui.page = ui.pageBuilder.build();
            ui.scene = new Scene(ui.page.getContainer(), 800, 800);
            setVpartition(ui.page.getContainer(), 1);
            stage.setScene(ui.scene);
            ui.scene.getStylesheets().add(LibMgmt.class.getResource("button.css").toExternalForm());
            stage.show();
            Timeline t = new Timeline(new KeyFrame(
                    Duration.millis(1000),
                    _ -> Animation.transitionToLogin(ui.pageDirector, ui.pageBuilder, ui.scene)
            ));
            t.play();
        }
    }

    public static void switchToSignUp() {
        Animation.transitionToSignup(ui.pageDirector, ui.pageBuilder, ui.scene);
    }

    public static void addPopup(Popup popup) {
        ui.page.addPopUp(popup);
    }

    public static void switchToLogin() {
        Animation.transitionToLogin(ui.pageDirector, ui.pageBuilder, ui.scene);
    }

    public static void setVpartition(Region region, double portion) {

        region.prefHeightProperty().bind(ui.scene.heightProperty().multiply(portion));
    }

    public static void gotoMain() {
        ui.pageDirector.createMainPage(ui.pageBuilder);
    }

    public static void switchToSection(BodyType bodyType) {
        ui.pageDirector.createSectionPanel(ui.pageBuilder, bodyType);
    }

    public static void openDocDetail(Document doc) {
        ui.pageDirector.createDocumentDetailPage(ui.pageBuilder, doc);
    }

    public static void openMemberDetails(User member) {
        ui.pageDirector.createMemberDetailPage(ui.pageBuilder, member);
    }

    public static void showAccountAction() {

    }

    public static void showNotificationPanel() {
    }
}