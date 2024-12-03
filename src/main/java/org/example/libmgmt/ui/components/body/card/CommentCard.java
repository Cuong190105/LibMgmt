package org.example.libmgmt.ui.components.body.card;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.libmgmt.DB.Comment;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.components.body.Star;
import org.example.libmgmt.ui.style.Style;

/**
 * Comment card in document detail viewer.
 */
public class CommentCard {
  private ImageView avatar;
  private final Label name;
  private final Text commentDate;
  private final Text comment;
  private final GridPane container;

  /**
   * Contructor.
   *
   * @param comment Comment object.
   */
  public CommentCard(Comment comment) {
    User reader = new User();
    try {
      this.avatar = new ImageView(reader.getAvatar());
    } catch (Exception e) {
      this.avatar = new ImageView(new Image(
          LibMgmt.class.getResourceAsStream("img/accountAction/userPlaceHolder.png")
      ));
    }
    this.name = new Label(reader.getName());
    this.commentDate = new Text(comment.getDate());
    this.comment = new Text(comment.getComment());
    VBox avatarContainer = new VBox(avatar);
    container = new GridPane();
    container.add(avatarContainer, 0, 0, 1, 3);
    HBox ratingStar = Star.getStar(1, comment.getRating());
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