package org.example.libmgmt.ui.components.body.contentSection;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.Validator;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.components.body.DateGroup;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

/**
 * A panel for sign up form.
 */
public class SignUpForm {
  private Label nameLabel;
  private Label usrnLabel;
  private Label pwdLabel;
  private Label retypePwdLabel;
  private Label genderLabel;
  private Label birthdateLabel;
  private TextField nameField;
  private TextField usrnField;
  private PasswordField pwdField;
  private PasswordField retypePwdField;
  private Button signUpBtn;
  private Button signInBtn;
  private ComboBox<String> genderField;
  private DateGroup birthdate;
  private Text usrnNote;
  private Text pwdNote;
  private Text retypePwdNote;
  private Text birthdateNote;
  private VBox buttonContainer;
  private VBox wrapper;
  private ScrollPane scrollPane;
  private VBox container;

  /**
   * Constructor.
   */
  public SignUpForm() {
    initializeElements();
    setFunction();
    styleForm();
  }

  private void initializeElements() {
    nameLabel = new Label("Họ và tên");
    usrnLabel = new Label("Tên đăng nhập");
    pwdLabel = new Label("Mật khẩu");
    retypePwdLabel = new Label("Nhập lại mật khẩu");
    genderLabel = new Label("Giới tính");
    birthdateLabel = new Label("Ngày sinh");
    nameField = new TextField();
    usrnField = new TextField();
    pwdField = new PasswordField();
    retypePwdField = new PasswordField();
    genderField = new ComboBox<>();
    genderField.getItems().addAll(
        "Nam",
        "Nữ",
        "Khác"
    );
    birthdate = new DateGroup();
    signUpBtn = new Button("Đăng ký");
    signInBtn = new Button("Đã có tài khoản?");
    buttonContainer = new VBox(signUpBtn, signInBtn);
    usrnNote = new Text("Tên đăng nhập có độ dài trong khoảng 6-30 ký tự, "
        + "chỉ bao gồm chữ cái, chữ số, dấu chấm (.), và dấu gạch dưới (_).");
    pwdNote = new Text("Mật khẩu có độ dài trong khoảng 8-50 ký tự, "
        + "chỉ chứa chữ cái, chữ số, và các kí tự thường dùng.");
    retypePwdNote = new Text("Mật khẩu không khớp với mật khẩu đã nhập.");
    birthdateNote = new Text("Vui lòng nhập ngày sinh hợp lệ.");
    wrapper = new VBox(nameLabel, nameField,
        usrnLabel, usrnField, usrnNote,
        pwdLabel, pwdField, pwdNote,
        retypePwdLabel, retypePwdField, retypePwdNote,
        genderLabel, genderField,
        birthdateLabel, birthdate.getContent(), birthdateNote
    );
    scrollPane = new ScrollPane(wrapper);
    container = new VBox(scrollPane, buttonContainer);
  }

  private void disableSubmitOnBlankForm() {
    signUpBtn.setDisable(nameField.getText().isEmpty()
        || usrnField.getText().isEmpty()
        || pwdField.getText().isEmpty()
        || retypePwdField.getText().isEmpty()
        || birthdate.getDay().isEmpty()
        || birthdate.getMonth() == null
        || birthdate.getYear().isEmpty()
        || genderField.getValue() == null);
  }

  private int validateInfo() {
    String username = usrnField.getText();
    String password = pwdField.getText();
    String retypePassword = retypePwdField.getText();

    int status = 0;

    if (!Validator.isValidUsername(username)) {
      status |= 1;
    }
    if (!Validator.isValidPassword(password)) {
      status |= 2;
    }
    if (password.compareTo(retypePassword) != 0) {
      status |= 4;
    }

    String day = birthdate.getDay();
    String month = birthdate.getMonth();
    String year = birthdate.getYear();
    if (!Validator.isValidDate(day, month, year)) {
      status |= 8;
    }
    return status;
  }

  private void trimText(TextField tf, int limit) {
    tf.setTextFormatter(new TextFormatter<>(change ->
        change.getControlNewText().length() <= limit ? change : null));
  }

  private void signUpHandler() {
    container.setDisable(true);
    signUpBtn.setText("Đang đăng ký...");
    int status = validateInfo();
    if (status == 0) {


      Popup p = new Popup("Đăng ký thành công!",
          "Bấm OK để quay lại đăng nhập.");
      p.addOkBtn();
      UIHandler.addPopup(p);
    } else {
      signUpBtn.setText("Đăng ký");
      container.setDisable(false);
      if ((status & 1) == 1) {
        Style.setFieldWarningBorder(usrnField);
        usrnNote.setVisible(true);
        usrnNote.setManaged(true);
      }
      if ((status & 2) == 2) {
        Style.setFieldWarningBorder(pwdField);
        pwdNote.setVisible(true);
        pwdNote.setManaged(true);
      }
      if ((status & 4) == 4) {
        Style.setFieldWarningBorder(retypePwdField);
        retypePwdNote.setVisible(true);
        retypePwdNote.setManaged(true);
      }
      if ((status & 8) == 8) {
        birthdate.toggleWarningBorder(true);
        birthdateNote.setVisible(true);
        birthdateNote.setManaged(true);
      }
    }
  }

