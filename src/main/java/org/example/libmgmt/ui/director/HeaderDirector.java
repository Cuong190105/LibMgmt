package org.example.libmgmt.ui.director;

import org.example.libmgmt.ui.builder.HeaderBuilder;
import org.example.libmgmt.ui.page.PageType;

public class HeaderDirector {
    public static final int LARGE_SIZE = 400;
    public static final int SMALL_SIZE = 200;
    public void createStartupLogo(HeaderBuilder hBuilder) {
        hBuilder.reset();
        hBuilder.setType(PageType.STARTUP_PAGE);
        hBuilder.setLogo();
        hBuilder.style();
    }

    public void createLoginLogo(HeaderBuilder hBuilder) {
        hBuilder.reset();
        hBuilder.setType(PageType.LOGIN_PAGE);
        hBuilder.setLogo();
        hBuilder.style();
    }

    public void setMainpageHeader(HeaderBuilder hBuilder) {
        hBuilder.reset();
        hBuilder.setType(PageType.MAIN_PAGE);
        hBuilder.setLogo();
        hBuilder.setControl();
        hBuilder.style();
    }
}
