package org.example.libmgmt.ui.builder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.style.Style;

public class BodyBuilder implements BodyBuilderInterface, GeneralBuilder {
    private BodyType bodyType;
    private Label sectionTitle;
    private HBox subsectionList;
    private GridPane searchPanel;
    private VBox content;
    private VBox container;

    public BodyBuilder() {
        bodyType = null;
        content = new VBox();
        subsectionList = new HBox();
        sectionTitle = new Label();
        searchPanel = new GridPane();
        container = new VBox();
    }

    @Override
    public void reset() {
        bodyType = null;
        subsectionList.getChildren().clear();
        content.getChildren().clear();
        sectionTitle.setText("");
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
    public void setContent(VBox content) {
        this.content = content;
        this.container.getChildren().add(content);
    }

    @Override
    public void style() {
        container.setSpacing(50);
        BackgroundFill bgF = new BackgroundFill(Color.WHITE, Style.BIG_CORNER, Insets.EMPTY);
        container.setBackground(new Background(bgF));
        Style.styleShadowBorder(container);

        Style.setDebugBorder(container);

        VBox.setMargin(sectionTitle, new Insets(25, 25, 0, 25));
        VBox.setMargin(content, new Insets(0, 25, 25, 25));
        Style.styleTitle(sectionTitle, 40);
        switch (bodyType) {
            case LOGIN_FORM -> {
                container.setSpacing(25);
                container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                container.setAlignment(Pos.CENTER);
                sectionTitle.setAlignment(Pos.CENTER);
            }
            case MAIN_PANEL -> {
            }
        }
    }

    @Override
    public Body build() {
        return new Body(bodyType, sectionTitle, subsectionList, searchPanel,
                content, container);
    }
}
