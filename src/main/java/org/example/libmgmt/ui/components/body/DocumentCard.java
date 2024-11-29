package org.example.libmgmt.ui.components.body;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.style.StyleCard;

public class DocumentCard {
    private Document doc;
    private ImageView cover;
    private Label title;
    private Text authors;
    private VBox container;

    public DocumentCard(Document doc) {
//        cover = new ImageView(new Image(doc.getCover()));
//        title = new Label(doc.getName());
//        authors = new Text(doc.getAuthor());
        try {
//            cover = new ImageView(new Image(doc.getCover()));
            // Trigger Exception
//            throw new Exception();
            cover = new ImageView(
                    new Image(LibMgmt.class.getResourceAsStream("img/bookCoverPlaceholder.png")));
        } catch (Exception e) {

        }
        this.doc = doc;
        title = new Label("Thay Đổi Tí Hon Hiệu Quả Bất Ngờ");
        authors = new Text("James Clear");
        container = new VBox(cover, title, authors);
        setFunction();
        style();
    }

    /** Set needed function. */
    private void setFunction() {
        container.setOnMouseClicked(_ -> {
            UIHandler.openDocDetail(doc);
        });
    }

    /** Style card. */
    private void style() {
        StyleCard.styleImg(cover, 200);
        container.setSpacing(10);
        StyleCard.styleCard(container, 220, 400);
        StyleCard.styleTitle(title,200);
        StyleCard.styleContent(authors, 200);
        container.setPadding(new Insets(10));
    }

    /** Get card's content. */
    public VBox getCard() {
        return container;
    }
}
