package org.example.libmgmt.ui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import org.example.libmgmt.LibMgmt;

import java.io.InputStream;

public class NavBar {
    private Button dashboard;
    private Button request;
    private Button member;
    private Button library;
    private Button feedback;
    private StackPane container;
    private Rectangle activeBtn;

    public NavBar() {
        dashboard = new Button();
        request = new Button();
        member = new Button();
        library = new Button();
        feedback = new Button();
        container = new StackPane(dashboard, request, member, library, feedback);
        activeBtn = new Rectangle();
        loadMedia();
        style();
        setFunction();
    }

    private void setFunction() {
        dashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switchToDashboard();
            }
        });
        request.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switchToRequest();
            }
        });
        member.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switchToMember();
            }
        });
        library.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switchToBook();
            }
        });
        feedback.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switchToFeedback();
            }
        });
    }

    private void loadMedia() {
        final int NUMBER_OF_CATEGORIES = 5;
        final String[] iconName = {"dashboard", "request", "member", "library", "feedback"};
        InputStream[] iconStream = new InputStream[NUMBER_OF_CATEGORIES];
        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            iconStream[i] = LibMgmt.class.getResourceAsStream("img/navbar/" + iconName[i] + ".png");
            if (iconStream[i] != null) {
                switch(i) {
                    case 0 -> {
                        dashboard.setGraphic(new ImageView(new Image(iconStream[i])));
                    }
                    case 1 -> {
                        request.setGraphic(new ImageView(new Image(iconStream[i])));
                    }
                    case 2 -> {
                        member.setGraphic(new ImageView(new Image(iconStream[i])));
                    }
                    case 3 -> {
                        library.setGraphic(new ImageView(new Image(iconStream[i])));
                    }
                    case 4 -> {
                        feedback.setGraphic(new ImageView(new Image(iconStream[i])));
                    }
                }
            }
        }
    }

    private void style() {

    }

    private void switchToDashboard() {

    }

    private void switchToRequest() {

    }

    private void switchToMember() {

    }

    private void switchToBook() {

    }

    private void switchToFeedback() {

    }

    public StackPane getLayout() {
        return container;
    }
}
