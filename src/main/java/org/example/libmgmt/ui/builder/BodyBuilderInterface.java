package org.example.libmgmt.ui.builder;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.components.body.BodyType;

public interface BodyBuilderInterface {
    void setType(BodyType bodyType);
    void setTitle(String sectionTitle);
    void setSubsection(HBox subsectionList);
    void setSearchPanel(GridPane searchPanel);
    void setContent(Parent content);
    Body build();
}
