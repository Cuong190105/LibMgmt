package org.example.libmgmt.ui.builder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.style.Style;

public class BodyBuilder implements BodyBuilderInterface, GeneralBuilder {
    private BodyType bodyType;
    private Label sectionTitle;
    private HBox subsectionList;
    private GridPane searchPanel;
    private Region content;
    private VBox container;

    public BodyBuilder() {
        container = new VBox();
        reset();
    }

    @Override
    public void reset() {
        bodyType = null;
        content = new ScrollPane();
        subsectionList = new HBox();
        sectionTitle = new Label();
        searchPanel = new GridPane();
        container.getChildren().clear();
    }

    @Override
    public void setType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public void setTitle(String sectionTitle) {
        this.sectionTitle.setText(sectionTitle.toUpperCase());
    }

    @Override
    public void setSubsection(HBox subsectionList) {
        this.subsectionList = subsectionList;
    }

    @Override
    public void setSearchPanel(GridPane searchPanel) {
        this.searchPanel = searchPanel;
    }

    @Override
    public void setContent(Region content) {
        this.content = content;
    }

    @Override
    public void style() {
        Style.styleTitle(sectionTitle, 40);
        VBox.setMargin(sectionTitle, new Insets(25, 25, 0, 25));
        VBox.setMargin(content, new Insets(0, 25, 25, 25));

//        content.setPrefViewportWidth(Region.USE_PREF_SIZE);
        content.getStylesheets().add(LibMgmt.class.getResource("viewport.css").toExternalForm());
        VBox.setVgrow(content, Priority.ALWAYS);

        Style.styleShadowBorder(container);
        BackgroundFill bgF = new BackgroundFill(Color.WHITE, Style.BIG_CORNER, Insets.EMPTY);
        container.setBackground(new Background(bgF));
//        container.setSpacing(10);
        if (content != null) {
            content.setPadding(new Insets(10, 0, 10, 0));
        }
        switch (bodyType) {
            case AUTHENTICATION -> {
                content.setMaxWidth(Region.USE_PREF_SIZE);
                container.setAlignment(Pos.CENTER);
                sectionTitle.setAlignment(Pos.CENTER);
                container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            }
            case MAIN -> {
                content.prefWidthProperty().bind(container.widthProperty());
                container.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                container.setAlignment(Pos.TOP_LEFT);
//                Style.setDebugBorder(content);
            }
        }
    }

    @Override
    public Body build() {
        container.getChildren().add(sectionTitle);
        if (!subsectionList.getChildren().isEmpty()) {
            container.getChildren().add(subsectionList);
        }
        if (!searchPanel.getChildren().isEmpty()) {
            container.getChildren().add(searchPanel);
        }
        container.getChildren().add(content);

        return new Body(bodyType, sectionTitle, subsectionList, searchPanel,
                content, container);
    }
}