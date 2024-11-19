package org.example.libmgmt.ui.components.body;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.libmgmt.control.Validator;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

public class LoginForm {
    private TextField usrnField;
    private PasswordField pwdField;
    private Text warning;
    private Button signInBtn;
    private Button signUpBtn;
    private VBox container;

    public LoginForm() {
        initializeElements();
        setFunction();
        styleForm();
    }

    private void initializeElements() {
        usrnField = new TextField();
        pwdField = new PasswordField();
        warning = new Text();
        signInBtn = new Button("Đăng nhập");
        signUpBtn = new Button("Tạo tài khoản");
        container = new VBox(usrnField, pwdField, warning, signInBtn, signUpBtn);
    }

    private void setFunction() {
        signInBtn.setDisable(true);
        EventHandler<KeyEvent> disableLoginOnBlankForm = keyEvent -> {
            if (usrnField.getText().isEmpty() || pwdField.getText().isEmpty()) {
                signInBtn.setDisable(true);
            } else {
                signInBtn.setDisable(false);
            }
            if (warning.isVisible()) {
                warning.setVisible(false);
                warning.setManaged(false);
            }
        };

        usrnField.setOnKeyTyped(disableLoginOnBlankForm);
        pwdField.setOnKeyTyped(disableLoginOnBlankForm);

        signInBtn.setOnMousePressed(_ -> {
            String username = usrnField.getText();
            String password = pwdField.getText();
            container.setDisable(true);
            signInBtn.setText("Đang đăng nhập...");
            int status = Validator.authenticate(username, password);
            switch (status) {
                case Validator.SUCCEED -> {
                    UIHandler.gotoMain();
                }
                case Validator.INVALID_CREDENTIALS -> {
                    container.setDisable(false);
                    warning.setText("Thông tin đăng nhập không chính xác.");
                    warning.setManaged(true);
                    warning.setVisible(true);
                    signInBtn.setText("Đăng nhập");
                }
            }
        });

        signUpBtn.setOnMousePressed(_ -> {
            UIHandler.switchToSignUp();
        });
    }

    private void styleForm() {
        Font font = Font.font("Inter", 20);
        warning.setVisible(false);
        warning.setManaged(false);
        StyleForm.styleWarning(warning);
        warning.setTextAlignment(TextAlignment.CENTER);

        Style.styleTextField(usrnField, 550, 100, 32, "Tên đăng nhập");
        Style.styleTextField(pwdField, 550, 100, 32, "Mật khẩu");

        signInBtn.setBackground(new Background(new BackgroundFill(Color.rgb(114, 191, 120),
                new CornerRadii(15), Insets.EMPTY)));
        signInBtn.setPrefSize(200, 50);
        signInBtn.setFont(font);

        signUpBtn.setBackground(Background.EMPTY);
        signUpBtn.setPrefSize(200, 50);
        signUpBtn.setFont(font);

        container.setSpacing(25);
        container.setAlignment(Pos.CENTER);
        container.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    }

    public VBox getLoginForm() {
        return container;
    }
}