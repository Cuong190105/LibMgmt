package org.example.libmgmt.ui.director;

import org.example.libmgmt.ui.builder.BodyBuilder;
import org.example.libmgmt.ui.builder.HeaderBuilder;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.components.body.Document;
import org.example.libmgmt.ui.page.PageType;

import static org.example.libmgmt.ui.components.body.BodyType.MAIN_DASHBOARD;

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
        if (pageBuilder.getCurrentPageType() != PageType.LOGIN_PAGE) {
            pageBuilder.reset();
            headerDirector.createLoginLogo(headerBuilder);
            pageBuilder.setType(PageType.LOGIN_PAGE);
            pageBuilder.setHeader(headerBuilder.build());
        }
        bodyDirector.createLoginForm(bodyBuilder);
        pageBuilder.setBody(bodyBuilder.build());
        pageBuilder.style();
    }

    public void createSignUpPage(PageBuilder pageBuilder) {
        bodyDirector.createSignUpForm(bodyBuilder);
        pageBuilder.setBody(bodyBuilder.build());
        pageBuilder.style();
    }

    public void createMainPage(PageBuilder pageBuilder) {
        pageBuilder.reset();
        pageBuilder.setType(PageType.MAIN_PAGE);
        headerDirector.createMainPageHeader(headerBuilder);
        pageBuilder.setHeader(headerBuilder.build());
        createSectionPanel(pageBuilder, MAIN_DASHBOARD);
        pageBuilder.style();
    }

    public void createSectionPanel(PageBuilder pageBuilder, BodyType bodyType) {
        bodyDirector.createMainPagePanel(bodyBuilder, bodyType);
        pageBuilder.setBody(bodyBuilder.build());
    }

    public void createDocumentDetailPanel(PageBuilder pageBuilder, Document doc) {
        bodyDirector.createDocumentDetailPanel(bodyBuilder, doc);
        pageBuilder.setBody(bodyBuilder.build());
    }
}
