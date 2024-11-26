package org.example.libmgmt.ui.components.body.card;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.components.body.Comment;
import org.example.libmgmt.ui.components.body.Star;
import org.example.libmgmt.ui.style.Style;

public class CommentCard {
    private ImageView avatar;
    private VBox avatarContainer;
    private Label name;
    private HBox ratingStar;
    private Text commentDate;
    private Text comment;
    private GridPane container;

    public CommentCard(Comment comment) {
        User reader = new User();
        try {
            this.avatar = new ImageView(new Image(reader.getAvatar()));
        } catch (Exception e){
            this.avatar = new ImageView(new Image(
                    LibMgmt.class.getResourceAsStream("img/accountAction/user0123456.png")
            ));
        }
        avatarContainer = new VBox(avatar);
        this.name = new Label(reader.getName());
        this.commentDate = new Text(comment.getDate());
        this.ratingStar = Star.getStar(1, comment.getRating());
        this.comment = new Text(comment.getComment());
        container = new GridPane();
        container.add(this.avatarContainer, 0, 0, 1, 3);
        container.addColumn(1, name, commentDate, ratingStar, this.comment);
        style();
    }

    private void style() {
        avatar.setPreserveRatio(true);
        avatar.setFitWidth(40);
        avatar.setSmooth(true);
        Style.styleTitle(name, 16);
        Style.styleWrapText(commentDate, 100, 12);
        Style.styleWrapText(comment, 600, 16);
        container.setHgap(25);
    }

    public GridPane getCard() {
        return container;
    }
}