package org.example.libmgmt.ui.components.body.contentSection;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.libmgmt.DB.Account;
import org.example.libmgmt.DB.AccountDAO;
import org.example.libmgmt.DB.DatabaseConnectionException;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.Validator;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

/**
 * A panel to view/edit/create account info.
 */
public class AccountInfoPanel extends Content {
  private final boolean isEditSession;
  private Account acc;
  private ScrollPane wrapper;
  private GridPane container;
  private Label usernameLabel;
  private Label oldPwdLabel;
  private Label pwdLabel;
  private Label retypePwdLabel;
  private Text usernameWarning;
  private Text oldPwdWarning;
  private Text pwdWarning;
  private Text retypePwdWarning;
  private Text failedToConnectWarning;
  private Button save;
  private TextField username;
  private PasswordField oldPwd;
  private PasswordField pwd;
  private PasswordField retypePwd;

  public AccountInfoPanel(Account acc) {
    super(false);
    isEditSession = true;
    this.acc = acc;
    createPanel();
  }

  public AccountInfoPanel() {
    super(false);
    isEditSession = false;
    this.acc = new Account();
    createPanel();
  }

  private void createPanel() {
    container = new GridPane();
    wrapper = new ScrollPane(container);
    usernameLabel = new Label("Tên đăng nhập:");
    oldPwdLabel = new Label("Mật khẩu cũ:");
    pwdLabel = new Label(isEditSession ? "Mật khẩu mới" : "Mật khẩu");
    retypePwdLabel = new Label("Nhập lại mật khẩu:");
    username = new TextField(isEditSession ? acc.getUsername() : "");
    oldPwd = new PasswordField();
    pwd = new PasswordField();
    retypePwd = new PasswordField();
    usernameWarning = new Text();
    oldPwdWarning = new Text("Mật khẩu không khớp với mật khẩu hiện tại.");
    failedToConnectWarning = new Text("Không thể kết nôi.");
    pwdWarning = new Text("Mật khẩu phải có độ dài 8-50 ký tự, chỉ chứa chữ cái,"
        + " chữ số, và các ký hiệu thông dụng.");
    retypePwdWarning = new Text("Mật khẩu nhập lại không khớp với mật khẩu mới đã cung cấp.");
    save = new Button(isEditSession ? "Lưu thay đổi" : "Tạo tài khoản");
    VBox usernameGroup = new VBox(username, usernameWarning);
    VBox oldPwdGroup = new VBox(oldPwd, oldPwdWarning);
    VBox pwdGroup = new VBox(pwd, pwdWarning);
    VBox retypePwdGroup = new VBox(retypePwd, retypePwdWarning);
    VBox saveGroup = new VBox(failedToConnectWarning, save);
    container.addColumn(0, usernameLabel, oldPwdLabel, pwdLabel, retypePwdLabel, saveGroup);
    container.addColumn(1, usernameGroup, oldPwdGroup, pwdGroup, retypePwdGroup);
    setFunction();
    style();
  }

