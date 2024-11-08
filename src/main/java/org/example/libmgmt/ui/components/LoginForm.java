package org.example.libmgmt.ui.components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.libmgmt.control.LoginControl;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.style.Style;

public class LoginForm {
    private Label usrnLb;
    private TextField usrnField;
    private Label pwdLb;
    private PasswordField pwdField;
    private Button signInBtn;
    private Button signUpBtn;
    private VBox container;

    public LoginForm() {
        initializeElements();
        setFunction();
        styleForm();
    }

    private void initializeElements() {
        usrnLb = new Label("TÊN ĐĂNG NHẬP");
        usrnField = new TextField();
        pwdLb = new Label("MẬT KHẨU");
        pwdField = new PasswordField();
        signInBtn = new Button("Đăng nhập");
        signUpBtn = new Button("Tạo tài khoản");
        container = new VBox(usrnField, pwdField, signInBtn, signUpBtn);
    }

    private void setFunction() {
        signInBtn.setDisable(true);
        EventHandler<KeyEvent> disableLoginOnBlankForm = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (usrnField.getText().isEmpty() || pwdField.getText().isEmpty()) {
                    signInBtn.setDisable(true);
                } else {
                    signInBtn.setDisable(false);
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
                signInBtn.setDisable(true);
                usrnField.setDisable(true);
                pwdField.setDisable(true);
                signInBtn.setText("Đang đăng nhập...");
                System.out.println("Disabled");
                int status = LoginControl.authenticate(username, password);
                if (status == LoginControl.SUCCEED) {
                    UIHandler.switchPage();
                }
            }
        });
    }

    private void styleForm() {
        Font fieldFont = new Font("Inter", 32);
        Font btnFont = new Font("Inter", 20);
        Insets textboxInset = new Insets(0, 25, 0, 25);
        Style.styleTextField(usrnField, 550, 100, 32, "Tên đăng nhập");
        Style.styleTextField(pwdField, 550, 100, 32, "Mật khẩu");

        signInBtn.setBackground(new Background(new BackgroundFill(Color.rgb(114, 191, 120),
                new CornerRadii(15), Insets.EMPTY)));
        signInBtn.setPrefSize(200, 50);
        signInBtn.setFont(btnFont);

        signUpBtn.setBackground(Background.EMPTY);
        signUpBtn.setPrefSize(200, 50);
        signUpBtn.setFont(btnFont);

        container.setSpacing(25);
        container.setMinWidth(500);
        container.setAlignment(Pos.CENTER);
//        enableBorder();
    }

    public void enableBorder() {
        container.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public VBox getLoginForm() {
        return container;
    }
}