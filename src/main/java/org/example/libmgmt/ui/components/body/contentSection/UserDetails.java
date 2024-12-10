package org.example.libmgmt.ui.components.body.contentSection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.example.libmgmt.DB.Account;
import org.example.libmgmt.DB.AccountDAO;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.DB.UserDAO;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.components.body.DateGroup;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

/**
 * A panel displaying a specific user info.
 */
public class UserDetails extends Content {
  private User user;
  private Account acc;
  private final boolean isEditSession;
  private static final Label nameLabel = new Label("Tên:");
  private static final Label userIdLabel = new Label("Mã người dùng:");
  private static final Label dobLabel = new Label("Ngày sinh:");
  private static final Label genderLabel = new Label("Giới tính:");
  private static final Label addressLabel = new Label("Địa chỉ:");
  private static final Label phoneNumberLabel = new Label("Số điện thoại:");
  private static final Label ssnLabel = new Label("Số CCCD:");
  private static final Label emailLabel = new Label("Email:");
  private static final Label roleLabel = new Label("Vai trò:");
  private static final Label photoNotice = new Label("Vui lòng đến "
      + "thư viện để thay đổi ảnh chân dung");
  private final ImageView potrait;
  private final TextField name;
  private final TextField userId;
  private final DateGroup dob;
  private final ComboBox<String> gender;
  private ComboBox<String> role;
  private final TextField address;
  private final TextField phoneNumber;
  private final TextField ssn;
  private final TextField email;
  private Button changeAvatar = new Button("Thay ảnh chân dung");
  private final Button saveChanges;
  private final GridPane infoTable;
  private final FlowPane mainContent;
  private final VBox potraitWrapper;
  private final VBox container;
  private final ScrollPane wrapper;

