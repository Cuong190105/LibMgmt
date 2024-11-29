package org.example.libmgmt.ui.director;

import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.ui.builder.BodyBuilder;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.components.body.DocumentSearchPanel;
import org.example.libmgmt.ui.components.body.MemberSearchPanel;
import org.example.libmgmt.ui.components.body.contentSection.*;

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
    bodyBuilder.setTitle("ĐĂNG NHẬP");
    LoginForm lf = new LoginForm();
    bodyBuilder.setContent(lf.getLoginForm());
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
    bodyBuilder.setTitle("ĐĂNG KÝ");
    SignUpForm sf = new SignUpForm();
    bodyBuilder.setContent(sf.getForm());
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
        bodyBuilder.setTitle("TRANG CHỦ");
      }
      case MAIN_REQUEST -> {
        bodyBuilder.setTitle("YÊU CẦU");
      }
      case MAIN_MEMBER -> {
        bodyBuilder.setTitle("THÀNH VIÊN");
        MemberListView member = new MemberListView();
        bodyBuilder.setSearchPanel(new MemberSearchPanel(member).getPanel());
        bodyBuilder.setContent(member.getContent());
      }
      case MAIN_LIBRARY -> {
        bodyBuilder.setTitle("THƯ VIỆN");
        DocumentLibrary library = new DocumentLibrary();
        bodyBuilder.setSearchPanel(new DocumentSearchPanel(library).getPanel());
        bodyBuilder.setContent(library.getContent());
      }
      case MAIN_FEEDBACK -> {
//                bodyBuilder.setTitle("PHẢN HỒI");
//                SearchPanel panel = new SearchPanel();
//                panel.addViewOption("Tất cả", "Chỉ quan trọng");
//                panel.addSortOption("Mới nhất", "Cũ nhất");
//                bodyBuilder.setSearchPanel(panel.getPanel());
//                VBox t = new VBox();
//                for (int i = 0; i < 20; i++) {
//                    System.out.println(i);
//                    FeedbackCard m = new FeedbackCard();
//                    t.getChildren().add(m.getCard());
//                }
//                t.setSpacing(25);
//                bodyBuilder.setContent(t);
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
    bodyBuilder.setTitle("Thông tin tài liệu");
    DocumentDetails dd = new DocumentDetails(doc);
    bodyBuilder.setContent(dd.getContent());
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
    bodyBuilder.setTitle("Thông tin thành viên");
    UserDetails ud = new UserDetails(member);
    bodyBuilder.setContent(ud.getContent());
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
    bodyBuilder.setTitle("Thông tin mượn sách");
    CheckoutPanel cp = new CheckoutPanel(user, doc);
    bodyBuilder.setContent(cp.getContent());
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
    bodyBuilder.setTitle("Chỉnh sửa tài liệu");
    DocumentEdit de = new DocumentEdit(doc);
    bodyBuilder.setContent(de.getContent());
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
    bodyBuilder.setTitle("Thêm tài liệu");
    DocumentEdit de = new DocumentEdit();
    bodyBuilder.setContent(de.getContent());
    bodyBuilder.style();
  }
}
