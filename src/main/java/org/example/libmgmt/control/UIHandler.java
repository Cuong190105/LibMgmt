package org.example.libmgmt.control;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.director.PageDirector;

public class UIHandler {
    private static UIHandler ui;
    private PageDirector pageDirector;
    private PageBuilder pageBuilder;
    private Scene scene;

    private UIHandler() {
        this.pageDirector = new PageDirector();
        this.pageBuilder = new PageBuilder();
    }

    public static void start(Stage stage) {
        if (ui == null) {
            ui = new UIHandler();
            ui.pageDirector.createStartupPage(ui.pageBuilder);
            ui.scene = new Scene(ui.pageBuilder.build().getContainer());
            ui.pageBuilder.bindSize(ui.scene);
            stage.setScene(ui.scene);
            stage.show();
            Timeline t = new Timeline(new KeyFrame(
                    Duration.millis(1000),
                    e -> {
                        Animation.transitionToLogin(ui.pageDirector, ui.pageBuilder, ui.scene);
                    }
            ));
            t.play();
        }
    }

    public static void switchPage() {
        Animation.transitionToMain(ui.pageDirector, ui.pageBuilder, ui.scene);
    }
}
