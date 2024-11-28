package org.example.libmgmt.ui.components.body.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleCard;

/**
 * A card to display brief info of a member in gallery view.
 */
public class MemberCard {
  private final User member;
  private final ImageView avatar;
  private final Label name;
  private final Text uid;
  private final Text userType;
  private final VBox info;
  private final BorderPane container;

  /**
   * Constructor.
   *
   * @param user Member whose info is displayed.
   */
  public MemberCard(User user) {
    member = user;
    avatar = new ImageView(user.getAvatar());
    name = new Label(user.getName());
    uid = new Text("Mã số: " + user.getUid());
    userType = new Text(user.isLibrarian() ? "Thủ thư" : "Người đọc");
    container = new BorderPane();
    container.setLeft(avatar);
    info = new VBox(name, uid, userType);
    container.setCenter(info);
    setFunction();
    style();
  }

  private void style() {
    Style.styleImg(avatar, 150);
    StyleCard.styleCard(container, 400, 200);
    StyleCard.styleTitle(name, 150);
    StyleCard.styleContent(uid, 150);
    StyleCard.styleContent(userType, 150);
    info.setAlignment(Pos.CENTER_LEFT);
    BorderPane.setMargin(avatar, new Insets(0, 25, 0, 0));
    container.setPadding(new Insets(25));
  }

  private void setFunction() {
    container.setOnMousePressed(_ -> {
      UIHandler.openMemberDetails(member);
    });
  }

  public BorderPane getCard() {
    return container;
  }
}