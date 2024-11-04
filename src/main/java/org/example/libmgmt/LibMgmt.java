package org.example.libmgmt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.director.PageDirector;
import org.example.libmgmt.ui.page.Page;

import java.io.IOException;

public class LibMgmt extends Application {

    /** Initialize the application. */
    @Override
    public void start(Stage stage) {
        stage.setTitle("LibMa 1.0");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        PageDirector pageDirector = new PageDirector();
        PageBuilder pageBuilder = new PageBuilder();
        pageDirector.createStartupPage(pageBuilder);
        Page p = pageBuilder.build();
        Scene scene = new Scene(p.getContainer());
        p.getContainer().prefHeightProperty().bind(scene.heightProperty());
        stage.setScene(scene);
        stage.show();
    }

    /** Launcher. */
    public static void main(String[] args) {
        launch();
    }
}