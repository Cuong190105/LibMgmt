package org.example.libmgmt.ui.director;

import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.ui.builder.BodyBuilder;
import org.example.libmgmt.ui.components.body.*;

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
                TilePane t = new TilePane();
                for (int i = 0; i < 20; i++) {
                    MemberCard m = new MemberCard();
                    t.getChildren().add(m.getCard());
                }
                t.setVgap(25);
                t.setHgap(25);
                bodyBuilder.setTitle("THÀNH VIÊN");
                SearchPanel panel = new SearchPanel();
                panel.addViewOption("Tất cả", "Thủ thư", "Người đọc");
                panel.addSortOption("Tên A-Z", "Tên Z-A", "Mã số 0-9", "Mã số 9-0");
                bodyBuilder.setSearchPanel(panel.getPanel());
                bodyBuilder.setContent(t);
            }

            case MAIN_LIBRARY -> {
                bodyBuilder.setTitle("THƯ VIỆN");
                Button addBook = new Button("Thêm sách");
                TilePane ti = new TilePane();
                for (int i = 0; i < 20; i++) {
                    DocumentCard m = new DocumentCard(new Document());
                    ti.getChildren().add(m.getCard());
                }
                VBox t = new VBox(addBook, ti);
                ti.setVgap(25);
                ti.setHgap(25);
                SearchPanel panel = new SearchPanel();
                panel.addViewOption("Tất cả", "Sách", "Luận án");
                panel.addSortOption("Tên A-Z", "Tên Z-A", "Mã số 0-9", "Mã số 9-0");
                bodyBuilder.setSearchPanel(panel.getPanel());
                bodyBuilder.setContent(t);
            }
            case MAIN_FEEDBACK -> {
                bodyBuilder.setTitle("PHẢN HỒI");
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
}
