package org.example.libmgmt.ui.components.body;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.libmgmt.ui.style.StyleCard;

public class MemberCard {
    private ImageView avatar;
    private Label name;
    private Text uid;
    private Text userType;
    private BorderPane container;

    public MemberCard(/*User user*/) {
//        avatar = new ImageView(new Image(user.getAvatar());
//        name = new Label(user.getName());
//        uid = new Text(user.getUID());
//        userType = new Text(user.getUserType());
        container = new BorderPane();
        name = new Label("Lorem ipsum");
        uid = new Text("12345678");
        userType = new Text("Thành viên miễn phí");
//        container.setLeft(avatar);
        container.setCenter(new VBox(name, uid, userType));
        style();
    }

    private void style() {
//        StyleCard.styleImg(avatar, 150);
        StyleCard.styleCard(container, 400, 200);
        StyleCard.styleTitle(name, 150);
        StyleCard.styleContent(uid, 150);
        StyleCard.styleContent(userType, 150);
        container.setPadding(new Insets(25));
    }

    public BorderPane getCard() {
        return container;
    }
}
