package org.example.libmgmt.ui.director;

import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.example.libmgmt.ui.builder.BodyBuilder;
import org.example.libmgmt.ui.components.body.LoginForm;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.components.body.MemberCard;
import org.example.libmgmt.ui.components.body.SignUpForm;

public class BodyDirector {
    public void createLoginForm(BodyBuilder bodyBuilder) {
        bodyBuilder.reset();
        bodyBuilder.setType(BodyType.LOGIN_FORM);
        bodyBuilder.setTitle("ĐĂNG NHẬP");
        LoginForm lf = new LoginForm();
        bodyBuilder.setContent(lf.getLoginForm());
        bodyBuilder.style();
    }

    public void createSignUpForm(BodyBuilder bodyBuilder) {
        bodyBuilder.reset();
        bodyBuilder.setType(BodyType.SIGNUP_FORM);
        bodyBuilder.setTitle("ĐĂNG KÝ");
        SignUpForm sf = new SignUpForm();
        bodyBuilder.setContent(sf.getForm());
        bodyBuilder.style();
    }

    public void createMainPagePanel(BodyBuilder bodyBuilder) {
        bodyBuilder.reset();
        bodyBuilder.setType(BodyType.MAIN_DASHBOARD);
        bodyBuilder.setTitle("TRANG CHỦ");
        TilePane userlist = new TilePane();
        for (int i = 0; i < 20; i++) {
            userlist.getChildren().add(new MemberCard().getCard());
        }
        bodyBuilder.setContent(userlist);
        bodyBuilder.style();
    }
}
