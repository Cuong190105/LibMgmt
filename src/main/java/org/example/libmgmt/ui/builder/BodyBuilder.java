package org.example.libmgmt.ui.builder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.style.Style;

public class BodyBuilder implements BodyBuilderInterface, GeneralBuilder {
    private BodyType bodyType;
    private Label sectionTitle;
    private HBox subsectionList;
    private GridPane searchPanel;
    private ScrollPane content;
    private VBox container;

    public BodyBuilder() {
        reset();
    }

    @Override
    public void reset() {
        bodyType = null;
        content = new ScrollPane();
        subsectionList = new HBox();
        sectionTitle = new Label();
        searchPanel = new GridPane();
        container = new VBox();
    }

    @Override
    public void setType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public void setTitle(String sectionTitle) {
        this.sectionTitle.setText(sectionTitle);
        container.getChildren().add(this.sectionTitle);
    }

    @Override
    public void setSubsection(HBox subsectionList) {
        this.subsectionList = subsectionList;
        container.getChildren().add(subsectionList);
    }

    @Override
    public void setSearchPanel(GridPane searchPanel) {
        this.searchPanel = searchPanel;
        container.getChildren().add(searchPanel);
    }

    @Override
    public void setContent(Parent content) {
        this.content.setContent(content);
        this.container.getChildren().add(this.content);
    }

    @Override
    public void style() {
        Style.styleTitle(sectionTitle, 40);
        VBox.setMargin(sectionTitle, new Insets(25, 25, 0, 25));
        VBox.setMargin(content, new Insets(0, 25, 25, 25));

        content.setFitToWidth(true);
        content.setPrefViewportWidth(Region.USE_PREF_SIZE);
        content.setMaxWidth(Region.USE_PREF_SIZE);
        content.getStylesheets().add(LibMgmt.class.getResource("viewport.css").toExternalForm());

        Style.styleShadowBorder(container);
        BackgroundFill bgF = new BackgroundFill(Color.WHITE, Style.BIG_CORNER, Insets.EMPTY);
        container.setBackground(new Background(bgF));
        container.setSpacing(50);
        switch (bodyType) {
            case LOGIN_FORM:
            case SIGNUP_FORM: {
                container.setSpacing(25);
                container.setAlignment(Pos.CENTER);
                sectionTitle.setAlignment(Pos.CENTER);
                container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                break;
            }
            case MAIN_PANEL: {
            }
        }
    }

    @Override
    public Body build() {
        return new Body(bodyType, sectionTitle, subsectionList, searchPanel,
                content, container);
    }
}