package org.example.libmgmt.ui.components.body;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Body {
    private final BodyType bodyType;
    private final Label sectionTitle;
    private final HBox subsectionList;
    private final GridPane searchPanel;
    private final ScrollPane content;
    private final VBox container;

    public Body(BodyType bodyType, Label sectionTitle, HBox subsectionList, GridPane searchPanel, ScrollPane content, VBox container) {
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

    public ScrollPane getContent() {
        return content;
    }

    /** Get body content. */
    public VBox getContainer() {
        return container;
    }
}