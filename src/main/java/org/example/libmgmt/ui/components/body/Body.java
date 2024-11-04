package org.example.libmgmt.ui.components.body;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Body {
    private BodyType type;
    private Text sectionTitle;
    private VBox content;
    private StackPane container;

    public Body(BodyType type, Text sectionTitle, VBox content) {
        this.type = type;
        this.sectionTitle = sectionTitle;
        this.content = content;
        this.container = new StackPane();
        styleBody();
    }

    private void styleBody() {

    }

    public BodyType getBodyType() {
        return type;
    }

    public Text getSectionTitle() {
        return sectionTitle;
    }

    public VBox getContent() {
        return content;
    }

    public StackPane getContainer() {
        return container;
    }
}