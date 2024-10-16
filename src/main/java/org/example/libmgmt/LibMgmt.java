package org.example.libmgmt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import org.example.libmgmt.cli.Main;


import java.io.IOException;

public class LibMgmt extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(true);
        StackPane st = new StackPane();
        Button btn = new Button("Hello JavaFX!");
        st.getChildren().add(btn);
        stage.setTitle("Hello!");
        stage.setScene(new Scene(st, 400, 200));
        stage.show();
    }

    public static void main(String[] args) {

//        launch();
        Main.main();
    }
}