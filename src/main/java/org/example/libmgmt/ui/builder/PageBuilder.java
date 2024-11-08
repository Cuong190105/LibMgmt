package org.example.libmgmt.ui.builder;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    private BorderPane container;

    public PageBuilder() {
        container = new BorderPane();
        container.setOnMousePressed(event -> {
            ((Node) event.getSource()).requestFocus();
        });
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
        container.setTop(header.getContainer());
    }

    @Override
    public void setBody(Body body) {
        this.body = body;
        container.setCenter(body.getContainer());
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
                BorderPane.setMargin(header.getContainer(), new Insets(50, 0, 0 ,0));
                BorderPane.setMargin(body.getContainer(), new Insets(50));
            }
            case MAIN_PAGE -> {
                BorderPane.setMargin(header.getContainer(), new Insets(0, 25, 0 ,25));
                BorderPane.setMargin(body.getContainer(), new Insets(0, 25, 25, 25));
            }
        }
    }

    public void bindSize(Scene scene) {
        container.prefHeightProperty().bind(scene.heightProperty());
    }

    @Override
    public Page build() {
        return new Page(type, header, body, container);
    }
}