  /**
   * Create a page to display user's info.
   * @param user User needs showing info.
   */
  public UserDetails(User user) {
    super(false);
    isEditSession = true;
    this.user = user;
    userId = new TextField(Integer.toString(user.getUid()));
    name = new TextField(user.getName());
    dob = new DateGroup(user.getDob().toString());
    gender = new ComboBox<>(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
    gender.getSelectionModel().select(user.getSex());
    address = new TextField(user.getAddress());
    phoneNumber = new TextField(user.getPhone());
    ssn = new TextField(user.getSSN());
    email = new TextField(user.getEmail());
    saveChanges = new Button("Lưu chỉnh sửa");
    infoTable = new GridPane();
    potrait = new ImageView(user.getAvatar());
    role = new ComboBox<>(FXCollections.observableArrayList("Thủ thư", "Người đọc"));
    role.getSelectionModel().select(user.isLibrarian() ? "Thủ thư" : "Người đọc");
    potraitWrapper = new VBox(potrait, UserControl.getUser().isLibrarian()
        ? changeAvatar : photoNotice);
    infoTable.addColumn(0, userIdLabel, nameLabel, dobLabel, genderLabel, addressLabel,
        phoneNumberLabel, ssnLabel, emailLabel, saveChanges);
    infoTable.addColumn(1, userId, name, dob.getContent(), gender, address, phoneNumber, ssn, email);
    mainContent = new FlowPane(infoTable, potraitWrapper);
    container = new VBox(mainContent, saveChanges);
    wrapper = new ScrollPane(container);
    setFunction();
    style();
  }

  public UserDetails(Account acc) {
    super(false);
    isEditSession = false;
    this.acc = acc;
    userId = new TextField();
    name = new TextField();
    dob = new DateGroup();
    gender = new ComboBox<>(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
    role = new ComboBox<>(FXCollections.observableArrayList("Thủ thư", "Người đọc"));
    address = new TextField();
    phoneNumber = new TextField();
    ssn = new TextField();
    email = new TextField();
    saveChanges = new Button("Tạo tài khoản");
    infoTable = new GridPane();
    potrait = new ImageView();
    potraitWrapper = new VBox(potrait, UserControl.getUser().isLibrarian()
        ? changeAvatar : photoNotice);
    infoTable.addColumn(0, nameLabel, dobLabel, genderLabel, roleLabel, addressLabel,
        phoneNumberLabel, ssnLabel, emailLabel, saveChanges);
    infoTable.addColumn(1, name, dob.getContent(), gender, role, address, phoneNumber, ssn, email);
    mainContent = new FlowPane(infoTable, potraitWrapper);
    container = new VBox(mainContent, saveChanges);
    wrapper = new ScrollPane(container);
    setFunction();
    style();
  }

  private void setFunction() {
    if (isEditSession) {
      userId.setEditable(false);
    }
    ssn.textProperty().addListener((obs, oldval, newval) -> {
      String modified = "";
      for (int i = 0; i < newval.length(); i++) {
        if ('0' <= newval.charAt(i) && newval.charAt(i) <= '9') {
          modified += newval.charAt(i);
          if (modified.length() == 12) {
            break;
          }
        }
      }
      ssn.setText(modified);
    });
    phoneNumber.textProperty().addListener((obs, oldval, newval) -> {
      String modified = "";
      for (int i = 0; i < newval.length(); i++) {
        if ('0' <= newval.charAt(i) && newval.charAt(i) <= '9') {
          modified += newval.charAt(i);
          if (modified.length() == 10) {
            break;
          }
        }
      }
      phoneNumber.setText(modified);
    });
    saveChanges.setOnMouseClicked(e -> {
      if (e.getButton() != MouseButton.PRIMARY) {
        return;
      }
      if (name.getText().isEmpty() || gender.getValue() == null
          || dob.getDay().isEmpty() || dob.getMonth().isEmpty() || dob.getYear().isEmpty()
          || address.getText().isEmpty() || phoneNumber.getText().isEmpty()
          || email.getText().isEmpty() || ssn.getText().isEmpty() || role.getValue() == null
          || potrait.getImage() == null) {
        Popup p = new Popup("Thiếu thông tin", "Vui lòng điền đầy đủ thông tin vào các miền.");
        p.addOkBtn();
        UIHandler.addPopup(p);
        return;
      }
      saveChanges.setText("Đang lưu");
      Style.styleRoundedSolidButton(saveChanges, Style.DARKGREEN,
          saveChanges.getWidth(), saveChanges.getHeight(), 16);
      Task<Void> save = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
          if (isEditSession) {
            user.setName(name.getText());
            user.setAddress(address.getText());
            user.setAvatar(potrait.getImage());
            user.setEmail(email.getText());
            user.setSex(gender.getValue());
            user.setPhone(phoneNumber.getText());
            user.setSSN(ssn.getText());
            Date d = Date.valueOf(dob.getYear() + "-" + dob.getMonth() + "-" + dob.getDay());
            user.setDob(d);
            UserDAO.getInstance().updateUser(user);
            Platform.runLater(() -> {
              saveChanges.setText("Lưu thay đổi");
              Style.styleRoundedSolidButton(saveChanges, Style.LIGHTGREEN,
                  saveChanges.getWidth(), saveChanges.getHeight(), 16);
              Popup p = new Popup("Thành công", "Cập nhật thông tin thành công.");
              p.addOkBtn();
              UIHandler.addPopup(p);
            });
          } else {
            user = new User();
            user.setName(name.getText());
            user.setAddress(address.getText());
            user.setAvatar(potrait.getImage());
            user.setEmail(email.getText());
            user.setSex(gender.getValue());
            user.setPhone(phoneNumber.getText());
            user.setSSN(ssn.getText());
            user.setAvatar(potrait.getImage());
            user.setLibrarian(role.getValue() == "Thủ thư");
            Date d = Date.valueOf(dob.getYear() + "-" + dob.getMonth() + "-" + dob.getDay());
            user.setDob(d);
            AccountDAO.getInstance().addAccount(acc);
            UserDAO.getInstance().addUser(user);
            Platform.runLater(() -> {
              saveChanges.setText("Lưu thay đổi");
              Style.styleRoundedSolidButton(saveChanges, Style.LIGHTGREEN,
                  saveChanges.getWidth(), saveChanges.getHeight(), 16);
              Popup p = new Popup("Thành công", "Tạo tài khoản thành công");
              p.addCustomBtn("OK", Style.LIGHTGREEN, () -> {
                if (UserControl.getUser().isLibrarian()) {
                  UIHandler.switchToSection(BodyType.MAIN_MEMBER);
                } else {
                  UIHandler.gotoMain();
                }
                return null;
              });
              UIHandler.addPopup(p);
            });
          }
          return null;
        }
      };
      new Thread(save).start();
    });
    changeAvatar.setOnMouseClicked(e -> {
      if (e.getButton() != MouseButton.PRIMARY) {
        return;
      }
      FileChooser f = new FileChooser();
      f.getExtensionFilters().add(new FileChooser.ExtensionFilter("Photo", "*.png", "*.jpg", "*.jpeg"));
      File avatarFile = f.showOpenDialog(UIHandler.getStage());
      if (avatarFile != null) {
        try {
          if (Files.size(avatarFile.toPath()) > 2 * 1024 * 1024) {
            Popup p = new Popup("Không thể tải lên", "Ảnh có dung lượng quá lớn (trên 2MB).");
          } else {
            potrait.setImage(new Image(new FileInputStream(avatarFile)));
          }
        } catch (FileNotFoundException ex) {
          throw new RuntimeException(ex);
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    });
  }

  private void style() {
    Style.styleImg(potrait, 400);
    Style.styleTitle(nameLabel, 16);
    Style.styleTitle(addressLabel, 16);
    Style.styleTitle(dobLabel, 16);
    Style.styleTitle(emailLabel, 16);
    Style.styleTitle(genderLabel, 16);
    Style.styleTitle(phoneNumberLabel, 16);
    Style.styleTitle(ssnLabel, 16);
    Style.styleTitle(photoNotice, 16);

    Style.styleTextField(name, 500, 50, 16, false, "");
    Style.styleTextField(address, 500, 50, 16, false, "");
    dob.style(500);
    Style.styleTextField(email, 500, 50, 16, false, "");
    Style.styleTextField(phoneNumber, 500, 50, 16, false, "");
    Style.styleTextField(ssn, 500, 50, 16, false, "");
    StyleForm.styleComboBox(gender, 500, 50, 16, "");
    Style.styleRoundedSolidButton(saveChanges, Style.LIGHTGREEN, 150, 50, 16);

    if (isEditSession) {
      Style.styleTitle(userIdLabel, 16);
      Style.styleTextField(userId, 500, 50, 16, false, "");
    } else {
      Style.styleTitle(roleLabel, 16);
      StyleForm.styleComboBox(role, 500, 50, 16, "");
    }

    container.prefWidthProperty().bind(wrapper.widthProperty().subtract(30));
    ObjectBinding<Insets> padding = Bindings.createObjectBinding(() -> {
      double val;
      if (infoTable.getWidth() == 0) {
        if (wrapper.getWidth() > 1200) {
          val = (wrapper.getWidth() - 1250) / 2;
        } else {
          val = (wrapper.getWidth() - 650) / 2;
        }
      } else {
        if (wrapper.getWidth() > infoTable.getWidth() + potraitWrapper.getWidth() + 150) {
          val = (wrapper.getWidth() - infoTable.getWidth()
              - potraitWrapper.getWidth() - 150) / 2;
        } else {
          val = (wrapper.getWidth() - infoTable.getWidth()) / 2;
        }
      }
      val = Math.max(0, val);
      return new Insets(0, val, 0, val);
    }, wrapper.widthProperty());
    mainContent.paddingProperty().bind(padding);
    infoTable.setVgap(10);
    infoTable.setHgap(50);
    mainContent.setHgap(100);
    mainContent.setVgap(20);
    Style.styleRoundedSolidButton(changeAvatar, Style.LIGHTGREEN, 250, 50, 16);
    potraitWrapper.setAlignment(Pos.CENTER);
    potraitWrapper.setSpacing(10);
    container.setAlignment(Pos.CENTER);
    container.setSpacing(50);
  }

  public ScrollPane getContent() {
    return wrapper;
  }
}


