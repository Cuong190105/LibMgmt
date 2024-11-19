package org.example.libmgmt.ui.builder;

import org.example.libmgmt.ui.components.header.Header;
import org.example.libmgmt.ui.page.PageType;

public interface HeaderBuilderInterface {
    void setType(PageType pageType);
    void setControl();
    Header build();
}