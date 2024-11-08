package org.example.libmgmt.ui.components.body;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Body {
    private final BodyType bodyType;
    private final Label sectionTitle;
    private final HBox subsectionList;
    private final GridPane searchPanel;
    private final VBox content;
    private final VBox container;

    public Body(BodyType bodyType, Label sectionTitle, HBox subsectionList, GridPane searchPanel, VBox content, VBox container) {
        this.bodyType = bodyType;
        this.sectionTitle = sectionTitle;
        this.subsectionList = subsectionList;
        this.searchPanel = searchPanel;
        this.content = content;
        this.container = container;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public Label getSectionTitle() {
        return sectionTitle;
    }

    public HBox getSubsectionList() {
        return subsectionList;
    }

    public GridPane getSearchPanel() {
        return searchPanel;
    }

    public VBox getContent() {
        return content;
    }

    public VBox getContainer() {
        return container;
    }
}