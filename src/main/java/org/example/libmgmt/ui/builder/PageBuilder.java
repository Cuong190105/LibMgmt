package org.example.libmgmt.ui.builder;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import org.example.libmgmt.ui.components.Header;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.page.Page;
import org.example.libmgmt.ui.page.PageType;

public class PageBuilder implements PageBuilderInterface, GeneralBuilder {
    private PageType type;
    private Header header;
    private Body body;
    private VBox container;

    public PageBuilder() {
        container = new VBox();
        reset();
    }

    public void enableBorder() {
        container.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    @Override
    public void reset() {
        type = null;
        header = null;
        body = null;
        container.getChildren().clear();
//        enableBorder();
    }

    @Override
    public void setType(PageType pageType) {
        this.type = pageType;
    }

    @Override
    public void setHeader(Header header) {
        this.header = header;
        container.getChildren().add(header.getContainer());
    }

    @Override
    public void setBody(Body body) {
        this.body = body;
        container.getChildren().add(body.getContainer());
    }

    public void style() {
        Color BG_YELLOW = Color.rgb(254, 255, 159);
        Color BG_GREEN = Color.rgb(160, 214, 131);
        Stop[] stops = new Stop[] {new Stop(0, BG_YELLOW), new Stop(1, BG_GREEN)};
        LinearGradient linear = new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.NO_CYCLE, stops);
        BackgroundFill bgFill = new BackgroundFill(linear, CornerRadii.EMPTY, Insets.EMPTY);
        Background BG = new Background(bgFill);
        container.setBackground(BG);

        switch (type) {
            case STARTUP_PAGE -> {
                header.getContainer().prefHeightProperty().bind(container.prefHeightProperty());
            }
            case LOGIN_PAGE -> {

            }
            case MAIN_PAGE -> {

            }
        }
    }

    @Override
    public Page build() {
        return new Page(type, header, body, container);
    }
}
