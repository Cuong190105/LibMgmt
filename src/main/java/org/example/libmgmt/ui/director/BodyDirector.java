package org.example.libmgmt.ui.director;

import org.example.libmgmt.DB.Account;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.builder.BodyBuilder;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.components.body.contentSection.AccountInfoPanel;
import org.example.libmgmt.ui.components.body.contentSection.BorrowHistoryPanel;
import org.example.libmgmt.ui.components.body.contentSection.CheckoutPanel;
import org.example.libmgmt.ui.components.body.contentSection.DocumentDetails;
import org.example.libmgmt.ui.components.body.contentSection.DocumentEdit;
import org.example.libmgmt.ui.components.body.contentSection.DocumentLibrary;
import org.example.libmgmt.ui.components.body.contentSection.FeedbackPanel;
import org.example.libmgmt.ui.components.body.contentSection.LibrarianDashboard;
import org.example.libmgmt.ui.components.body.contentSection.LoginForm;
import org.example.libmgmt.ui.components.body.contentSection.MemberListView;
import org.example.libmgmt.ui.components.body.contentSection.PDFViewer;
import org.example.libmgmt.ui.components.body.contentSection.ReturnDocument;
import org.example.libmgmt.ui.components.body.contentSection.SignUpForm;
import org.example.libmgmt.ui.components.body.contentSection.UserDetails;
import org.example.libmgmt.ui.components.body.searchPanel.DocumentSearchPanel;
import org.example.libmgmt.ui.components.body.searchPanel.MemberSearchPanel;

/**
 * A director instance which tells the builder to build body part.
 */
