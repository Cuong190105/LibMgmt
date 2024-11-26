package org.example.libmgmt.ui.components.header;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleNavBar;

import java.io.InputStream;

public class NavBar {
    private Button dashboard;
    private Button request;
    private Button member;
    private Button docSearch;
    private Button bookshelf;
    private Button library;
    private Button feedback;
    private HBox btnRack;
    private Rectangle activeBtn;
    private Pane btnOverlay;
    private StackPane container;

    public NavBar() {
        btnRack = new HBox();
        dashboard = new Button();
        feedback = new Button();
        if (UserControl.getUser().isLibrarian()) {
            request = new Button();
            member = new Button();
            library = new Button();
            btnRack.getChildren().addAll(dashboard, request, library, member, feedback);
        } else {
            docSearch = new Button();
            bookshelf = new Button();
            library = new Button();
            btnRack.getChildren().addAll(dashboard, library, docSearch, bookshelf, feedback);
        }
        activeBtn = new Rectangle();
        btnOverlay = new Pane(activeBtn);
        container = new StackPane(btnOverlay, btnRack);
        loadMedia();
        style();
        setFunction();
        switchToDashboard();
    }

    private void setFunction() {
        dashboard.setOnMouseClicked(_ -> switchToDashboard());
        dashboard.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                switchToDashboard();
            }
        });

        library.setOnMouseClicked(_ -> switchToLibrary());
        library.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                switchToLibrary();
            }
        });

        feedback.setOnMouseClicked(_ -> switchToFeedback());
        feedback.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                switchToFeedback();
            }
        });
        if (UserControl.getUser().isLibrarian()) {
            request.setOnMouseClicked(_ -> switchToRequest());
            request.setOnKeyPressed(key -> {
                if (key.getCode().equals(KeyCode.ENTER)) {
                    switchToRequest();
                }
            });

            member.setOnMouseClicked(_ -> switchToMember());
            member.setOnKeyPressed(key -> {
                if (key.getCode().equals(KeyCode.ENTER)) {
                    switchToMember();
                }
            });
        } else {
            bookshelf.setOnMouseClicked(_ -> switchToBookshelf());
            bookshelf.setOnKeyPressed(key -> {
                if (key.getCode().equals(KeyCode.ENTER)) {
                    switchToBookshelf();
                }
            });

            docSearch.setOnMouseClicked(_ -> switchToDocSearch());
            docSearch.setOnKeyPressed(key -> {
                if (key.getCode().equals(KeyCode.ENTER)) {
                    switchToDocSearch();
                }
            });
        }
    }

    private void loadMedia() {
        final int NUMBER_OF_CATEGORIES = 5;
        String[] iconName = {"dashboard", "request", "library", "member", "feedback"};
        if (!UserControl.getUser().isLibrarian()) {
            iconName[1] = "library";
            iconName[2] = "docSearch";
            iconName[3] = "bookshelf";
        }
        InputStream[] iconStream = new InputStream[NUMBER_OF_CATEGORIES];
        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            iconStream[i] = LibMgmt.class.getResourceAsStream("img/navbar/" + iconName[i] + ".png");
            if (iconStream[i] != null) {
                ImageView img = new ImageView(new Image(iconStream[i]));
                img.setPreserveRatio(true);
                img.setFitHeight(25);
                switch(i) {
                    case 0 -> dashboard.setGraphic(img);
                    case 1 -> {
                        if (request != null) {
                            request.setGraphic(img);
                        } else {
                            library.setGraphic(img);
                        }
                    }
                    case 2 -> {
                        if (docSearch != null) {
                            docSearch.setGraphic(img);
                        } else {
                            library.setGraphic(img);
                        }
                    }
                    case 3 -> {
                        if (member != null) {
                            member.setGraphic(img);
                        } else {
                            bookshelf.setGraphic(img);
                        }
                    }
                    case 4 -> feedback.setGraphic(img);
                }
            } else {
                System.out.println("Icon not found");
            }
        }
    }

    private void style() {
        StyleNavBar.styleButton(dashboard);
        StyleNavBar.styleButton(feedback);
        StyleNavBar.styleButton(library);
        if (UserControl.getUser().isLibrarian()) {
            StyleNavBar.styleButton(request);
            StyleNavBar.styleButton(member);
        } else {
            StyleNavBar.styleButton(bookshelf);
            StyleNavBar.styleButton(docSearch);
        }

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

    private void  switchToDashboard() {
        container.requestFocus();
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(dashboard.layoutXProperty());
        UIHandler.switchToSection(BodyType.MAIN_DASHBOARD);
    }

    private void switchToRequest() {
        container.requestFocus();
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(request.layoutXProperty());
        UIHandler.switchToSection(BodyType.MAIN_REQUEST);
    }

    private void switchToBookshelf() {
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(bookshelf.layoutXProperty());
        UIHandler.switchToSection(BodyType.MAIN_MEMBER);
        container.requestFocus();
    }

    private void switchToMember() {
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(member.layoutXProperty());
        UIHandler.switchToSection(BodyType.MAIN_MEMBER);
        container.requestFocus();
    }

    private void switchToLibrary() {
        container.requestFocus();
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(library.layoutXProperty());
        UIHandler.switchToSection(BodyType.MAIN_LIBRARY);
    }

    private void switchToDocSearch() {
        container.requestFocus();
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(docSearch.layoutXProperty());
        UIHandler.switchToSection(BodyType.MAIN_LIBRARY);
    }

    private void switchToFeedback() {
        container.requestFocus();
        activeBtn.xProperty().unbind();
        activeBtn.xProperty().bind(feedback.layoutXProperty());
        UIHandler.switchToSection(BodyType.MAIN_FEEDBACK);
    }

    public StackPane getLayout() {
        return container;
    }
}
