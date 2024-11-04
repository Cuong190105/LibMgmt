package org.example.libmgmt.ui.builder;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.components.body.BodyType;

public class BodyBuilder implements BodyBuilderInterface, GeneralBuilder {
    private BodyType bodyType;
    private Text sectionTitle;
    private VBox content;
//    private VBox hanger;
//    private ScrollPane contentContainer;
//    private VBox container;

    public BodyBuilder() {
        bodyType = null;
        content = null;
//        hanger = new VBox();
//        contentContainer = new ScrollPane();
//        container = new VBox();
    }

    @Override
    public void reset() {
        bodyType = null;
        content = null;
//        hanger.getChildren().clear();
//        container.getChildren().clear();
    }

    @Override
    public void setType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public void setTitle(Text sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    @Override
    public void setContent(VBox content) {
        this.content = content;
    }

    @Override
    public void style() {

    }

    public Body build() {
        return new Body(bodyType, sectionTitle, content);
    }
}