public class BodyDirector {
  /**
   * Creates a login form.
   *
   * @param bodyBuilder Builder.
   */
  public void createLoginForm(BodyBuilder bodyBuilder) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.AUTHENTICATION);
    bodyBuilder.setTitle("ĐĂNG NHẬP", false);
    LoginForm lf = new LoginForm();
    bodyBuilder.setContent(lf);
    bodyBuilder.style();
  }

  /**
   * Creates a sign-up form.
   *
   * @param bodyBuilder Builder.
   */
  public void createSignUpForm(BodyBuilder bodyBuilder) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.AUTHENTICATION);
    bodyBuilder.setTitle("ĐĂNG KÝ", false);
    SignUpForm sf = new SignUpForm();
    bodyBuilder.setContent(sf);
    bodyBuilder.style();
  }

  /**
   * Creates a main page.
   *
   * @param bodyBuilder Builder.
   */
  public void createMainPagePanel(BodyBuilder bodyBuilder, BodyType bodyType) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.MAIN);
    switch (bodyType) {
      case MAIN_DASHBOARD -> {
        bodyBuilder.setTitle("TRANG CHỦ", false);
        if (UserControl.getUser().isLibrarian()) {
          LibrarianDashboard ld = new LibrarianDashboard();
          bodyBuilder.setContent(ld);
        }
      }
      case MAIN_HISTORY -> {
        bodyBuilder.setTitle("LỊCH SỬ MƯỢN SÁCH", false);
        BorrowHistoryPanel bhp = new BorrowHistoryPanel();
        bodyBuilder.setContent(bhp);
      }
      case MAIN_MEMBER -> {
        bodyBuilder.setTitle("THÀNH VIÊN", false);
        MemberListView member = new MemberListView();
        bodyBuilder.setSearchPanel(new MemberSearchPanel(member).getPanel());
        bodyBuilder.setContent(member);
      }
      case MAIN_LIBRARY -> {
        bodyBuilder.setTitle("THƯ VIỆN", false);
        DocumentLibrary library = new DocumentLibrary();
        bodyBuilder.setSearchPanel(new DocumentSearchPanel(library).getPanel());
        bodyBuilder.setContent(library);
      }
      case MAIN_FEEDBACK -> {
        bodyBuilder.setTitle("PHẢN HỒI", false);
        FeedbackPanel fp = new FeedbackPanel();
        bodyBuilder.setContent(fp);
      }
    }
    bodyBuilder.style();
  }

  /**
   * Creates a document detail panel.
   *
   * @param bodyBuilder Builder.
   * @param doc Document whose info is displayed.
   */
  public void createDocumentDetailPanel(BodyBuilder bodyBuilder, Document doc) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.MAIN);
    bodyBuilder.setTitle("Thông tin tài liệu", true);
    DocumentDetails dd = new DocumentDetails(doc);
    bodyBuilder.setContent(dd);
    bodyBuilder.style();
  }

  /**
   * Creates a login form.
   *
   * @param bodyBuilder Builder.
   * @param member User whose info is displayed.
   */
  public void createMemberDetailPanel(BodyBuilder bodyBuilder, User member) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.MAIN);
    bodyBuilder.setTitle("Thông tin thành viên", true);
    UserDetails ud = new UserDetails(member);
    bodyBuilder.setContent(ud);
    bodyBuilder.style();
  }

  /**
   * Creates a login form.
   *
   * @param bodyBuilder Builder.
   * @param user Borrower.
   * @param doc Checkout document.
   */
  public void createCheckoutPanel(BodyBuilder bodyBuilder, User user, Document doc) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.MAIN);
    bodyBuilder.setTitle("Thông tin mượn sách", true);
    CheckoutPanel cp = new CheckoutPanel(user, doc);
    bodyBuilder.setContent(cp);
    bodyBuilder.style();
  }

  /**
   * Creates a document edit panel.
   *
   * @param bodyBuilder Builder.
   * @param doc Target.
   */
  public void createDocumentEditPanel(BodyBuilder bodyBuilder, Document doc) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.MAIN);
    bodyBuilder.setTitle("Chỉnh sửa tài liệu", true);
    DocumentEdit de = new DocumentEdit(doc);
    bodyBuilder.setContent(de);
    bodyBuilder.style();
  }

  /**
   * Creates an add document panel.
   *
   * @param bodyBuilder Builder.
   */
  public void createAddDocumentPanel(BodyBuilder bodyBuilder) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.MAIN);
    bodyBuilder.setTitle("Thêm tài liệu", true);
    DocumentEdit de = new DocumentEdit();
    bodyBuilder.setContent(de);
    bodyBuilder.style();
  }

  public void createPdfViewer(BodyBuilder bodyBuilder, PDFViewer viewer) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.PDF_VIEWER);
    bodyBuilder.setContent(viewer);
    bodyBuilder.style();
  }

  public void createAddMemberPanel(BodyBuilder bodyBuilder) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.MAIN);
    bodyBuilder.setTitle("Tạo tài khoản", true);
    AccountInfoPanel aip = new AccountInfoPanel();
    bodyBuilder.setContent(aip);
    bodyBuilder.style();
  }

  public void createChangePwdPanel(BodyBuilder bodyBuilder) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.MAIN);
    bodyBuilder.setTitle("Đổi mật khẩu", false);
    AccountInfoPanel aip = new AccountInfoPanel(UserControl.getAccount());
    bodyBuilder.setContent(aip);
    bodyBuilder.style();
  }

  public void createNewMemberDetailPanel(BodyBuilder bodyBuilder, Account acc) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.MAIN);
    bodyBuilder.setTitle("Cập nhật thông tin", true);
    UserDetails ud = new UserDetails(acc);
    bodyBuilder.setContent(ud);
    bodyBuilder.style();
  }

  public void createReturnDocmentPanel(BodyBuilder bodyBuilder) {
    bodyBuilder.reset();
    bodyBuilder.setType(BodyType.MAIN);
    bodyBuilder.setTitle("Trả tài liệu", true);
    ReturnDocument rd = new ReturnDocument();
    bodyBuilder.setContent(rd);
    bodyBuilder.style();
  }
}
