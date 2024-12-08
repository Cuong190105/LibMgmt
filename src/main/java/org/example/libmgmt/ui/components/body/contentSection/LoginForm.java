package org.example.libmgmt.ui.components.body.contentSection;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.libmgmt.AppLogger;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

/**
 * Creates a login form panel.
 */
public class LoginForm extends Content {
  private TextField usrnField;
  private PasswordField pwdField;
  private Text warning;
  private Button signInBtn;
  private Button signUpBtn;
  private VBox container;

  /**
   * Constructor.
   */
  public LoginForm() {
    super(false);
    initializeElements();
    setFunction();
    styleForm();
    signInBtn.setDisable(false);
  }

  private void initializeElements() {
    usrnField = new TextField("librarian");
    pwdField = new PasswordField();
    pwdField.setText("librarian");
    warning = new Text();
    signInBtn = new Button("Đăng nhập");
    signUpBtn = new Button("Tạo tài khoản");
    container = new VBox(usrnField, pwdField, warning, signInBtn, signUpBtn);
  }

  private void loginHandler() {
    String username = usrnField.getText();
    String password = pwdField.getText();
    container.setDisable(true);
    signInBtn.setText("Đang đăng nhập...");
    Task<Void> login = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        int status = UserControl.authenticate(username, password);
        Platform.runLater(() -> {
          if (status == UserControl.SUCCEED) {
            UIHandler.gotoMain();
            return;
          }
          container.setDisable(false);
          warning.setManaged(true);
          warning.setVisible(true);
          signInBtn.setText("Đăng nhập");
          switch (status) {
            case UserControl.INVALID_CREDENTIALS ->
                warning.setText("Thông tin đăng nhập không chính xác.");
            case UserControl.COULD_NOT_CONNECT ->
                warning.setText("Không thể kết nối đến máy chủ. Vui lòng thử lại sau.");
            case UserControl.PASSWORD_NOT_MATCHES ->
                warning.setText("Mật khẩu không khớp với tên đăng nhập được cung cấp.");
            case UserControl.USERNAME_NOT_EXISTS ->
                warning.setText("Tên đăng nhập không tồn tại.");
            default -> AppLogger.log(new IllegalStateException(
                "Unexpected value: " + status));
          }
        });
        return null;
      }
    };
    Thread t = new Thread(login);
    t.setDaemon(true);
    t.start();
  }

  private void setFunction() {
    signInBtn.setDisable(true);
    container.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
      if (keyEvent.getCode().equals(KeyCode.ENTER)) {
        loginHandler();
      }
    });

    usrnField.setOnKeyReleased(key -> {
      if (!key.getCode().equals(KeyCode.ENTER)) {
        signInBtn.setDisable(usrnField.getText().isEmpty() || pwdField.getText().isEmpty());
        if (warning.isVisible()) {
          warning.setVisible(false);
          warning.setManaged(false);
        }
      }
    });

    pwdField.setOnKeyReleased(key -> {
      if (!key.getCode().equals(KeyCode.ENTER)) {
        signInBtn.setDisable(usrnField.getText().isEmpty() || pwdField.getText().isEmpty());
        if (warning.isVisible()) {
          warning.setVisible(false);
          warning.setManaged(false);
        }
      }
    });

    signUpBtn.setOnKeyReleased(keyEvent -> {
      if (keyEvent.getCode().equals(KeyCode.ENTER)) {
        UIHandler.switchToSignUp();
      }
    });

    signInBtn.setOnMousePressed(e -> loginHandler());
    signUpBtn.setOnMousePressed(e -> UIHandler.switchToSignUp());
  }

  private void styleForm() {
    warning.setVisible(false);
    warning.setManaged(false);
    StyleForm.styleWarning(warning);
    warning.setTextAlignment(TextAlignment.CENTER);

    Style.styleTextField(usrnField, 550, 100, 32, false, "Tên đăng nhập");
    Style.styleTextField(pwdField, 550, 100, 32, false, "Mật khẩu");

    Style.styleRoundedSolidButton(signInBtn, Style.DARKGREEN, 300, 50, 24);
    Style.styleRoundedSolidButton(signUpBtn, Color.TRANSPARENT, 200, 50, 24);

    container.setSpacing(25);
    container.setAlignment(Pos.CENTER);
    container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  @Override
  public VBox getContent() {
    return container;
  }
}