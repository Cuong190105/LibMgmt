package org.example.libmgmt.ui.components.body.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleCard;

/**
 * A card to display brief info of a document in gallery view.
 */
public class DocumentCard {
  private final Document doc;
  private final ImageView cover;
  private final Label title;
  private final Text authors;
  private final VBox container;

  /**
   * Constructor.
   *
   * @param doc Target.
   */
  public DocumentCard(Document doc) {
    this.doc = doc;
    title = new Label(doc.getTitle());
    authors = new Text(doc.getAuthor());
    cover = new ImageView(doc.getCover());
    container = new VBox(cover, title, authors);
    setFunction();
    style();
  }

  /**
   * Set needed function.
   */
  private void setFunction() {
    container.setOnMouseClicked(_ -> {
      UIHandler.openDocDetail(doc);
    });
  }

  /**
   * Style card.
   */
  private void style() {
    Style.styleImg(cover, 200);
    container.setSpacing(10);

    StyleCard.styleCard(container, 250, 400);
    container.setAlignment(Pos.TOP_CENTER);
    StyleCard.styleTitle(title, 230);
    StyleCard.styleContent(authors, 230);
    container.setPadding(new Insets(25, 10, 25, 10));
  }

  /** Get card's content. */
  public VBox getCard() {
    return container;
  }
}
    