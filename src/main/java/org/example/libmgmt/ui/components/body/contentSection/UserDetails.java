package org.example.libmgmt.ui.components.body.contentSection;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.ui.components.body.DateGroup;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

/**
 * A panel displaying a specific user info.
 */
public class UserDetails {
  private static final Label nameLabel = new Label("Tên:");
  private static final Label userIdLabel = new Label("Mã người dùng:");
  private static final Label dobLabel = new Label("Ngày sinh:");
  private static final Label genderLabel = new Label("Giới tính:");
  private static final Label addressLabel = new Label("Địa chỉ:");
  private static final Label phoneNumberLabel = new Label("Số điện thoại:");
  private static final Label ssnLabel = new Label("Số CCCD:");
  private static final Label emailLabel = new Label("Email:");
  private static final Label photoNotice = new Label("Vui lòng đến "
      + "thư viện để thay đổi ảnh chân dung");
  private final Button copyUserId;
  private final ImageView potrait;
  private final TextField name;
  private final TextField userId;
  private final DateGroup dob;
  private final ComboBox<String> gender;
  private final TextField address;
  private final TextField phoneNumber;
  private final TextField ssn;
  private final TextField email;
  private final Button saveChanges;
  private final GridPane container;
  private final VBox potraitWrapper;
  private final HBox userIdWrapper;

  /**
   * Create a page to display user's info.
   * @param user User needs showing info.
   */
  public UserDetails(User user) {
    userId = new TextField(Integer.toString(user.getUid()));
    copyUserId = new Button("Sao chép");
    userIdWrapper = new HBox(userId, copyUserId);
    name = new TextField(user.getName());
    dob = new DateGroup(user.getDob().toString());
    gender = new ComboBox<>(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
    gender.getSelectionModel().select(user.getSex());
    address = new TextField(user.getAddress());
    phoneNumber = new TextField(user.getPhone());
    ssn = new TextField(user.getSSN());
    email = new TextField(user.getEmail());
    saveChanges = new Button("Lưu chỉnh sửa");
    container = new GridPane();
    potrait = new ImageView(user.getAvatar());
    potraitWrapper = new VBox(potrait, photoNotice);
    container.addColumn(0, userIdLabel, nameLabel, dobLabel, genderLabel, addressLabel,
        phoneNumberLabel, ssnLabel, emailLabel, saveChanges);
    container.addColumn(1, userIdWrapper, name, dob.getContent(), gender, address, phoneNumber, ssn, email);
    container.add(potraitWrapper, 2, 0, 1, 8);
    setFunction();
    style();
  }

  private void setFunction() {
    copyUserId.setOnMouseClicked(_ -> {
      Toolkit.getDefaultToolkit()
          .getSystemClipboard()
          .setContents(
              new StringSelection(userId.getText()),
              null
          );
      copyUserId.setText("Đã sao chép");
      copyUserId.setMouseTransparent(true);
      Style.styleRoundedButton(copyUserId, Style.DARKGREEN, 150, 50, 16);
      Timeline t = new Timeline(new KeyFrame(
          Duration.millis(2000),
          e -> {
            copyUserId.setMouseTransparent(false);
            copyUserId.setText("Sao chép");
            Style.styleRoundedButton(copyUserId, Style.LIGHTGREEN, 150, 50, 16);
          }
      ));
      t.play();
    });
    saveChanges.setOnMouseClicked(_ -> {

    });
  }

  private void style() {
    Style.styleImg(potrait, 400);
    Style.styleTitle(nameLabel, 16);
    Style.styleTitle(addressLabel, 16);
    Style.styleTitle(dobLabel, 16);
    Style.styleTitle(emailLabel, 16);
    Style.styleTitle(userIdLabel, 16);
    Style.styleTitle(genderLabel, 16);
    Style.styleTitle(phoneNumberLabel, 16);
    Style.styleTitle(ssnLabel, 16);
    Style.styleTitle(photoNotice, 16);

    userId.setMouseTransparent(true);
    userId.setFocusTraversable(false);
    userIdWrapper.setSpacing(50);
    Style.styleTextField(userId, 250, 50, 16, "");
    Style.styleTextField(name, 500, 50, 16, "");
    Style.styleTextField(address, 500, 50, 16, "");
    dob.style(500);
    Style.styleTextField(email, 500, 50, 16, "");
    Style.styleTextField(phoneNumber, 500, 50, 16, "");
    Style.styleTextField(ssn, 500, 50, 16, "");
    StyleForm.styleComboBox(gender, 500, 50, 16, "");
    Style.styleRoundedButton(copyUserId, Style.LIGHTGREEN, 150, 50, 16);
    Style.styleRoundedButton(saveChanges, Style.LIGHTGREEN, 150, 50, 16);

    ColumnConstraints labelCol = new ColumnConstraints(150);
    ColumnConstraints fieldCol = new ColumnConstraints(550);
    ColumnConstraints potraitCol = new ColumnConstraints();
    potraitCol.setHgrow(Priority.ALWAYS);
    container.getColumnConstraints().addAll(labelCol, fieldCol, potraitCol);
    Style.setDebugBorder(potraitWrapper);
    potraitWrapper.setAlignment(Pos.CENTER);
    potraitWrapper.setSpacing(10);
    container.setVgap(10);
    container.setHgap(20);
  }

  public GridPane getContent() {
    return container;
  }
}