  private void setFunction() {
    usrnNote.setVisible(false);
    usrnNote.setManaged(false);
    pwdNote.setVisible(false);
    pwdNote.setManaged(false);
    retypePwdNote.setVisible(false);
    retypePwdNote.setManaged(false);
    birthdateNote.setVisible(false);
    birthdateNote.setManaged(false);
    signUpBtn.setDisable(true);

    trimText(nameField, 50);
    trimText(usrnField, 30);
    trimText(pwdField, 50);
    trimText(retypePwdField, 50);
    nameField.setOnKeyTyped(_ -> {
      disableSubmitOnBlankForm();
    });

    usrnField.setOnKeyTyped(_ -> {
      disableSubmitOnBlankForm();
      if (usrnNote.isVisible()) {
        usrnField.setBorder(null);
        usrnNote.setVisible(false);
        usrnNote.setManaged(false);
      }
    });

    pwdField.setOnKeyTyped(_ -> {
      disableSubmitOnBlankForm();
      if (pwdNote.isVisible()) {
        pwdField.setBorder(null);
        pwdNote.setVisible(false);
        pwdNote.setManaged(false);
      }
    });

    retypePwdField.setOnKeyTyped(_ -> {
      disableSubmitOnBlankForm();
      if (retypePwdNote.isVisible()) {
        retypePwdField.setBorder(null);
        retypePwdNote.setVisible(false);
        retypePwdNote.setManaged(false);
      }
    });

    birthdate.setFunction(() -> {
      disableSubmitOnBlankForm();
      if (birthdateNote.isVisible()) {
        birthdateNote.setVisible(false);
        birthdateNote.setManaged(false);
        birthdate.toggleWarningBorder(false);
      }
      return 0;
    });

    genderField.setOnAction(_ -> {
      disableSubmitOnBlankForm();
      genderField.show();
    });

    signUpBtn.setOnMousePressed(_ -> {
      signUpHandler();
    });

    signUpBtn.setOnKeyPressed(key -> {
      if (key.getCode().equals(KeyCode.ENTER)) {
        signUpHandler();
      }
    });

    signInBtn.setOnKeyPressed(key -> {
      if (key.getCode().equals(KeyCode.ENTER)) {
        UIHandler.switchToLogin();
      }
    });

    signInBtn.setOnMousePressed(_ -> {
      UIHandler.switchToLogin();
    });
  }

  private void styleForm() {
    Style.styleTextField(nameField, 550, 50, 16, "");
    Style.styleTextField(usrnField, 550, 50, 16, "");
    Style.styleTextField(pwdField, 550, 50, 16, "");
    Style.styleTextField(retypePwdField, 550, 50, 16, "");
    birthdate.style(550);

    Font labelFont = Font.font("Inter", FontWeight.BOLD, 20);
    nameLabel.setFont(labelFont);
    usrnLabel.setFont(labelFont);
    pwdLabel.setFont(labelFont);
    retypePwdLabel.setFont(labelFont);
    genderLabel.setFont(labelFont);
    birthdateLabel.setFont(labelFont);

    StyleForm.styleComboBox(genderField, 550, 50, 24, "");

    Style.styleRoundedButton(signUpBtn, 200, 50);
    signUpBtn.setBackground(new Background(new BackgroundFill(Style.DARKGREEN,
        Style.SMALL_CORNER, Insets.EMPTY)));
    signUpBtn.setFont(labelFont);

    signInBtn.setBackground(Background.EMPTY);
    signInBtn.setPrefSize(200, 50);
    signInBtn.setFont(labelFont);

    buttonContainer.setAlignment(Pos.CENTER);

    StyleForm.styleWarning(usrnNote);
    StyleForm.styleWarning(pwdNote);
    StyleForm.styleWarning(retypePwdNote);
    StyleForm.styleWarning(birthdateNote);

    VBox.setMargin(buttonContainer, new Insets(15, 0, 0, 0));
    wrapper.setSpacing(10);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    scrollPane.setFitToWidth(true);
    buttonContainer.setSpacing(10);
    container.setMaxWidth(Region.USE_PREF_SIZE);
  }

  public VBox getForm() {
    return container;
  }
}