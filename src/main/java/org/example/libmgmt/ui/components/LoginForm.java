package org.example.libmgmt.ui.components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginForm {
    private Text formTitle;
    private Label usrnLb;
    private TextField usrnField;
    private Label pwdLb;
    private PasswordField pwdField;
    private Button signInBtn;
    private Button signUpBtn;
    //    private Button forgotPwdBtn = new Button("Quên mật khẩu?");
    private VBox container;

    public LoginForm() {
        initializeElements();
        setFunction();
        styleForm();
    }

    private void initializeElements() {
        formTitle = new Text("ĐĂNG NHẬP");
        usrnLb = new Label("Tên đăng nhập");
        usrnField = new TextField();
        pwdLb = new Label("Tên đăng nhập");
        pwdField = new PasswordField();
        signInBtn = new Button("Đăng nhập");
        signUpBtn = new Button("Tạo tài khoản");
        container = new VBox(formTitle, usrnLb, usrnField, pwdLb, pwdField, signInBtn, signUpBtn);
    }

    private void setFunction() {
        EventHandler<KeyEvent> disableLoginOnBlankForm = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (usrnField.getText().isEmpty() || pwdField.getText().isEmpty()) {
                    signInBtn.setDisable(true);
                } else {
                    signInBtn.setDisable(true);
                }
            }
        };

        usrnField.setOnKeyTyped(disableLoginOnBlankForm);
        pwdField.setOnKeyTyped(disableLoginOnBlankForm);

        signInBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String username = usrnField.getText();
                String password = pwdField.getText();
                //Validate
            }
        });
    }

    private void styleForm() {
        container.setAlignment(Pos.CENTER);
        container.setFillWidth(true);
        container.setSpacing(25);

    }

    public VBox getLoginForm() {
        return container;
    }
}