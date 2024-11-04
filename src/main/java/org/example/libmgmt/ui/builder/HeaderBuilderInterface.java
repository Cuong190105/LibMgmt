package org.example.libmgmt.ui.builder;

import org.example.libmgmt.ui.components.Header;
import org.example.libmgmt.ui.page.PageType;

public interface HeaderBuilderInterface {
    void setType(PageType pageType);
    void setLogo();
    void setControl();
    Header build();
}