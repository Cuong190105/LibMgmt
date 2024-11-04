package org.example.libmgmt.ui.builder;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.components.body.BodyType;

public interface BodyBuilderInterface {
    void setType(BodyType bodyType);
    void setTitle(Text sectionTitle);
    void setContent(VBox content);
    Body build();
}
