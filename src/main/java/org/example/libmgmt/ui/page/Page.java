package org.example.libmgmt.ui.page;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.components.header.Header;
import org.example.libmgmt.ui.components.body.Body;

public class Page {
    private final PageType pageType;
    private final Header header;
    private final Body body;
    private final BorderPane container;
    private StackPane wrapper;

    public Page(PageType pageType, Header header, Body body, BorderPane container) {
        this.pageType = pageType;
        this.header = header;
        this.body = body;
        this.container = container;
        wrapper = new StackPane(container);
    }

    public PageType getPageType() {
        return pageType;
    }

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }

    public void addPopUp(Popup p) {
        wrapper.getChildren().add(p.getPopup());
        p.linkToPage(this);
    }

    public void closePopUp(Popup p) {
        wrapper.getChildren().remove(p);
    }

    public StackPane getContainer() {
        return wrapper;
    }
}
