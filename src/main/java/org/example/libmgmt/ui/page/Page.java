package org.example.libmgmt.ui.page;

import javafx.scene.layout.VBox;
import org.example.libmgmt.ui.components.Header;
import org.example.libmgmt.ui.components.body.Body;

public class Page {
    private final PageType pageType;
    private final Header header;
    private final Body body;
    private final VBox container;

    public Page(PageType pageType, Header header, Body body, VBox container) {
        this.pageType = pageType;
        this.header = header;
        this.body = body;
        this.container = container;
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

    public VBox getContainer() {
        return container;
    }
}
