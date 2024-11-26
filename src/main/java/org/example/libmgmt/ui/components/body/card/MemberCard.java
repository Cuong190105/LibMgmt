package org.example.libmgmt.ui.components.body.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleCard;

public class MemberCard {
    private User member;
    private ImageView avatar;
    private Label name;
    private Text uid;
    private Text userType;
    private VBox info;
    private BorderPane container;

    public MemberCard(User user) {
        member = user;
        avatar = new ImageView(new Image(
                LibMgmt.class.getResourceAsStream("img/accountAction/user0123456.png")
        ));
//        name = new Label(user.getName());
//        uid = new Text("Mã số: " + user.getUID());
//        userType = new Text(user.getUserType());
        container = new BorderPane();
        name = new Label("Lorem ipsum");
        uid = new Text("Mã số: 12345678");
        userType = new Text("Thành viên miễn phí");
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