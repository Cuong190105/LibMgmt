package org.example.libmgmt.ui.director;

import org.example.libmgmt.ui.builder.BodyBuilder;
import org.example.libmgmt.ui.builder.HeaderBuilder;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.page.PageType;

public class PageDirector {
    private HeaderBuilder headerBuilder;
    private HeaderDirector headerDirector;
    private BodyBuilder bodyBuilder;
    private BodyDirector bodyDirector;

    public PageDirector() {
        this.headerBuilder = new HeaderBuilder();
        this.headerDirector = new HeaderDirector();
        this.bodyBuilder = new BodyBuilder();
        this.bodyDirector = new BodyDirector();
    }

    public void createStartupPage(PageBuilder pageBuilder) {
        pageBuilder.reset();
        headerDirector.createStartupLogo(headerBuilder);
        pageBuilder.setType(PageType.STARTUP_PAGE);
        pageBuilder.setHeader(headerBuilder.build());
        pageBuilder.style();
    }

    public void createLoginPage(PageBuilder pageBuilder) {
        pageBuilder.reset();
        headerDirector.createLoginLogo(headerBuilder);
        bodyDirector.createLoginForm(bodyBuilder);
        pageBuilder.setType(PageType.LOGIN_PAGE);
        pageBuilder.setHeader(headerBuilder.build());
        pageBuilder.setBody(bodyBuilder.build());
        pageBuilder.style();
    }
}
