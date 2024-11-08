package org.example.libmgmt.ui.director;

import org.example.libmgmt.ui.builder.BodyBuilder;
import org.example.libmgmt.ui.components.LoginForm;
import org.example.libmgmt.ui.components.body.BodyType;

public class BodyDirector {
    public void createStartup(BodyBuilder bodyBuilder) {
        bodyBuilder.reset();
        bodyBuilder.setType(BodyType.STARTUP);
    }

    public void createLoginForm(BodyBuilder bodyBuilder) {
        bodyBuilder.reset();
        bodyBuilder.setType(BodyType.LOGIN_FORM);
        bodyBuilder.setTitle("ĐĂNG NHẬP");
        LoginForm lf = new LoginForm();
        bodyBuilder.setContent(lf.getLoginForm());
        bodyBuilder.style();
    }


    public void createMainPagePanel(BodyBuilder bodyBuilder) {
        bodyBuilder.reset();
        bodyBuilder.setType(BodyType.MAIN_DASHBOARD);
        bodyBuilder.setTitle("TRANG CHỦ");
        bodyBuilder.style();
    }
}
