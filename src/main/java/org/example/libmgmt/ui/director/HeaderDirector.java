package org.example.libmgmt.ui.director;

import org.example.libmgmt.ui.builder.HeaderBuilder;
import org.example.libmgmt.ui.page.PageType;

public class HeaderDirector {
    public void createStartupLogo(HeaderBuilder hBuilder) {
        hBuilder.reset();
        hBuilder.setType(PageType.STARTUP_PAGE);
        hBuilder.style();
    }

    public void createLoginLogo(HeaderBuilder hBuilder) {
        hBuilder.reset();
        hBuilder.setType(PageType.LOGIN_PAGE);
        hBuilder.style();
    }

    public void createMainPageHeader(HeaderBuilder hBuilder) {
        hBuilder.reset();
        hBuilder.setType(PageType.MAIN_PAGE);
        hBuilder.setControl();
        hBuilder.style();
    }
}
