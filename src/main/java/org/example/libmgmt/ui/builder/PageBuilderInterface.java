package org.example.libmgmt.ui.builder;

import org.example.libmgmt.ui.components.header.Header;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.page.Page;
import org.example.libmgmt.ui.page.PageType;

public interface PageBuilderInterface {
    void setType(PageType pageType);
    void setHeader(Header header);
    void setBody(Body body);
    Page build();
}
