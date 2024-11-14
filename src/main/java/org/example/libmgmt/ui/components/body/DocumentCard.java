//package org.example.libmgmt.ui.components.body;
//
//import javafx.scene.control.Label;
//import javafx.scene.effect.BlurType;
//import javafx.scene.effect.DropShadow;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//import org.example.libmgmt.ui.style.StyleCard;
//
//public class DocumentCard {
//    private ImageView cover;
//    private Label title;
//    private Text authors;
//    private VBox container;
//
//    public DocumentCard(Document doc) {
//        cover = new ImageView(new Image(doc.getCover()));
//        title = new Label(doc.getTitle());
//        authors = new Text(doc.getAuthor());
//        container = new VBox(cover, title, authors);
//        style();
//    }
//
//    public void style() {
//        StyleCard.styleImg(cover, 200);
//        container.setSpacing(10);
//        StyleCard.styleCard(container, 250, 400);
//        StyleCard.styleTitle(title,200);
//        StyleCard.styleContent(authors, 200);
//    }
//
//    public VBox getCard() {
//        return container;
//    }
//}