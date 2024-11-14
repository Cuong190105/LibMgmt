package org.example.libmgmt.control;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.director.PageDirector;
import org.example.libmgmt.ui.page.Page;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class UIHandler {
    private static UIHandler ui;
    private PageDirector pageDirector;
    private PageBuilder pageBuilder;
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
            ui.page = ui.pageBuilder.build();
            ui.scene = new Scene(ui.page.getContainer(), 800, 800);
            setVpartition(ui.page.getContainer(), 1);
            stage.setScene(ui.scene);
            stage.show();
            Timeline t = new Timeline(new KeyFrame(
                    Duration.millis(1000),
                    e -> Animation.transitionToLogin(ui.pageDirector, ui.pageBuilder, ui.scene)
            ));
            t.play();
        }
    }

    public static void switchPage() {
        Animation.transitionToMain(ui.pageDirector, ui.pageBuilder, ui.scene);
    }

    public static void switchToSignUp() {
        Animation.transitionToSignup(ui.pageDirector, ui.pageBuilder, ui.scene);
    }

    public static void switchToDocument() {

    }

    public static void addPopup(Popup popup) {
    }

    public static void switchToLogin() {
        Animation.transitionToLogin(ui.pageDirector, ui.pageBuilder, ui.scene);
    }

    public static void setVpartition(Region region, double portion) {

        region.prefHeightProperty().bind(ui.scene.heightProperty().multiply(portion));
    }

    public static void switchToDashboard() {
        ui.pageDirector.switchToDashboard(ui.pageBuilder);
    }
}