  private void setFunction() {
    username.textProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal.length() > 30) {
        username.setText(newVal.substring(0, 30));
      }
      if (usernameWarning.isVisible()) {
        usernameWarning.setVisible(false);
        usernameWarning.setManaged(false);
      }
    });
    oldPwd.textProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal.length() > 50) {
        oldPwd.setText(newVal.substring(0, 50));
      }
      if (oldPwdWarning.isVisible()) {
        oldPwdWarning.setVisible(false);
        oldPwdWarning.setManaged(false);
      }
    });
    pwd.textProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal.length() > 50) {
        pwd.setText(newVal.substring(0, 50));
      }
      if (pwdWarning.isVisible()) {
        pwdWarning.setVisible(false);
        pwdWarning.setManaged(false);
      }
    });
    retypePwd.textProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal.length() > 50) {
        retypePwd.setText(newVal.substring(0, 50));
      }
      if (retypePwdWarning.isVisible()) {
        retypePwdWarning.setVisible(false);
        retypePwdWarning.setManaged(false);
      }
    });
    save.setOnMouseClicked(e -> {
      if (e.getButton() != MouseButton.PRIMARY) {
        return;
      }
      if (failedToConnectWarning.isVisible()) {
        failedToConnectWarning.setVisible(false);
        failedToConnectWarning.setManaged(false);
      }
      save.setText("Đang lưu");
      Style.styleRoundedSolidButton(save, Style.DARKGREEN, 150, 50, 16);
      Task<Void> validate = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
          Thread.sleep(1000);
          int status = 0;
          status |= Validator.isValidPassword(pwd.getText()) ? 0 : 2;
          if (isEditSession) {
            status |= acc.getPassword().compareTo(oldPwd.getText()) == 0 ? 0 : 4;
          } else {
            status |= Validator.isValidUsername(username.getText()) ? 0 : 1;
            try {
              status |= AccountDAO.getInstance()
                  .getAccountFromUsername(username.getText()) == null ? 0 : 16;
            } catch (DatabaseConnectionException e) {
              status |= 32;
            }
          }
          status |= pwd.getText().compareTo(retypePwd.getText()) == 0 ? 0 : 8;
          if (status == 0) {
            acc.setPassword(retypePwd.getText());
            if (isEditSession) {
              AccountDAO.getInstance().changePassword(acc);

            } else {
              acc.setUsername(username.getText());
              AccountDAO.getInstance().addAccount(acc);
            }
          }
          int finalStatus = status;
          Platform.runLater(() -> {
            if (finalStatus == 0) {
              if (isEditSession) {
                Popup p = new Popup("Thành công", "Đổi mật khẩu thành công.");
                p.addOkBtn();
                UIHandler.addPopup(p);
              }
            }
            if ((finalStatus & 1) == 1) {
              usernameWarning.setText("Tên người dùng có độ dài 6-30 ký tự, chỉ được chứa chữ cái,"
                  + " chữ số, dấu chấm (.), và dấu gạch dưới (_).");
              usernameWarning.setManaged(true);
              usernameWarning.setVisible(true);
            }
            if ((finalStatus & 2) == 2) {
              pwdWarning.setManaged(true);
              pwdWarning.setVisible(true);
            }
            if ((finalStatus & 4) == 4) {
              oldPwdWarning.setManaged(true);
              oldPwdWarning.setVisible(true);
            }
            if ((finalStatus & 8) == 8) {
              retypePwdWarning.setManaged(true);
              retypePwdWarning.setVisible(true);
            }
            if ((finalStatus & 16) == 16) {
              usernameWarning.setText("Tên người dùng đã tồn tại.");
              usernameWarning.setManaged(true);
              usernameWarning.setVisible(true);
            }
            if ((finalStatus & 32) == 32) {
              failedToConnectWarning.setManaged(true);
              failedToConnectWarning.setVisible(true);
            }
            save.setText(isEditSession ? "Lưu thay đổi" : "Tạo tài khoản");
            Style.styleRoundedSolidButton(save, Style.LIGHTGREEN, 150, 50, 16);
          });
          return null;
        }
      };
      new Thread(validate).start();
    });
  }

  private void style() {
    usernameWarning.setVisible(false);
    usernameWarning.setManaged(false);
    oldPwdWarning.setVisible(false);
    oldPwdWarning.setManaged(false);
    pwdWarning.setVisible(false);
    pwdWarning.setManaged(false);
    retypePwdWarning.setVisible(false);
    retypePwdWarning.setManaged(false);
    failedToConnectWarning.setVisible(false);
    failedToConnectWarning.setManaged(false);

    if (isEditSession) {
      username.setEditable(false);
    } else {
      oldPwdLabel.setVisible(false);
      oldPwdLabel.setManaged(false);
      oldPwd.setVisible(false);
      oldPwd.setManaged(false);
      oldPwdWarning.setVisible(false);
      oldPwdWarning.setManaged(false);
    }

    Style.styleTitle(usernameLabel, 16);
    Style.styleTitle(pwdLabel, 16);
    Style.styleTitle(oldPwdLabel, 16);
    Style.styleTitle(retypePwdLabel, 16);
    Style.styleTextField(username, 500, 50, 16, false, "");
    Style.styleTextField(oldPwd, 500, 50, 16, false, "");
    Style.styleTextField(pwd, 500, 50, 16, false, "");
    Style.styleTextField(retypePwd, 500, 50, 16, false, "");
    StyleForm.styleWarning(usernameWarning);
    StyleForm.styleWarning(oldPwdWarning);
    StyleForm.styleWarning(pwdWarning);
    StyleForm.styleWarning(retypePwdWarning);
    StyleForm.styleWarning(failedToConnectWarning);
    Style.styleRoundedSolidButton(save, Style.LIGHTGREEN, 150, 50, 16);
    container.setHgap(50);
    container.setVgap(20);
    container.setAlignment(Pos.CENTER);
  }

  @Override
  public ScrollPane getContent() {
    return wrapper;
  }
}
