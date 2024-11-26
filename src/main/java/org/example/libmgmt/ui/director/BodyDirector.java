package org.example.libmgmt.ui.director;

import org.example.libmgmt.DB.Document;
import org.example.libmgmt.ui.builder.BodyBuilder;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.components.body.DocumentSearchPanel;
import org.example.libmgmt.ui.components.body.MemberSearchPanel;
import org.example.libmgmt.ui.components.body.contentSection.*;

public class BodyDirector {
    public void createLoginForm(BodyBuilder bodyBuilder) {
        bodyBuilder.reset();
        bodyBuilder.setType(BodyType.AUTHENTICATION);
        bodyBuilder.setTitle("ĐĂNG NHẬP");
        LoginForm lf = new LoginForm();
        bodyBuilder.setContent(lf.getLoginForm());
        bodyBuilder.style();
    }

    public void createSignUpForm(BodyBuilder bodyBuilder) {
        bodyBuilder.reset();
        bodyBuilder.setType(BodyType.AUTHENTICATION);
        bodyBuilder.setTitle("ĐĂNG KÝ");
        SignUpForm sf = new SignUpForm();
        bodyBuilder.setContent(sf.getForm());
        bodyBuilder.style();
    }

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

    public void createDocumentDetailPanel(BodyBuilder bodyBuilder, Document doc) {
        bodyBuilder.reset();
        bodyBuilder.setType(BodyType.DOC_DETAIL);
        bodyBuilder.setTitle("Thông tin tài liệu");
        DocumentDetails dd = new DocumentDetails(doc);
        bodyBuilder.setContent(dd.getContent());
        bodyBuilder.style();
    }

    public void createMemberDetailPanel(BodyBuilder bodyBuilder, User member) {
        bodyBuilder.reset();
        bodyBuilder.setType(BodyType.MEMBER_DETAIL);
        bodyBuilder.setTitle("Thông tin thành viên");
        UserDetails ud = new UserDetails(member);
        bodyBuilder.setContent(ud.getContent());
        bodyBuilder.style();
    }
}
