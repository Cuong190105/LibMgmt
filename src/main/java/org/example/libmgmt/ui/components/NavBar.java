package org.example.libmgmt.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleNavBar;

import java.io.InputStream;

public class NavBar {
    private Button dashboard;
    private Button request;
    private Button member;
    private Button library;
    private Button feedback;
    private HBox btnRack;
    private Rectangle activeBtn;
    private Pane btnOverlay;
    private StackPane container;

    public NavBar() {
        dashboard = new Button();
        request = new Button();
        member = new Button();
        library = new Button();
        feedback = new Button();
        btnRack = new HBox(dashboard, request, member, library, feedback);
        activeBtn = new Rectangle();
        btnOverlay = new Pane(activeBtn);
        container = new StackPane(btnOverlay, btnRack);
        loadMedia();
        style();
        setFunction();
        switchToDashboard();
    }

    private void setFunction() {
        dashboard.setOnAction(_ -> switchToDashboard());
        request.setOnAction(_ -> switchToRequest());
        member.setOnAction(_ -> switchToMember());
        library.setOnAction(_ -> switchToLibrary());
        feedback.setOnAction(_ -> switchToFeedback());
    }

    private void loadMedia() {
        final int NUMBER_OF_CATEGORIES = 5;
        final String[] iconName = {"dashboard", "request", "member", "library", "feedback"};
        InputStream[] iconStream = new InputStream[NUMBER_OF_CATEGORIES];
        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            iconStream[i] = LibMgmt.class.getResourceAsStream("img/navbar/" + iconName[i] + ".png");
            if (iconStream[i] != null) {
                ImageView img = new ImageView(new Image(iconStream[i]));
                img.setPreserveRatio(true);
                img.setFitHeight(25);
                switch(i) {
                    case 0 -> {
                        dashboard.setGraphic(img);
                    }
                    case 1 -> {
                        request.setGraphic(img);
                    }
                    case 2 -> {
                        member.setGraphic(img);
                    }
                    case 3 -> {
                        library.setGraphic(img);
                    }
                    case 4 -> {
                        feedback.setGraphic(img);
                    }
                }
            } else {
                System.out.println("Icon not found");
            }
        }
    }

    private void style() {
        StyleNavBar.styleButton(dashboard);
        StyleNavBar.styleButton(request);
        StyleNavBar.styleButton(member);
        StyleNavBar.styleButton(library);
        StyleNavBar.styleButton(feedback);

        btnOverlay.prefHeightProperty().bind(container.heightProperty());
        btnOverlay.prefWidthProperty().bind(container.widthProperty());

        activeBtn.xProperty().bind(dashboard.layoutXProperty());
        activeBtn.yProperty().bind(dashboard.layoutYProperty());
        activeBtn.widthProperty().bind(dashboard.widthProperty());
        activeBtn.setHeight(50);
        activeBtn.setArcWidth(30);
        activeBtn.setArcHeight(30);
        activeBtn.setFill(Style.LIGHTGREEN);

        BackgroundFill bgF = new BackgroundFill(Style.DARKGREEN, Style.SMALL_CORNER, Insets.EMPTY);
        container.setBackground(new Background(bgF));
        Style.styleShadowBorder(container);
        BorderPane.setMargin(container, new Insets(0, 50, 0, 50));
        container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        container.setAlignment(Pos.CENTER_LEFT);
//        Style.setDebugBorder(container);
    }

    private void switchToDashboard() {
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(dashboard.layoutXProperty());
    }

    private void switchToRequest() {
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(request.layoutXProperty());
    }

    private void switchToMember() {
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(member.layoutXProperty());
    }

    private void switchToLibrary() {
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(library.layoutXProperty());
    }

    private void switchToFeedback() {
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(feedback.layoutXProperty());
    }

    public StackPane getLayout() {
        return container;
    }
}
