package org.example.libmgmt.ui.components.body.contentSection;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.ui.components.body.DateGroup;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

/**
 * A panel displaying a specific user info.
 */
public class UserDetails extends Content {
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
    potraitWrapper = new VBox(potrait, photoNotice);
    infoTable.addColumn(0, userIdLabel, nameLabel, dobLabel, genderLabel, addressLabel,
        phoneNumberLabel, ssnLabel, emailLabel, saveChanges);
    infoTable.addColumn(1, userId, name, dob.getContent(), gender, address, phoneNumber, ssn, email);
    mainContent = new FlowPane(infoTable, potraitWrapper);
    container = new VBox(mainContent, saveChanges);
    wrapper = new ScrollPane(container);
    setFunction();
    style();
  }

  private void setFunction() {
    userId.setEditable(false);
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

    Style.styleTextField(userId, 500, 50, 16, false, "");
    Style.styleTextField(name, 500, 50, 16, false, "");
    Style.styleTextField(address, 500, 50, 16, false, "");
    dob.style(500);
    Style.styleTextField(email, 500, 50, 16, false, "");
    Style.styleTextField(phoneNumber, 500, 50, 16, false, "");
    Style.styleTextField(ssn, 500, 50, 16, false, "");
    StyleForm.styleComboBox(gender, 500, 50, 16, "");
    Style.styleRoundedSolidButton(saveChanges, Style.LIGHTGREEN, 150, 50, 16);

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
    potraitWrapper.setAlignment(Pos.CENTER);
    potraitWrapper.setSpacing(10);
    container.setAlignment(Pos.CENTER);
    container.setSpacing(50);
  }

  public ScrollPane getContent() {
    return wrapper;
  }
}


