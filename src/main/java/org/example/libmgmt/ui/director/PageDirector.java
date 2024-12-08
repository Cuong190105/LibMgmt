package org.example.libmgmt.ui.director;

import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.ui.builder.BodyBuilder;
import org.example.libmgmt.ui.builder.HeaderBuilder;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.components.body.contentSection.PDFViewer;
import org.example.libmgmt.ui.page.PageType;

/**
 * A director which tells builder to build pages.
 */
public class PageDirector {
  private final HeaderBuilder headerBuilder;
  private final HeaderDirector headerDirector;
  private final BodyBuilder bodyBuilder;
  private final BodyDirector bodyDirector;

  /**
   * Constructor.
   */
  public PageDirector() {
    this.headerBuilder = new HeaderBuilder();
    this.headerDirector = new HeaderDirector();
    this.bodyBuilder = new BodyBuilder();
    this.bodyDirector = new BodyDirector();
  }

  /**
   * Create startup page.
   *
   * @param pageBuilder Builder
   */
  public void createStartupPage(PageBuilder pageBuilder) {
    pageBuilder.reset();
    headerDirector.createStartupLogo(headerBuilder);
    pageBuilder.setType(PageType.STARTUP_PAGE);
    pageBuilder.setHeader(headerBuilder.build());
    pageBuilder.style();
  }

  /**
   * Create login page.
   *
   * @param pageBuilder Builder
   */
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

  /**
   * Create sign-up page.
   *
   * @param pageBuilder Builder
   */
  public void createSignUpPage(PageBuilder pageBuilder) {
    bodyDirector.createSignUpForm(bodyBuilder);
    pageBuilder.setBody(bodyBuilder.build());
    pageBuilder.style();
  }

  /**
   * Create main page.
   *
   * @param pageBuilder Builder
   */
  public void createMainPage(PageBuilder pageBuilder) {
    headerDirector.createMainPageHeader(headerBuilder);
    createSectionPanel(pageBuilder, BodyType.MAIN_DASHBOARD);
  }

  /**
   * Creates a new body for new section.
   *
   * @param pageBuilder Builder
   */
  public void createSectionPanel(PageBuilder pageBuilder, BodyType bodyType) {
    pageBuilder.reset();
    pageBuilder.setType(PageType.MAIN_PAGE);
    pageBuilder.setHeader(headerBuilder.build());
    bodyDirector.createMainPagePanel(bodyBuilder, bodyType);
    pageBuilder.setBody(bodyBuilder.build());
    pageBuilder.style();
  }

  /**
   * Create a document detail page.
   *
   * @param pageBuilder Builder
   */
  public void createDocumentDetailPage(PageBuilder pageBuilder, Document doc) {
    pageBuilder.reset();
    pageBuilder.setType(PageType.MAIN_PAGE);
    pageBuilder.setHeader(headerBuilder.build());
    bodyDirector.createDocumentDetailPanel(bodyBuilder, doc);
    pageBuilder.setBody(bodyBuilder.build());
    pageBuilder.style();
  }

  /**
   * Create a member detail page.
   *
   * @param pageBuilder Builder
   */
  public void createMemberDetailPage(PageBuilder pageBuilder, User member) {
    pageBuilder.reset();
    pageBuilder.setType(PageType.MAIN_PAGE);
    pageBuilder.setHeader(headerBuilder.build());
    bodyDirector.createMemberDetailPanel(bodyBuilder, member);
    pageBuilder.setBody(bodyBuilder.build());
    pageBuilder.style();
  }

  /**
   * Create a checkout detail page.
   *
   * @param pageBuilder Builder
   */
  public void createCheckoutPage(PageBuilder pageBuilder, User user, Document doc) {
    pageBuilder.reset();
    pageBuilder.setType(PageType.MAIN_PAGE);
    pageBuilder.setHeader(headerBuilder.build());
    bodyDirector.createCheckoutPanel(bodyBuilder, user, doc);
    pageBuilder.setBody(bodyBuilder.build());
    pageBuilder.style();
  }

  public void createDocumentEditPage(PageBuilder pageBuilder, Document doc) {
    pageBuilder.reset();
    pageBuilder.setType(PageType.MAIN_PAGE);
    pageBuilder.setHeader(headerBuilder.build());
    bodyDirector.createDocumentEditPanel(bodyBuilder, doc);
    pageBuilder.setBody(bodyBuilder.build());
    pageBuilder.style();
  }

  public void createAddDocumentPage(PageBuilder pageBuilder) {
    pageBuilder.reset();
    pageBuilder.setType(PageType.MAIN_PAGE);
    pageBuilder.setHeader(headerBuilder.build());
    bodyDirector.createAddDocumentPanel(bodyBuilder);
    pageBuilder.setBody(bodyBuilder.build());
    pageBuilder.style();
  }

  public void createPdfViewer(PageBuilder pageBuilder, Document doc) {
    pageBuilder.reset();
    pageBuilder.setType(PageType.PDF_VIEWER);
    PDFViewer viewer = new PDFViewer(doc);
    bodyDirector.createPdfViewer(bodyBuilder, viewer);
    headerDirector.createPdfController(headerBuilder, viewer);
    pageBuilder.setHeader(headerBuilder.build());
    pageBuilder.setBody(bodyBuilder.build());
    pageBuilder.style();
  }

  public void createAddMemberPage(PageBuilder pageBuilder) {
    pageBuilder.reset();
    pageBuilder.setType(PageType.MAIN_PAGE);
    pageBuilder.setHeader(headerBuilder.build());
    bodyDirector.createAddMemberPanel(bodyBuilder);
    pageBuilder.setBody(bodyBuilder.build());
    pageBuilder.style();
  }

  public void createChangeAccountInfo(PageBuilder pageBuilder) {
    pageBuilder.reset();
    pageBuilder.setType(PageType.MAIN_PAGE);
    pageBuilder.setHeader(headerBuilder.build());
    bodyDirector.createChangePwdPanel(bodyBuilder);
    pageBuilder.setBody(bodyBuilder.build());
    pageBuilder.style();
  }
}
