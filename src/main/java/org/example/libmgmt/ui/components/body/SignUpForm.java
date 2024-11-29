package org.example.libmgmt.ui.components.body;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.libmgmt.DB.Account;
import org.example.libmgmt.DB.AccountDAO;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.DB.UserDAO;
import org.example.libmgmt.control.Validator;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

import java.sql.Date;

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
    private GridPane birthdateContainer;
    private TextField dayField;
    private ComboBox<String> monthField;
    private TextField yearField;
    private Text usrnNote;
    private Text pwdNote;
    private Text retypePwdNote;
    private Text birthdateNote;
    private VBox buttonContainer;
    private VBox container;

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
        dayField = new TextField();
        monthField = new ComboBox<>();
        monthField.getItems().addAll(
                "Tháng 1",
                "Tháng 2",
                "Tháng 3",
                "Tháng 4",
                "Tháng 5",
                "Tháng 6",
                "Tháng 7",
                "Tháng 8",
                "Tháng 9",
                "Tháng 10",
                "Tháng 11",
                "Tháng 12"
        );
        yearField = new TextField();
        birthdateContainer = new GridPane();
        birthdateContainer.add(dayField, 0, 0);
        birthdateContainer.add(monthField, 1, 0);
        birthdateContainer.add(yearField, 2, 0);
        ColumnConstraints c0 = new ColumnConstraints(),
                c1 = new ColumnConstraints(),
                c2 = new ColumnConstraints();
        c0.setPercentWidth(25);
        c1.setPercentWidth(40);
        c2.setPercentWidth(35);
        birthdateContainer.getColumnConstraints().addAll(c0, c1, c2);
        signUpBtn = new Button("Đăng ký");
        signInBtn = new Button("Đã có tài khoản?");
        buttonContainer = new VBox(signUpBtn, signInBtn);
        usrnNote = new Text("Tên đăng nhập có độ dài trong khoảng 6-30 ký tự, " +
                "chỉ bao gồm chữ cái, chữ số, dấu chấm (.), và dấu gạch dưới (_).");
        pwdNote = new Text("Mật khẩu có độ dài trong khoảng 8-50 ký tự, " +
                "chỉ chứa chữ cái, chữ số, và các kí tự thường dùng.");
        retypePwdNote = new Text("Mật khẩu không khớp với mật khẩu đã nhập.");
        birthdateNote = new Text("Vui lòng nhập ngày sinh hợp lệ.");
        container = new VBox(nameLabel, nameField,
                usrnLabel, usrnField, usrnNote,
                pwdLabel, pwdField, pwdNote,
                retypePwdLabel, retypePwdField, retypePwdNote,
                genderLabel, genderField,
                birthdateLabel, birthdateContainer, birthdateNote,
                buttonContainer);
    }

    private void disableSubmitOnBlankForm() {
        if (nameField.getText().isEmpty()
                || usrnField.getText().isEmpty()
                || pwdField.getText().isEmpty()
                || retypePwdField.getText().isEmpty()
                || dayField.getText().isEmpty()
                || yearField.getText().isEmpty()
                || genderField.getValue() == null
                || monthField.getValue() == null) {
            signUpBtn.setDisable(true);
        } else {
            signUpBtn.setDisable(false);
        }
    }

    private int validateInfo() {
        String username = usrnField.getText();
        String password = pwdField.getText();
        String retypePassword = retypePwdField.getText();
        String day = dayField.getText();
        String month = monthField.getValue();
        String year = yearField.getText();

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
        if (!Validator.isValidDate(day, month, year)) {
            status |= 8;
        }
        if (Validator.isUsernameExisted(username)) {
            status |= 16;
        }
        return status;
    }

    private void trimText(TextField tf, int limit) {
        if (tf.getLength() > limit) {
            tf.setText(tf.getText().substring(0, limit));
            tf.positionCaret(limit);
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

        nameField.setOnKeyTyped(e -> {
            disableSubmitOnBlankForm();
            trimText(nameField, 50);
        });
        usrnField.setOnKeyTyped(e -> {
            disableSubmitOnBlankForm();
            trimText(usrnField, 30);
            if (usrnNote.isVisible()) {
                usrnField.setBorder(null);
                usrnNote.setVisible(false);
                usrnNote.setManaged(false);
            }
        });
        pwdField.setOnKeyTyped(e -> {
            disableSubmitOnBlankForm();
            trimText(pwdField, 50);
            if (pwdNote.isVisible()) {
                pwdField.setBorder(null);
                pwdNote.setVisible(false);
                pwdNote.setManaged(false);
            }
        });
        retypePwdField.setOnKeyTyped(e -> {
            disableSubmitOnBlankForm();
            trimText(retypePwdField, 50);
            if (retypePwdNote.isVisible()) {
                retypePwdField.setBorder(null);
                retypePwdNote.setVisible(false);
                retypePwdNote.setManaged(false);
            }
        });
        dayField.setOnKeyTyped(e -> {
            disableSubmitOnBlankForm();
            trimText(dayField, 2);
            if (birthdateNote.isVisible()) {
                dayField.setBorder(null);
                monthField.setBorder(null);
                yearField.setBorder(null);
                birthdateNote.setVisible(false);
                birthdateNote.setManaged(false);
            }
        });
        yearField.setOnKeyTyped(e -> {
            disableSubmitOnBlankForm();
            trimText(yearField, 4);
            if (birthdateNote.isVisible()) {
                dayField.setBorder(null);
                monthField.setBorder(null);
                yearField.setBorder(null);
                birthdateNote.setVisible(false);
                birthdateNote.setManaged(false);
            }
        });
        genderField.setOnAction(e -> {
            disableSubmitOnBlankForm();
        });
        monthField.setOnAction(e -> {
            disableSubmitOnBlankForm();
            if (birthdateNote.isVisible()) {
                dayField.setBorder(null);
                monthField.setBorder(null);
                yearField.setBorder(null);
                birthdateNote.setVisible(false);
                birthdateNote.setManaged(false);
            }
        });

        signUpBtn.setOnMousePressed(_ -> {
            container.setDisable(true);
            signUpBtn.setText("Đang đăng ký...");
            int status = validateInfo();
            if (status == 0) {
                addAccountToDB();
                Popup p = new Popup("Đăng ký thành công!",
                        "Bấm OK để quay lại đăng nhập.");
                p.addOkBtn();
                UIHandler.addPopup(p);
            } else {
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
                    Style.setFieldWarningBorder(dayField);
                    Style.setFieldWarningBorder(monthField);
                    Style.setFieldWarningBorder(yearField);
                    birthdateNote.setVisible(true);
                    birthdateNote.setManaged(true);
                }
                if ((status & 16) == 16) {

                }
            }
        });

        signInBtn.setOnMousePressed(_ -> {
            UIHandler.switchToLogin();
        });
    }

    private void styleForm() {
        Font labelFont = Font.font("Inter", FontWeight.BOLD, 20);
        Insets textboxInset = new Insets(0, 25, 0, 25);
        Style.styleTextField(nameField, 550, 50, 24, "");
        Style.styleTextField(usrnField, 550, 50, 24, "");
        Style.styleTextField(pwdField, 550, 50, 24, "");
        Style.styleTextField(retypePwdField, 550, 50, 24, "");
        Style.styleTextField(dayField, 550, 50, 24, "Ngày");
        Style.styleTextField(yearField, 550, 50, 24, "Năm");

        nameLabel.setFont(labelFont);
        usrnLabel.setFont(labelFont);
        pwdLabel.setFont(labelFont);
        retypePwdLabel.setFont(labelFont);
        genderLabel.setFont(labelFont);
        birthdateLabel.setFont(labelFont);

        StyleForm.styleComboBox(genderField, 550, 50, 24, "");
        StyleForm.styleComboBox(monthField, 550, 50, 24, "Tháng");

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

        birthdateContainer.setMaxWidth(550);
        birthdateContainer.setHgap(10);
        VBox.setMargin(buttonContainer, new Insets(15, 0, 0, 0));
        HBox.setHgrow(dayField, Priority.ALWAYS);
        HBox.setHgrow(monthField, Priority.ALWAYS);
        HBox.setHgrow(yearField, Priority.ALWAYS);
        container.setSpacing(10);
        container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    public VBox getForm() {
        return container;
    }

    public void addAccountToDB() {
        Account acc = new Account();
        User user = new User();
        user.setName(nameField.getText());
        user.setSex(genderField.getValue());
        String dob = String.format("%04d-%02d-%02d",
                Integer.parseInt(yearField.getText()),
                Integer.parseInt(monthField.getValue().substring(6)),
                Integer.parseInt(dayField.getText()));
        user.setDob(Date.valueOf(dob));

        UserDAO userDAO = UserDAO.getInstance();
        int UID = userDAO.addUser(user);
        user.setUID(UID);

        acc.setUID(UID);
        acc.setUsername(usrnField.getText());
        acc.setPassword(pwdField.getText());

        AccountDAO accountDAO = AccountDAO.getInstance();
        accountDAO.addAccount(acc);
    }
}